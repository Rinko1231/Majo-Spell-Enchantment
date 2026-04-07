package com.rinko1231.majospellenchantment.config;




import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

import static java.lang.Math.min;

public class MajoSpellEnchantmentConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue maxManaDisabled;

    public static ForgeConfigSpec.BooleanValue cdReductionDisabled;

    public static ForgeConfigSpec.BooleanValue manaReaperDisabled;
    public static ForgeConfigSpec.DoubleValue manaReaperRegenFactor;
    public static ForgeConfigSpec.DoubleValue manaReaperRegenCapPerLevel;

    public static ForgeConfigSpec.BooleanValue hopelessPowerDisabled;
    public static ForgeConfigSpec.DoubleValue hopelessPowerThresholdStart;
    public static ForgeConfigSpec.DoubleValue hopelessPowerThresholdMax;
    public static ForgeConfigSpec.DoubleValue hopelessPowerBonusPerLevel;

    public static ForgeConfigSpec.BooleanValue oceanGraceDisabled;
    public static ForgeConfigSpec.DoubleValue oceanGraceManaReductionPerLevel;

    public static ForgeConfigSpec.BooleanValue netherHeartDisabled;

    public static ForgeConfigSpec.BooleanValue phaseDashedDisabled;
    public static ForgeConfigSpec.BooleanValue phaseDashedTreasureOnly;
    public static ForgeConfigSpec.IntValue phaseDashedEffectDuration;

    public static ForgeConfigSpec.BooleanValue spellStreakDisabled;
    public static ForgeConfigSpec.BooleanValue spellStreakTreasureOnly;
    public static ForgeConfigSpec.DoubleValue spellStreakCDReductionPerLevel;
    public static ForgeConfigSpec.DoubleValue spellStreakEntityMinHealth;
    public static ForgeConfigSpec.BooleanValue spellStreakBlacklistOrWhitelist;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> spellStreakEntityBlacklist;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> spellStreakEntityWhitelist;

    public static ForgeConfigSpec.BooleanValue nocturneAriaDisabled;
    public static ForgeConfigSpec.DoubleValue nocturneAriaBonusPerLevel;
    public static ForgeConfigSpec.BooleanValue daylightAnthemDisabled;
    public static ForgeConfigSpec.DoubleValue daylightAnthemBonusPerLevel;

    public static ForgeConfigSpec.BooleanValue bloodManaDisabled;
    public static ForgeConfigSpec.BooleanValue bloodManaTreasureOnly;
    public static ForgeConfigSpec.DoubleValue bloodManaRatioPerLevel;
    public static ForgeConfigSpec.DoubleValue bloodManaCapPerLevel;

    public static ForgeConfigSpec.BooleanValue vladTepesDisabled;
    public static ForgeConfigSpec.BooleanValue vladTepesTreasureOnly;
    public static ForgeConfigSpec.DoubleValue vladTepesBaseManaCost;
    public static ForgeConfigSpec.DoubleValue vladTepesManaCostPerLevel;
    public static ForgeConfigSpec.DoubleValue vladTepesBaseBloodNeedleDamage;
    public static ForgeConfigSpec.DoubleValue vladTepesBloodNeedleDamagePerLevel;
    public static ForgeConfigSpec.DoubleValue vladTepesBloodNeedleDamageBloodSpellPowerScaling;
    public static ForgeConfigSpec.IntValue vladTepesBaseBloodNeedleCount;
    public static ForgeConfigSpec.IntValue vladTepesBloodNeedleCountPerLevel;

    public static ForgeConfigSpec.BooleanValue redLotusDisabled;
    public static ForgeConfigSpec.BooleanValue redLotusTreasureOnly;
    public static ForgeConfigSpec.DoubleValue redLotusFireFieldBaseDamage;
    public static ForgeConfigSpec.DoubleValue redLotusFireFieldDamagePerLevel;
    public static ForgeConfigSpec.DoubleValue redLotusFireFieldBaseRadius;
    public static ForgeConfigSpec.DoubleValue redLotusFireFieldRadiusPerLevel;
    public static ForgeConfigSpec.IntValue redLotusFireFieldBaseDuration;
    public static ForgeConfigSpec.IntValue redLotusFireFieldDurationPerLevel;

    public static ForgeConfigSpec.BooleanValue manaMendingDisabled;
    public static ForgeConfigSpec.BooleanValue manaMendingTreasureOnly;
    public static ForgeConfigSpec.BooleanValue manaMendingIncompatibleWithMending;
    public static ForgeConfigSpec.DoubleValue manaMendingThreshold;
    public static ForgeConfigSpec.DoubleValue manaMendingCoefficient;

    public static ForgeConfigSpec.BooleanValue NecrovoltDisabled;
    public static ForgeConfigSpec.BooleanValue NecrovoltTreasureOnly;
    public static ForgeConfigSpec.DoubleValue NecrovoltBaseDamage;
    public static ForgeConfigSpec.DoubleValue NecrovoltDamagePerLevel;
    public static ForgeConfigSpec.DoubleValue NecrovoltBaseRadius;
    public static ForgeConfigSpec.DoubleValue NecrovoltRadiusPerLevel;
    public static ForgeConfigSpec.DoubleValue NecrovoltLightningSpellPowerScaling;
    public static ForgeConfigSpec.DoubleValue NecrovoltBonusDamageGrowthPerLevel;

    public static ForgeConfigSpec.BooleanValue partyLeaderDisabled;
    public static ForgeConfigSpec.DoubleValue partyLeaderGlowChancePerLevel;
    public static ForgeConfigSpec.DoubleValue partyLeaderFeverChancePerLevel;
    public static ForgeConfigSpec.IntValue partyLeaderEffectDuration;
    public static ForgeConfigSpec.IntValue partyLeaderEffectDurationSundayBonus;

    public static ForgeConfigSpec.BooleanValue zoophonyDisabled;

    static {
        BUILDER.comment("Majo's Spell Config");




        BUILDER.push("Vas Mana/Max Mana");

        maxManaDisabled = BUILDER
                .define("maxManaDisabled", false);


        BUILDER.pop();
        BUILDER.push("Cantus Celeris/CD Reduction");

        cdReductionDisabled = BUILDER
                .define("cdReductionDisabled", false);


        BUILDER.pop();
        BUILDER.push("Messor Vitae/Mana Reaper");
        // Mana Reaper Enchantment
        manaReaperDisabled = BUILDER
                .define("manaReaperDisabled", false);
        manaReaperRegenFactor = BUILDER
                .comment("[Messor Vitae/Mana Reaper] Mana restoration factor per point of target's max health")
                .defineInRange("manaReaperRegenFactor", 1.0, 0.0, 100.0);
        manaReaperRegenCapPerLevel = BUILDER
                .comment("[Messor Vitae/Mana Reaper] Maximum mana restored per enchantment level")
                .defineInRange("manaReaperRegenCapPerLevel", 40.0, 0.0, 1000.0);

        BUILDER.pop();

        BUILDER.push("Potentia Desperata/Hopeless Power");

        // Hopeless Power Enchantment
        hopelessPowerDisabled = BUILDER
                .define("hopelessPowerDisabled", false);
        hopelessPowerThresholdStart = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] HP% below this starts increasing spell power (0–1)")
                .defineInRange("hopelessPowerThresholdStart", 0.7, 0.0, 1.0);
        hopelessPowerThresholdMax = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] HP% below this reaches maximum spell power bonus (0–1)")
                .defineInRange("hopelessPowerThresholdMax", 0.3, 0.0, 1.0);
        hopelessPowerBonusPerLevel = BUILDER
                .comment("[Potentia Desperata/Hopeless Power] Maximum spell power bonus per enchantment level (ADD_MULTIPLIED_BASE)")
                .defineInRange("hopelessPowerBonusPerLevel", 0.20, 0.0, 10.0);


        BUILDER.pop();

        BUILDER.push("Gratia Oceani/Ocean Grace");

        // Ocean Grace Enchantment
        oceanGraceDisabled = BUILDER
                .define("oceanGraceDisabled", false);
        oceanGraceManaReductionPerLevel = BUILDER
                .comment("[Gratia Oceani/Ocean Grace] Mana cost reduction per enchantment level")
                .defineInRange("oceanGraceManaReductionPerLevel", 0.1, 0.0, 1);


        BUILDER.pop();

        BUILDER.push("Cor Inferni/Nether Heart");

        netherHeartDisabled = BUILDER
                .define("netherHeartDisabled", false);


        BUILDER.pop();

        BUILDER.push("Cursus Phasium/Phase Dashed");


        // Phase Dashed Enchantment
        phaseDashedDisabled = BUILDER
                .define("phaseDashedDisabled", false);
        phaseDashedTreasureOnly = BUILDER
                .define("phaseDashedTreasureOnly", true);
        phaseDashedEffectDuration = BUILDER
                .comment("[Cursus Phasium/Phase Dashed] Effect duration in ticks after activation")
                .defineInRange("phaseDashedEffectDuration", 80, 0, 114514);


        BUILDER.pop();

        BUILDER.push("Mortis Impetus/Spell Streak");

        // Spell Streak Enchantment
        spellStreakDisabled = BUILDER
                .define("spellStreakDisabled", false);
        spellStreakTreasureOnly = BUILDER
                .define("spellStreakTreasureOnly", true);
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

        BUILDER.pop();

        BUILDER.push("Aria Noctis/Nocturne Aria");

        // Day Or Night Enchantment
        nocturneAriaDisabled = BUILDER
                .define("nocturneAriaDisabled", false);
        nocturneAriaBonusPerLevel = BUILDER
                .comment("[Aria Noctis/Nocturne Aria] Cast Time Reduction per level at night with clear sky")
                .defineInRange("nocturneAriaBonusPerLevel", 0.20, 0.0, 1.0);

        daylightAnthemDisabled = BUILDER
                .define("daylightAnthemDisabled", false);
        daylightAnthemBonusPerLevel = BUILDER
                .comment("[Hymnus Aurorae/Daylight Anthem] Cast Time Reduction per level during day with clear sky")
                .defineInRange("daylightAnthemBonusPerLevel", 0.20, 0.0, 1.0);


        BUILDER.pop();

        BUILDER.push("Sacramentum Sanguinis/Blood Mana");

        // Blood As Mana Enchantment

        bloodManaDisabled = BUILDER
                .define("bloodManaDisabled", false);
        bloodManaTreasureOnly = BUILDER
                .define("bloodManaTreasureOnly", true);
        bloodManaRatioPerLevel = BUILDER
                .comment("[Sacramentum Sanguinis/Blood Mana] Mana restored per damage point per level (default tuned to be ~2 Gluttony levels stronger)")
                .defineInRange("bloodManaRatioPerLevel", 3.0, 0.0, 114514);

        bloodManaCapPerLevel = BUILDER
                .comment("[Sacramentum Sanguinis/Blood Mana] Max mana restored per enchantment level")
                .defineInRange("bloodManaCapPerLevel", 40.0, 0.0, 1919810);

        BUILDER.pop();

        BUILDER.push("Vlad Tepes");
        vladTepesDisabled = BUILDER
                .define("vladTepesDisabled", false);
        vladTepesTreasureOnly = BUILDER
                .define("vladTepesTreasureOnly", false);
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


        BUILDER.pop();

        BUILDER.push("Lotus Ignis/Red Lotus");
        redLotusDisabled = BUILDER
                .define("redLotusDisabled", false);
        redLotusTreasureOnly = BUILDER
                .define("redLotusTreasureOnly", false);
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

        BUILDER.pop();

        BUILDER.push("Mana Mending");
        manaMendingDisabled = BUILDER
                .define("manaMendingDisabled", false);
        manaMendingTreasureOnly = BUILDER
                .define("manaMendingTreasureOnly", true);
        manaMendingIncompatibleWithMending = BUILDER
                .define("manaMendingIncompatibleWithMending", true);
        manaMendingThreshold = BUILDER
                .comment("[Mana Mending] Base Mana Cost to Trigger Mana Mending")
                .defineInRange("manaMendingThreshold", 30.0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        manaMendingCoefficient = BUILDER
                .comment("[Mana Mending] Durability Recovered per 100 Mana")
                .defineInRange("manaMendingCoefficient", 2.0, 0.01, Integer.MAX_VALUE);


        BUILDER.pop();

        BUILDER.push("Necrovolt");

        //Necrovolt
        NecrovoltDisabled = BUILDER
                .comment("[Necrovolt] If Disabled, it will not take effect.")
                .define("NecrovoltDisabled", false);
        NecrovoltTreasureOnly = BUILDER
                .define("NecrovoltTreasureOnly", true);
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

        BUILDER.pop();

        BUILDER.push("Dux Festivus/Party Leader");

        partyLeaderDisabled = BUILDER
                .define("partyLeaderDisabled", false);

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


        BUILDER.pop();

        BUILDER.push("Zoophony");

        // Zoophony Enchantment
        zoophonyDisabled = BUILDER
                .define("zoophonyDisabled", false);



        SPEC = BUILDER.build();
    }

    @SuppressWarnings("removal")
    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "MajoSpellEnchantmentConfig.toml");
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