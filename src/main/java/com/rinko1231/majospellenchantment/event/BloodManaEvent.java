package com.rinko1231.majospellenchantment.event;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;

import io.redspace.ironsspellbooks.api.magic.MagicData;

import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.setup.Messages;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.rinko1231.majospellenchantment.init.ModEnchantments.BLOOD_MANA_ENCHANT;

public class BloodManaEvent {
    @SubscribeEvent
    public void onPlayerDamaged(LivingDamageEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        // 检查附魔
        int enchantLevel = player.getItemBySlot(EquipmentSlot.CHEST)
                .getEnchantmentLevel(BLOOD_MANA_ENCHANT.get());
        if (enchantLevel <= 0) return;


        float damageTaken = event.getAmount();
        if (damageTaken <= 0) return;

        double bloodManaRatioPerLevel = MajoSpellEnchantmentConfig.bloodManaRatioPerLevel.get();// 每点伤害转化为多少魔力
        double capPerLevel = MajoSpellEnchantmentConfig.bloodManaCapPerLevel.get(); //每级最大魔力恢复量

        // 计算恢复魔力
        double manaRestore = damageTaken * (bloodManaRatioPerLevel * enchantLevel);
        double cap = capPerLevel * enchantLevel;
        manaRestore = Math.min(manaRestore, cap);

        if (manaRestore <= 0) return;

        // 回复魔力
        MagicData magicData = MagicData.getPlayerMagicData(player);
        magicData.addMana((float) manaRestore);

        Messages.sendToPlayer(new SyncManaPacket(magicData), player);
    }
}
