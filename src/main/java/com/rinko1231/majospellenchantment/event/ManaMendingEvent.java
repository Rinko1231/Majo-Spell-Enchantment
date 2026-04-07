package com.rinko1231.majospellenchantment.event;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.List;

import static com.rinko1231.majospellenchantment.init.EnchantmentRegistry.MANA_MENDING_ENCHANT;


public class ManaMendingEvent {


    @SubscribeEvent
    public void onManaChanged(ChangeManaEvent event) {
        if(!MajoSpellEnchantmentConfig.manaMendingEnabled.get()) return;
        Player player = event.getEntity();
        if (player == null || player.level().isClientSide()) return;

        float oldMana = event.getOldMana();
        float newMana = event.getNewMana();

        // 仅在“消耗”时触发（旧值 > 新值）
        float consumed = oldMana - newMana;
        if (consumed < MajoSpellEnchantmentConfig.manaMendingThreshold.get()) return;

        // 规则：至少 1 点；每消耗满 100 再 +2
        int repairPoints = 1 + (int) (Math.floor(consumed / 100f) * MajoSpellEnchantmentConfig.manaMendingCoefficient.get());

        // 收集需要修复的物品（护甲 + 主副手）
        List<ItemStack> targets = new ArrayList<>();
        for (ItemStack armor : player.getArmorSlots()) {
            targets.add(armor);
        }
        targets.add(player.getMainHandItem());
        targets.add(player.getOffhandItem());

        for (ItemStack stack : targets) {
            if (stack.isEmpty() || !stack.isDamageableItem() || !stack.isDamaged()) continue;

            int level = getMendingLevel(stack, player);
            if (level <= 0) continue;

            // 实际修复；一次性减小 damage 值
            int current = stack.getDamageValue();
            int repaired = Math.max(0, current - repairPoints);
            if (repaired != current) {
                stack.setDamageValue(repaired);
                // 可选：按等级加成
                // int repaired = Math.max(0, current - repairPoints * level);
            }
        }
    }


    private static int getMendingLevel(ItemStack stack, Player player) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                player.level().holderLookup(MANA_MENDING_ENCHANT.registryKey()).getOrThrow(MANA_MENDING_ENCHANT),
                stack
        );
    }

}
