package xyz.wheeple.contentcraft.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
//    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BISMUTH_ORE_KEY = registerKey("bismuth_ore");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BISMUTH_ORE_KEY = registerKey("nether_bismuth_ore");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BISMUTH_ORE_KEY = registerKey("end_bismuth_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_KEY = registerKey("maple_key");

//    public static final ResourceKey<ConfiguredFeature<?, ?>> GOJI_BERRY_BUSH_KEY = registerKey("goji_berry_bush");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
//        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
//        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
//        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);
//
//        List<OreConfiguration.TargetBlockState> overworldBismuthOres = List.of(
//                OreConfiguration.target(stoneReplaceables, ModBlocks.BISMUTH_ORE.get().defaultBlockState()),
//                OreConfiguration.target(deepslateReplaceables, ModBlocks.BISMUTH_DEEPSLATE_ORE.get().defaultBlockState()));
//
//        register(context, OVERWORLD_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBismuthOres, 9));
//        register(context, NETHER_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
//                ModBlocks.BISMUTH_NETHER_ORE.get().defaultBlockState(), 9));
//        register(context, END_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
//                ModBlocks.BISMUTH_END_ORE.get().defaultBlockState(), 9));


        register(context, MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                // Trunk Provider (Maple Log)
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),

                // Straight Trunk Placer: base_height 4, height_rand_a 2, height_rand_b 0
                new StraightTrunkPlacer(4, 2, 0),

                // Foliage Provider (Maple Leaves)
                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES.get()),

                // Blob Foliage Placer: radius 2, offset 0, height 3
                new BlobFoliagePlacer(
                        ConstantInt.of(2), // radius
                        ConstantInt.of(0), // offset
                        3                  // height
                ),

                // Minimum Size: limit 1, lower_size 0, upper_size 1
                new TwoLayersFeatureSize(1, 0, 1)
        )
                .ignoreVines()
                .build());

//        register(context, GOJI_BERRY_BUSH_KEY, Feature.RANDOM_PATCH,
//                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
//                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GOJI_BERRY_BUSH.get()
//                                .defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))
//                        ), List.of(Blocks.GRASS_BLOCK)));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Contentcraft.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}