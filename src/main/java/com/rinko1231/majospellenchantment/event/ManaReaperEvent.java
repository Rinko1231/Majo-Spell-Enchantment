package com.rinko1231.majospellenchantment.event;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;

import io.redspace.ironsspellbooks.api.magic.MagicData;

import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.setup.Messages;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.rinko1231.majospellenchantment.init.ModEnchantments.MANA_REAPER_ENCHANT;


public class ManaReaperEvent {


    @SubscribeEvent
    public void onMobDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) return;

        ItemStack heldItem1 = serverPlayer.getMainHandItem();
        ItemStack heldItem2 = serverPlayer.getOffhandItem();

        int enchantLevel1 = serverPlayer.getItemBySlot(EquipmentSlot.MAINHAND).getEnchantmentLevel(MANA_REAPER_ENCHANT.get());
        int enchantLevel2 = serverPlayer.getItemBySlot(EquipmentSlot.OFFHAND).getEnchantmentLevel(MANA_REAPER_ENCHANT.get());
        int enchantLevel = Math.max(enchantLevel1,enchantLevel2);
        if (enchantLevel <= 0) return;


        double mobMaxHealth = event.getEntity().getMaxHealth();

        // 配置
        double factor = MajoSpellEnchantmentConfig.manaReaperRegenFactor.get();
        double capPerLevel = MajoSpellEnchantmentConfig.manaReaperRegenCapPerLevel.get();

        // 计算魔力恢复
        double manaRestore = mobMaxHealth * factor;
        double cap = capPerLevel * enchantLevel;
        manaRestore = Math.min(manaRestore, cap);

        if (manaRestore <= 0) return;

        // 应用恢复效果
        MagicData magicData = MagicData.getPlayerMagicData(serverPlayer);
        magicData.addMana((float)manaRestore);

        Messages.sendToPlayer(new SyncManaPacket(magicData), serverPlayer);
    }
}
