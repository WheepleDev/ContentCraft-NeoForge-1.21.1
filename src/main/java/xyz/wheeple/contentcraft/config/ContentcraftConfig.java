package xyz.wheeple.contentcraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ContentcraftConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG_MODE = BUILDER
            .comment("Enable debug logging")
            .define("debugMode", false);

    public static final ModConfigSpec.IntValue ALLOY_FORGE_MAX_LAVA = BUILDER
            .comment("Maximum lava (mB) the Alloy Forge can store")
            .defineInRange("maxLava", 64000, 1000, 1000000);

    public static final ModConfigSpec.IntValue ALLOY_FORGE_LAVA_PER_OPERATION = BUILDER
            .comment("Lava (mB) consumed per Alloy Forge operation")
            .defineInRange("lavaPerOperation", 500, 1, 10000);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
