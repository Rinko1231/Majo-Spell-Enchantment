package com.rinko1231.majospellenchantment.event;

import com.rinko1231.majospellenchantment.config.MajoSpellEnchantmentConfig;

import com.rinko1231.majospellenchantment.init.ModMobEffects;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.rinko1231.majospellenchantment.init.ModEnchantments.PHASE_DASHED_ENCHANT;

public class PhaseDashedEvent {

    private static final Map<UUID, Integer> castCountMap = new HashMap<>();
    private static final Map<UUID, Long> lastCastTimeMap = new HashMap<>();

    @SubscribeEvent
    public void onSpellCast(LivingDamageEvent event) {


        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        if (!(event.getSource() instanceof SpellDamageSource)) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int phaseLevel = boots.getEnchantmentLevel(PHASE_DASHED_ENCHANT.get());
        if (phaseLevel <= 0) return;



        UUID playerId = player.getUUID();
        long currentTime = player.level().getGameTime();

        long lastCastTime = lastCastTimeMap.getOrDefault(playerId, 0L);
        if (currentTime - lastCastTime > 60) {
            castCountMap.put(playerId, 0);
        }

        int currentCount = castCountMap.getOrDefault(playerId, 0) + 1;
        castCountMap.put(playerId, currentCount);
        lastCastTimeMap.put(playerId, currentTime);

        if (currentCount >= 3) {
            castCountMap.put(playerId, 0); // 重置
            player.addEffect(new MobEffectInstance(
                    ModMobEffects.PHASE_DASHED.get(),
                    MajoSpellEnchantmentConfig.phaseDashedEffectDuration.get(),             // 4 秒
                    phaseLevel - 1, // 等级对应附魔等级
                    false, false, true
            ));
        }
    }
}