package com.rinko1231.majospellenchantment.event;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.entity.spells.blood_needle.BloodNeedle;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.setup.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.rinko1231.majospellenchantment.init.ModEnchantments.VLAD_TEPES_ENCHANT;

public class VladTepesEvent {
    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        if(MajoSpellEnchantmentConfig.vladTepesDisabled.get()) return;
        if (event.getLevel().isClientSide) return; // 只在服务端跑

        ServerPlayer player = (ServerPlayer) event.getEntity();
        if(player == null) return;

        ItemStack bow = event.getBow();

        int enchantLevel = bow.getEnchantmentLevel(VLAD_TEPES_ENCHANT.get());
        if (enchantLevel <= 0) return;

        AttributeInstance attr = player.getAttribute(AttributeRegistry.BLOOD_SPELL_POWER.get());
        double bloodPower =0;
        if(attr != null)
            bloodPower = attr.getValue();

        // 根据等级计算触发概率
        if (player.getRandom().nextFloat() > 0.125F * enchantLevel) return;
        MagicData magicData = MagicData.getPlayerMagicData(player);
        float manaCost = (float) MajoSpellEnchantmentConfig.getVladTepesManaCost(enchantLevel);
        if(magicData.getMana()>=manaCost)
        {
            magicData.addMana(-(manaCost));
            shootExtraNeedles(player, event.getLevel(), enchantLevel, bloodPower);
            Messages.sendToPlayer(new SyncManaPacket(magicData), player);
        }
    }

    private void shootExtraNeedles(ServerPlayer player, Level world, int enchantmentLevel, double bloodPower) {
        int count = MajoSpellEnchantmentConfig.getVladTepesBloodNeedleCount(enchantmentLevel); // 每级多发几根

        float damage = (float)(MajoSpellEnchantmentConfig.getVladTepesBloodNeedleRawDamage(enchantmentLevel) + MajoSpellEnchantmentConfig.vladTepesBloodNeedleDamageBloodSpellPowerScaling.get() * bloodPower);

        // 做一次射线，方向跟玩家拉弓一致
        Vec3 look = player.getLookAngle();
        HitResult raycast = player.pick(64.0D, 0.0F, false);

        int degreesPerNeedle = 10; // 血针散射角度

        // 让箭居中对称排列
        int half = count / 2;
        for (int i = 0; i < count; i++) {
            int offset = i - half;
            // 如果是偶数，把整体往右偏 0.5
            if (count % 2 == 0) {
                offset += 0.5;
            }

            int rotation = (int)(offset * degreesPerNeedle);

            BloodNeedle needle = new BloodNeedle(world, player);
            needle.setDamage(damage);
            needle.setZRot((float) rotation);

            Vec3 spawn = player.getEyePosition()
                    .add((new Vec3(0.0F, 1.5F, 0.0F))
                            .zRot((float) rotation * ((float) Math.PI / 180F))
                            .xRot(-player.getXRot() * ((float) Math.PI / 180F))
                            .yRot(-player.getYRot() * ((float) Math.PI / 180F)));

            needle.moveTo(spawn);

            needle.shoot(raycast.getLocation().subtract(spawn).normalize());
            world.addFreshEntity(needle);
        }
    }
}
