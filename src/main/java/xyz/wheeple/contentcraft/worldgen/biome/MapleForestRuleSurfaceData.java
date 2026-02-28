package xyz.wheeple.contentcraft.worldgen.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class MapleForestRuleSurfaceData {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);

    // Reference your biome key here
    private static final ResourceKey<Biome> MAPLE_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("contentcraft", "maple_forest"));

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        // A mix of Podzol and Grass for that forest floor feel
        SurfaceRules.RuleSource forestSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                // Use noise to scatter Podzol and Coarse Dirt among the grass
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(net.minecraft.world.level.levelgen.Noises.SURFACE, 0.0D, 0.4D), PODZOL),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(net.minecraft.world.level.levelgen.Noises.SURFACE, 0.4D, 0.6D), COARSE_DIRT),
                                SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK),
                                DIRT
                        )
                )
        );

        return SurfaceRules.sequence(
                // Only apply these rules if we are in the Maple Forest
                SurfaceRules.ifTrue(SurfaceRules.isBiome(MAPLE_FOREST), forestSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}