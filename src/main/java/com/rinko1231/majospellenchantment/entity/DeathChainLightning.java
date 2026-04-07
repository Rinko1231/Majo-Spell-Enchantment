package com.rinko1231.majospellenchantment.entity;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.ZapParticleOption;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.rinko1231.majospellenchantment.init.EntityRegistry.DEATH_CHAIN_LIGHTNING;

public class DeathChainLightning extends AbstractMagicProjectile {
    List<Entity> allVictims = new ArrayList<>();
    Entity initialVictim;

    public int maxConnections;
    public float range;
    private static final Supplier<AbstractSpell> SPELL = SpellRegistry.CHAIN_LIGHTNING_SPELL;

    int hits;
    boolean deathChainMode;
    int lingerTicks;
    float bonusDamage;
    float bonusDamageGrowth;
    Entity lastVictim;

    public void impactParticles(double x, double y, double z) {}
    public void trailParticles() {}

    public DeathChainLightning(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
        this.maxConnections = 1;
        this.range = 3.0F;
        this.setNoGravity(true);
        this.noPhysics = true;

        this.deathChainMode = false;
        this.lingerTicks = 20;
        this.bonusDamage = 0f;
    }

    public DeathChainLightning(Level level, Entity owner, Entity initialVictim) {
        this((EntityType) DEATH_CHAIN_LIGHTNING.get(), level);
        this.setOwner(owner);
        this.setPos(initialVictim.position());
        this.initialVictim = initialVictim;
    }

    public DeathChainLightning asDeathChain(int baseConnections, float baseRange,
                                            float extraDamage, int minLinger, float bonusDamageGrowth) {
        this.deathChainMode = true;
        this.maxConnections = Math.max(1, baseConnections);
        this.range = Math.max(2.0F, baseRange);
        this.bonusDamage = Math.max(0f, extraDamage);
        this.bonusDamageGrowth = bonusDamageGrowth;
        this.lingerTicks = Math.max(10, minLinger);
        return this;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        if (this.lingerTicks > 0) this.lingerTicks--;

        int f = this.tickCount - 1;
        if (f % 6 == 0) {
            if (f == 0) {
                if (this.initialVictim != null && !this.allVictims.contains(this.initialVictim)) {
                    doHurtOnce(this.initialVictim);
                    this.allVictims.add(this.initialVictim);
                    this.lastVictim = this.initialVictim;
                }
            } else {
                if (this.lastVictim != null && this.hits < this.maxConnections) {
                    List<LivingEntity> candidates = this.level().getEntitiesOfClass(
                            LivingEntity.class,
                            this.lastVictim.getBoundingBox().inflate(this.range),
                            this::canHitEntity
                    );
                    if (!candidates.isEmpty()) {
                        candidates.sort(Comparator.comparingDouble(o -> o.distanceToSqr(this.lastVictim)));
                        LivingEntity victim = candidates.get(0);

                        doZap(this.lastVictim, victim);
                        this.allVictims.add(victim);
                        this.lastVictim = victim;
                    } else {
                        this.discard();
                    }
                } else {
                    this.discard();
                }
            }

            if ((this.hits >= this.maxConnections) || this.lingerTicks <= 0) {
                this.discard();
            }
        }
    }

    private void doZap(Entity src, LivingEntity victim) {
        Vec3 start = src.position().add(0, src.getBbHeight()/2.0F, 0);
        Vec3 dest  = victim.position().add(0, victim.getBbHeight()/2.0F, 0);

        doHurtOnce(victim);
        victim.playSound(SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), 2.0F, 1.0F);
        ((ServerLevel)this.level()).sendParticles(new ZapParticleOption(dest),
                start.x, start.y, start.z, 1, 0,0,0, 0);
    }

    // —— 只管伤害，不判死亡 —— //
    private void doHurtOnce(Entity victim) {
        this.hits++;
        float amount = this.damage + this.bonusDamage;
        DamageSources.applyDamage(victim, amount, SPELL.get().getDamageSource(this, this.getOwner()));
        MagicManager.spawnParticles(this.level(), ParticleHelper.ELECTRICITY,
                victim.getX(), victim.getY() + victim.getBbHeight()/2.0F, victim.getZ(),
                10, victim.getBbWidth()/3.0F, victim.getBbHeight()/3.0F, victim.getBbWidth()/3.0F, 0.1, false);
    }

    // —— 当外部检测到“击杀成功”时调用 —— //
    public void onKill(LivingEntity victim) {
        if (!this.deathChainMode) return;
        this.maxConnections += 1;
        this.bonusDamage += this.bonusDamageGrowth > 0 ? this.bonusDamageGrowth : 1.0F;
        this.lingerTicks = Math.max(this.lingerTicks, 10);
        this.lastVictim = victim; // 继续从这里传递
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return target instanceof LivingEntity
                && !DamageSources.isFriendlyFireBetween(target, this.getOwner())
                && target != this.getOwner()
                && !this.hasAlreadyZapped(target)
                && super.canHitEntity(target);
    }

    public boolean hasAlreadyZapped(Entity entity) {
        return this.allVictims.contains(entity);
    }

    @Override public float getSpeed() { return 0.0F; }
    public Optional<Supplier<SoundEvent>> getImpactSound() { return Optional.empty(); }
    @Override public boolean shouldBeSaved() { return false; }
}