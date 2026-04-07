package com.rinko1231.majospellenchantment.entity;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.ZapParticleOption;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Holder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


import net.minecraft.world.phys.Vec3;

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
                        LivingEntity victim = candidates.getFirst();

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
    @Override public Optional<Holder<SoundEvent>> getImpactSound() { return Optional.empty(); }
    @Override public boolean shouldBeSaved() { return false; }
}

/*
public class DeathChainLightning extends AbstractMagicProjectile {
    List<Entity> allVictims = new ArrayList<>();
    Entity initialVictim;

    public int maxConnections;           // 总允许命中次数上限（随击杀可 +1）
    public float range;
    private static final Supplier<AbstractSpell> SPELL = SpellRegistry.CHAIN_LIGHTNING_SPELL;

    int hits;                // 已命中次数
    boolean deathChainMode;  // 是否为“死亡连锁”模式
    int lingerTicks;         // 最少存活时间
    float bonusDamage;       // 伤害成长（击杀 +1）
    float bonusDamageGrowth;
    Entity lastVictim;       // 单链：上一波的目标

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

    // —— 外部初始化 —— //
    public DeathChainLightning asDeathChain(int baseConnections, float baseRange, float extraDamage, int minLinger, float bonusDamageGrowth) {
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

        if (!this.level().isClientSide) {
            if (this.lingerTicks > 0) this.lingerTicks--;

            int f = this.tickCount - 1;
            if (f % 6 == 0) {
                if (f == 0) {
                    // 起始打击 initialVictim
                    if (this.initialVictim != null && !this.allVictims.contains(this.initialVictim)) {
                        boolean killed = doHurtOnce(this.initialVictim);
                        if (this.getOwner() != null) {
                            Vec3 start = this.getOwner().position().add(0, this.getOwner().getBbHeight()/2.0F, 0);
                            Vec3 dest  = this.initialVictim.position().add(0, this.initialVictim.getBbHeight()/2.0F, 0);
                            ((ServerLevel)this.level()).sendParticles(new ZapParticleOption(dest), start.x, start.y, start.z, 1, 0,0,0, 0);
                        }
                        this.allVictims.add(this.initialVictim);
                        this.lastVictim = this.initialVictim;

                        if (!killed) {
                            this.discard();
                        }
                    }
                } else {
                    if (this.lastVictim != null && this.hits < this.maxConnections) {
                        // —— 在范围内寻找最近的一个目标 —— //
                        List<LivingEntity> candidates = this.level().getEntitiesOfClass(
                                LivingEntity.class,
                                this.lastVictim.getBoundingBox().inflate(this.range),
                                this::canHitEntity
                        );
                        if (!candidates.isEmpty()) {
                            // 最近优先
                            candidates.sort(Comparator.comparingDouble(o -> o.distanceToSqr(this.lastVictim)));
                            LivingEntity victim = candidates.getFirst();

                            boolean killed = tryZapOne(
                                    this.lastVictim.position().add(0, this.lastVictim.getBbHeight()/2.0F, 0),
                                    this.lastVictim,
                                    victim
                            );

                            this.allVictims.add(victim);
                            this.lastVictim = victim;

                            if (!killed) {
                                this.discard(); // 未击杀则终止
                            }
                        } else {
                            this.discard(); // 没有目标则终止
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
    }


    // —— 放电 —— //
    private boolean tryZapOne(Vec3 startPos, Entity srcEntity, LivingEntity victim) {
        if (this.hits >= this.maxConnections) return false;
        if (!canHitEntity(victim)) return false;

        Vec3 dest = victim.position().add(0, victim.getBbHeight()/2.0F, 0);
        boolean los = Utils.hasLineOfSight(this.level(), srcEntity.getEyePosition(), dest, true);
        if (!los) return false;

        boolean killed = doHurtOnce(victim);
        victim.playSound(SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), 2.0F, 1.0F);
        ((ServerLevel)this.level()).sendParticles(new ZapParticleOption(dest),
                startPos.x, startPos.y, startPos.z, 1, 0,0,0, 0);
        return killed;
    }

    // —— 造成伤害；击杀则 +1 继续数、+1 伤害 —— //
    private boolean doHurtOnce(Entity victim) {
        this.hits++;
        float amount = this.damage + this.bonusDamage;
        DamageSources.applyDamage(victim, amount, SPELL.get().getDamageSource(this, this.getOwner()));
        MagicManager.spawnParticles(this.level(), ParticleHelper.ELECTRICITY,
                victim.getX(), victim.getY() + victim.getBbHeight()/2.0F, victim.getZ(),
                10, victim.getBbWidth()/3.0F, victim.getBbHeight()/3.0F, victim.getBbWidth()/3.0F, 0.1, false);

        boolean killed = (victim instanceof LivingEntity le) && le.isDeadOrDying();
        if (killed && this.deathChainMode) {
            this.maxConnections += 1;     // 可继续数 +1
            this.bonusDamage   += 1.0F;   // 伤害 +1
            this.lingerTicks   = Math.max(this.lingerTicks, 10);
        }
        return killed;
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
    @Override public Optional<Holder<SoundEvent>> getImpactSound() { return Optional.empty(); }
    @Override public boolean shouldBeSaved() { return false; }
}
*/