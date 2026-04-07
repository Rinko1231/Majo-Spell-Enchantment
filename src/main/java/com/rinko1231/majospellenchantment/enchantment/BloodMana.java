package com.rinko1231.majospellenchantment.enchantment;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;
import com.rinko1231.majospellenchantment.init.BaseEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BloodMana extends BaseEnchantment {
    public BloodMana() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST , new EquipmentSlot[]{EquipmentSlot.CHEST});
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    public boolean isTreasureOnly() {
        return MajoSpellEnchantmentConfig.bloodManaTreasureOnly.get();
    }

    @Override
    public boolean isDisabled() {
        return MajoSpellEnchantmentConfig.bloodManaDisabled.get();
    }
}