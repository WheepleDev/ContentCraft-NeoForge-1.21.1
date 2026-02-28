package xyz.wheeple.contentcraft.worldgen.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class WisteriaGroveRuleSurfaceData {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState());
    private static final ResourceKey<Biome> WISTERIA_GROVE = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("contentcraft", "wisteria_grove"));

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(WISTERIA_GROVE),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK)
                )
        );
    }
}