package com.rinko1231.majospellenchantment.config;


import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

import static java.lang.Math.min;

public class MajoSpellEnchantmentConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue manaReaperEnabled;
    public static ModConfigSpec.DoubleValue manaReaperRegenFactor;
    public static ModConfigSpec.DoubleValue manaReaperRegenCapPerLevel;

    public static ModConfigSpec.BooleanValue hopelessPowerEnabled;
    public static ModConfigSpec.DoubleValue hopelessPowerThresholdStart;
    public static ModConfigSpec.DoubleValue hopelessPowerThresholdMax;
    public static ModConfigSpec.DoubleValue hopelessPowerBonusPerLevel;

    public static ModConfigSpec.BooleanValue oceanGraceEnabled;
    public static ModConfigSpec.DoubleValue oceanGraceManaReductionPerLevel;

    public static ModConfigSpec.BooleanValue phaseDashedEnabled;
    public static ModConfigSpec.IntValue phaseDashedEffectDuration;

    public static ModConfigSpec.BooleanValue spellStreakEnabled;
    public static ModConfigSpec.DoubleValue spellStreakCDReductionPerLevel;
    public static ModConfigSpec.DoubleValue spellStreakEntityMinHealth;
    public static ModConfigSpec.BooleanValue spellStreakBlacklistOrWhitelist;
    public static ModConfigSpec.ConfigValue<List<? extends String>> spellStreakEntityBlacklist;
    public static ModConfigSpec.ConfigValue<List<? extends String>> spellStreakEntityWhitelist;

    public static ModConfigSpec.BooleanValue nocturneAriaEnabled;
    public static ModConfigSpec.DoubleValue nocturneAriaBonusPerLevel;
    public static ModConfigSpec.BooleanValue daylightAnthemEnabled;
    public static ModConfigSpec.DoubleValue daylightAnthemBonusPerLevel;

    public static ModConfigSpec.BooleanValue bloodManaEnabled;
    public static ModConfigSpec.DoubleValue bloodManaRatioPerLevel;
    public static ModConfigSpec.DoubleValue bloodManaCapPerLevel;

    public static ModConfigSpec.BooleanValue partyLeaderEnabled;
    public static ModConfigSpec.DoubleValue partyLeaderGlowChancePerLevel;
    public static ModConfigSpec.DoubleValue partyLeaderFeverChancePerLevel;
    public static ModConfigSpec.IntValue partyLeaderEffectDuration;
    public static ModConfigSpec.IntValue partyLeaderEffectDurationSundayBonus;

    public static ModConfigSpec.BooleanValue vladTepesEnabled;
    public static ModConfigSpec.DoubleValue vladTepesBaseManaCost;
    public static ModConfigSpec.DoubleValue vladTepesManaCostPerLevel;
    public static ModConfigSpec.DoubleValue vladTepesBaseBloodNeedleDamage;
    public static ModConfigSpec.DoubleValue vladTepesBloodNeedleDamagePerLevel;
    public static ModConfigSpec.DoubleValue vladTepesBloodNeedleDamageBloodSpellPowerScaling;
    public static ModConfigSpec.IntValue vladTepesBaseBloodNeedleCount;
    public static ModConfigSpec.IntValue vladTepesBloodNeedleCountPerLevel;

    public static ModConfigSpec.BooleanValue redLotusEnabled;
    public static ModConfigSpec.DoubleValue redLotusFireFieldBaseDamage;
    public static ModConfigSpec.DoubleValue redLotusFireFieldDamagePerLevel;
    public static ModConfigSpec.DoubleValue redLotusFireFieldBaseRadius;
    public static ModConfigSpec.DoubleValue redLotusFireFieldRadiusPerLevel;
    public static ModConfigSpec.IntValue redLotusFireFieldBaseDuration;
    public static ModConfigSpec.IntValue redLotusFireFieldDurationPerLevel;

    public static ModConfigSpec.BooleanValue manaMendingEnabled;
    public static ModConfigSpec.DoubleValue manaMendingThreshold;
    public static ModConfigSpec.DoubleValue manaMendingCoefficient;

    public static ModConfigSpec.BooleanValue gentleGraveyardKeeperEnabled;

    public static ModConfigSpec.BooleanValue netherHeartEnabled;


    public static ModConfigSpec.BooleanValue iceFireSongEnabled;
    public static ModConfigSpec.DoubleValue iceFireSongExtraDamageRatioPerLevel;

    public static ModConfigSpec.BooleanValue NecrovoltEnabled;
    public static ModConfigSpec.DoubleValue NecrovoltBaseDamage;
    public static ModConfigSpec.DoubleValue NecrovoltDamagePerLevel;
    public static ModConfigSpec.DoubleValue NecrovoltBaseRadius;
    public static ModConfigSpec.DoubleValue NecrovoltRadiusPerLevel;
    public static ModConfigSpec.DoubleValue NecrovoltLightningSpellPowerScaling;
    public static ModConfigSpec.DoubleValue NecrovoltBonusDamageGrowthPerLevel;

    public static ModConfigSpec.BooleanValue lichKingEnabled;
    public static ModConfigSpec.DoubleValue lichKingUndeadSummonPossibilityPerLevel;
    public static ModConfigSpec.IntValue lichKingUndeadDurationPerLevel;

    public static ModConfigSpec.BooleanValue zoophonyEnabled;

    static {
        BUILDER.push("Config");

        // Mana Reaper Enchantment
        manaReaperEnabled = BUILDER
                .comment("[Messor Vitae/Mana Reaper] If Disabled, it will not take effect.")
                .define("manaReaperEnabled", true);
        manaReaperRegenFactor = BUILDER
                .comment("[Messor Vitae/Mana Reaper] Mana restoration factor per point of target's max health")
                .defineInRange("manaReaperRegenFactor", 1.0, 0.0, 100.0);
        manaReaperRegenCapPerLevel = BUILDER
                .comment("[Messor Vitae/Mana Reaper] Maximum mana restored per enchantment level")
                .defineInRange("manaReaperRegenCapPerLevel", 40.0, 0.0, 1000.0);

        // Hopeless Power Enchantment
        hopelessPowerEnabled = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] If Disabled, it will not take effect.")
                .define("hopelessPowerEnabled", true);
        hopelessPowerThresholdStart = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] HP% below this starts increasing spell power (0–1)")
                .defineInRange("hopelessPowerThresholdStart", 0.7, 0.0, 1.0);
        hopelessPowerThresholdMax = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] HP% below this reaches maximum spell power bonus (0–1)")
                .defineInRange("hopelessPowerThresholdMax", 0.3, 0.0, 1.0);
        hopelessPowerBonusPerLevel = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] Maximum spell power bonus per enchantment level (ADD_MULTIPLIED_BASE)")
                .defineInRange("hopelessPowerBonusPerLevel", 0.20, 0.0, 10.0);

        // Ocean Grace Enchantment
        oceanGraceEnabled = BUILDER
                .comment("[Gratia Oceani/Ocean Grace] If Disabled, it will not take effect.")
                .define("oceanGraceEnabled", true);
        oceanGraceManaReductionPerLevel = BUILDER
                .comment("[Gratia Oceani/Ocean Grace] Mana cost reduction per enchantment level")
                .defineInRange("oceanGraceManaReductionPerLevel", 0.1, 0.0, 1);

        // Phase Dashed Enchantment
        phaseDashedEnabled = BUILDER
                .comment("[Cursus Phasium/Phase Dashed] If Disabled, it will not take effect.")
                .define("phaseDashedEnabled", true);
        phaseDashedEffectDuration = BUILDER
                .comment("[Cursus Phasium/Phase Dashed] Effect duration in ticks after activation")
                .defineInRange("phaseDashedEffectDuration", 80, 0, 114514);

        // Spell Streak Enchantment
        spellStreakEnabled = BUILDER
                .comment("[Mortis Impetus/Spell Streak] If Disabled, it will not take effect.")
                .define("spellStreakEnabled", true);
        spellStreakCDReductionPerLevel = BUILDER
                .comment("[Mortis Impetus/Spell Streak] Cooldown reduction percentage per enchantment level (0–1)")
                .defineInRange("spellStreakCDReductionPerLevel", 0.1, 0.0, 1);
        spellStreakEntityMinHealth = BUILDER
                .comment("[Mortis Impetus/Spell Streak] Minimum entity max health required to trigger cooldown reduction. ")
                .comment("Entities with max health below this threshold will not trigger the effect.")
                .defineInRange("spellStreakEntityMinHealth", 16.0, 0.0, Integer.MAX_VALUE);
        spellStreakBlacklistOrWhitelist = BUILDER
                .comment("[Mortis Impetus/Spell Streak] Entity filtering mode: true = Blacklist mode (entities in blacklist are ignored), false = Whitelist mode (only entities in whitelist are valid)")
                .define("spellStreakBlacklistOrWhitelist", true);
        spellStreakEntityBlacklist = BUILDER
                .comment("[Mortis Impetus/Spell Streak] Entity blacklist (entity IDs in blacklist will not trigger cooldown reduction when killed). Example: [\"minecraft:squid\", \"minecraft:cod\"]")
                .defineList("spellStreakEntityBlacklist",
                        List.of("minecraft:squid", "minecraft:cod", "minecraft:salmon", "minecraft:horse"),
                        obj -> obj instanceof String);
        spellStreakEntityWhitelist = BUILDER
                .comment("[Mortis Impetus/Spell Streak] Entity whitelist (only entity IDs in whitelist will trigger cooldown reduction when killed). Example: [\"minecraft:zombie\", \"minecraft:skeleton\"]")
                .defineList("spellStreakEntityWhitelist",
                        List.of("minecraft:zombie", "minecraft:skeleton", "minecraft:player"),
                        obj -> obj instanceof String);

        // Day Or Night Enchantment
        nocturneAriaEnabled = BUILDER
                .comment("[Aria Noctis/Nocturne Aria] If Disabled, it will not take effect.")
                .define("nocturneAriaEnabled", true);
        nocturneAriaBonusPerLevel = BUILDER
                .comment("[Aria Noctis/Nocturne Aria] Cast Time Reduction per level at night with clear sky")
                .defineInRange("nocturneAriaBonusPerLevel", 0.20, 0.0, 1.0);
        daylightAnthemEnabled = BUILDER
                .comment("[Hymnus Aurorae/Daylight Anthem] If Disabled, it will not take effect.")
                .define("daylightAnthemEnabled", true);
        daylightAnthemBonusPerLevel = BUILDER
                .comment("[Hymnus Aurorae/Daylight Anthem] Cast Time Reduction per level during day with clear sky")
                .defineInRange("daylightAnthemBonusPerLevel", 0.20, 0.0, 1.0);

        // Blood As Mana Enchantment

        bloodManaEnabled = BUILDER
                .comment("[Sacramentum Sanguinis/Blood Mana] If Disabled, it will not take effect.")
                .define("bloodManaEnabled", true);

        bloodManaRatioPerLevel = BUILDER
                .comment("[Sacramentum Sanguinis/Blood Mana] Mana restored per damage point per level (default tuned to be ~2 Gluttony levels stronger)")
                .defineInRange("bloodManaRatioPerLevel", 3.0, 0.0, 114514);

        bloodManaCapPerLevel = BUILDER
                .comment("[Sacramentum Sanguinis/Blood Mana] Max mana restored per enchantment level")
                .defineInRange("bloodManaCapPerLevel", 40.0, 0.0, 1919810);


        partyLeaderEnabled = BUILDER
                .comment("[Dux Festivus/Party Leader] If Disabled, it will not take effect.")
                .define("partyLeaderEnabled", true);

        partyLeaderGlowChancePerLevel = BUILDER
                .comment("[Dux Festivus/Party Leader] Glow chance per enchantment level (0–1)")
                .defineInRange("partyLeaderGlowChancePerLevel", 0.2, 0.0, 1.0);

        partyLeaderFeverChancePerLevel = BUILDER
                .comment("[Dux Festivus/Party Leader] Sunday Fever chance per enchantment level (0–1)")
                .defineInRange("partyLeaderFeverChancePerLevel", 0.1, 0.0, 1.0);

        partyLeaderEffectDuration = BUILDER
                .comment("[Dux Festivus/Party Leader] Glowing/Sunday Fever effect duration (ticks)")
                .defineInRange("partyLeaderEffectDuration", 160, 0, 1919810);

        partyLeaderEffectDurationSundayBonus = BUILDER
                .comment("[Dux Festivus/Party Leader] Glowing/Sunday Fever effect duration bonus on Sunday (ticks)")
                .defineInRange("partyLeaderEffectDurationSundayBonus", 40, 0, 1919810);


        // Vlad Tepes Enchantment
        vladTepesEnabled = BUILDER
                .comment("[Vlad Tepes] If Disabled, it will not take effect.")
                .define("vladTepesEnabled", true);
        vladTepesBaseManaCost = BUILDER
                .comment("[Vlad Tepes] Mana Cost = BaseManaCost + (enchantmentLevel-1) * ManaCostPerLevel")
                .defineInRange("vladTepesBaseManaCost", 12.5, 0.0, Integer.MAX_VALUE);
        vladTepesManaCostPerLevel = BUILDER
                .comment("[Vlad Tepes] Mana Cost = BaseManaCost + (enchantmentLevel-1) * ManaCostPerLevel")
                .defineInRange("vladTepesManaCostPerLevel", 5, 0.0, Integer.MAX_VALUE);
        vladTepesBaseBloodNeedleDamage = BUILDER
                .comment("[Vlad Tepes] Needle Damage = BaseNeedleDamage + (enchantmentLevel-1) * NeedleDamagePerLevel + BloodSpellPower * PowerScaling")
                .defineInRange("vladTepesBaseBloodNeedleDamage", 2, 0.0, Integer.MAX_VALUE);
        vladTepesBloodNeedleDamagePerLevel = BUILDER
                .comment("[Vlad Tepes] Needle Damage = BaseNeedleDamage + (enchantmentLevel-1) * NeedleDamagePerLevel + BloodSpellPower * PowerScaling")
                .defineInRange("vladTepesBloodNeedleDamagePerLevel", 0.25, 0.0, Integer.MAX_VALUE);
        vladTepesBloodNeedleDamageBloodSpellPowerScaling = BUILDER
                .comment("[Vlad Tepes] Needle Damage = BaseNeedleDamage + (enchantmentLevel-1) * NeedleDamagePerLevel + BloodSpellPower * PowerScaling")
                .defineInRange("vladTepesBloodNeedleDamageBloodSpellPowerScaling", 0.25, 0.0, Integer.MAX_VALUE);
        vladTepesBaseBloodNeedleCount = BUILDER
                .comment("[Vlad Tepes] Needle Count = BaseNeedleCount + (enchantmentLevel-1) * NeedleCountPerLevel")
                .defineInRange("vladTepesBaseBloodNeedleCount", 1, 0, Integer.MAX_VALUE);
        vladTepesBloodNeedleCountPerLevel = BUILDER
                .comment("[Vlad Tepes] Needle Count = BaseNeedleCount + (enchantmentLevel-1) * NeedleCountPerLevel")
                .defineInRange("vladTepesBloodNeedleCountPerLevel", 1, 0, Integer.MAX_VALUE);
        //Red Lotus Enchant
        redLotusEnabled = BUILDER
                .comment("[Lotus Ignis/Red Lotus] If Disabled, it will not take effect.")
                .define("redLotusEnabled", true);
        redLotusFireFieldBaseDamage = BUILDER
                .comment("[Lotus Ignis/Red Lotus] AOE Damage = BaseDamage + (enchantmentLevel-1) * DamagePerLevel")
                .defineInRange("redLotusFireFieldBaseDamage", 1.0, 0, Integer.MAX_VALUE);
        redLotusFireFieldDamagePerLevel = BUILDER
                .comment("[Lotus Ignis/Red Lotus] AOE Damage = BaseDamage + (enchantmentLevel-1) * DamagePerLevel")
                .defineInRange("redLotusFireFieldDamagePerLevel", 0.75, 0, Integer.MAX_VALUE);
        redLotusFireFieldBaseRadius = BUILDER
                .comment("[Lotus Ignis/Red Lotus] Radius = BaseRadius + (enchantmentLevel-1) * RadiusPerLevel")
                .defineInRange("redLotusFireFieldBaseRadius", 2.0, 1.0, Integer.MAX_VALUE);
        redLotusFireFieldRadiusPerLevel = BUILDER
                .comment("[Lotus Ignis/Red Lotus] Radius = BaseRadius + (enchantmentLevel-1) * RadiusPerLevel")
                .defineInRange("redLotusFireFieldRadiusPerLevel", 0.5, 0, Integer.MAX_VALUE);
        redLotusFireFieldBaseDuration = BUILDER
                .comment("[Lotus Ignis/Red Lotus] Duration = BaseDuration + (enchantmentLevel-1) * DurationPerLevel")
                .defineInRange("redLotusFireFieldBaseDuration", 40, 20, Integer.MAX_VALUE);
        redLotusFireFieldDurationPerLevel = BUILDER
                .comment("[Lotus Ignis/Red Lotus] Duration = BaseDuration + (enchantmentLevel-1) * DurationPerLevel")
                .defineInRange("redLotusFireFieldDurationPerLevel", 20, 0, Integer.MAX_VALUE);

        //Mana Mending Enchant
        manaMendingEnabled = BUILDER
                .comment("[Mana Mending] If Disabled, it will not take effect.")
                .define("manaMendingEnabled", true);
        manaMendingThreshold = BUILDER
                .comment("[Mana Mending] Base Mana Cost to Trigger Mana Mending")
                .defineInRange("manaMendingThreshold", 30.0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        manaMendingCoefficient = BUILDER
                .comment("[Mana Mending] Durability Recovered per 100 Mana")
                .defineInRange("manaMendingCoefficient", 2.0, 0.01, Integer.MAX_VALUE);

        //Graveyard Keeper
        gentleGraveyardKeeperEnabled = BUILDER
                .comment("[Pax Sepulchri/Gentle Graveyard Keeper] If Disabled, it will not take effect.")
                .define("gentleGraveyardKeeperEnabled", true);
        //Nether Heart
        netherHeartEnabled = BUILDER
                .comment("[Cor Inferni/Nether Heart] If Disabled, it will not take effect.")
                .define("netherHeartEnabled", true);


        //Ice Fire Song
        iceFireSongEnabled = BUILDER
                .comment("[Melodia Gelu et Flammae] If Disabled, it will not take effect.")
                .define("iceFireSongEnabled", true);
        iceFireSongExtraDamageRatioPerLevel = BUILDER
                .comment("[Melodia Gelu et Flammae] Damage = Original Damage * (1.0 + enchantmentLevel * iceFireSongExtraDamageRatioPerLevel)")
                .defineInRange("iceFireSongExtraDamageRatioPerLevel", 0.16, 0, Integer.MAX_VALUE);




        //Necrovolt
        NecrovoltEnabled = BUILDER
                .comment("[Necrovolt] If Disabled, it will not take effect.")
                .define("NecrovoltEnabled", true);
        NecrovoltBaseDamage = BUILDER
                .comment("[Necrovolt] Damage = BaseDamage + (enchantmentLevel-1) * DamagePerLevel + LightningSpellPower * PowerScaling")
                .defineInRange("NecrovoltBaseDamage", 3.0, 0, Integer.MAX_VALUE);
        NecrovoltDamagePerLevel = BUILDER
                .comment("[Necrovolt] Damage = BaseDamage + (enchantmentLevel-1) * DamagePerLevel + LightningSpellPower * PowerScaling")
                .defineInRange("NecrovoltDamagePerLevel", 0.5, 0, Integer.MAX_VALUE);
        NecrovoltBaseRadius = BUILDER
                .comment("[Necrovolt] Radius = BaseRadius + (enchantmentLevel-1) * RadiusPerLevel")
                .defineInRange("NecrovoltBaseRadius", 2.0, 1.0, Integer.MAX_VALUE);
        NecrovoltRadiusPerLevel = BUILDER
                .comment("[Necrovolt] Radius = BaseRadius + (enchantmentLevel-1) * RadiusPerLevel")
                .defineInRange("NecrovoltRadiusPerLevel", 0.5, 0, Integer.MAX_VALUE);
        NecrovoltLightningSpellPowerScaling = BUILDER
                .comment("[Necrovolt] Damage = BaseDamage + (enchantmentLevel-1) * DamagePerLevel + LightningSpellPower * PowerScaling")
                .defineInRange("NecrovoltLightningSpellPowerScaling", 0.5, 0.0, Integer.MAX_VALUE);
        NecrovoltBonusDamageGrowthPerLevel = BUILDER
                .comment("[Necrovolt] Bonus Damage Growth Per Level After Killing One")
                .defineInRange("NecrovoltBonusDamageGrowth", 1.0, 0, Integer.MAX_VALUE);

        //Lich King
        lichKingEnabled = BUILDER
                .comment("[Dominion Helm/Lich King] If Disabled, it will not take effect.")
                .define("lichKingEnabled", true);
        lichKingUndeadSummonPossibilityPerLevel = BUILDER
                .comment("[Dominion Helm/Lich King] Undead Summoning Possibility Per Level.")
                .defineInRange("lichKingUndeadSummonPossibilityPerLevel", 0.15, 0, Integer.MAX_VALUE);
        lichKingUndeadDurationPerLevel = BUILDER
                .comment("[Dominion Helm/Lich King] Undead Duration in ticks")
                .defineInRange("lichKingUndeadDurationPerLevel", 800, 0, Integer.MAX_VALUE);


        //Zoo
        zoophonyEnabled = BUILDER
                .comment("[Zoophony] If Disabled, it will not take effect.")
                .define("zoophonyEnabled", true);

        SPEC = BUILDER.build();
    }


    /**
     * 计算法力消耗
     */
    public static double getVladTepesManaCost(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.vladTepesBaseManaCost.get();
        double perLevel = MajoSpellEnchantmentConfig.vladTepesManaCostPerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算血针伤害
     */
    public static double getVladTepesBloodNeedleRawDamage(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.vladTepesBaseBloodNeedleDamage.get();
        double perLevel = MajoSpellEnchantmentConfig.vladTepesBloodNeedleDamagePerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算血针数量
     */
    public static int getVladTepesBloodNeedleCount(int enchantmentLevel) {
        int base = MajoSpellEnchantmentConfig.vladTepesBaseBloodNeedleCount.get();
        int perLevel = MajoSpellEnchantmentConfig.vladTepesBloodNeedleCountPerLevel.get();
        int rawCount = base + (enchantmentLevel - 1) * perLevel;
        return min(8, rawCount);
    }


    /**
     * 计算火场伤害
     */
    public static double getRedLotusDamage(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.redLotusFireFieldBaseDamage.get();
        double perLevel = MajoSpellEnchantmentConfig.redLotusFireFieldDamagePerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算火场半径
     */
    public static double getRedLotusRadius(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.redLotusFireFieldBaseRadius.get();
        double perLevel = MajoSpellEnchantmentConfig.redLotusFireFieldRadiusPerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算火场时间
     */
    public static int getRedLotusDuration(int enchantmentLevel) {
        int base = MajoSpellEnchantmentConfig.redLotusFireFieldBaseDuration.get();
        int perLevel = MajoSpellEnchantmentConfig.redLotusFireFieldDurationPerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算死电伤害
     */
    public static double getNecrovoltRawDamage(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.NecrovoltBaseDamage.get();
        double perLevel = MajoSpellEnchantmentConfig.NecrovoltDamagePerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }

    /**
     * 计算死电半径
     */
    public static double getNecrovoltRadius(int enchantmentLevel) {
        double base = MajoSpellEnchantmentConfig.NecrovoltBaseRadius.get();
        double perLevel = MajoSpellEnchantmentConfig.NecrovoltRadiusPerLevel.get();
        return base + (enchantmentLevel - 1) * perLevel;
    }


}