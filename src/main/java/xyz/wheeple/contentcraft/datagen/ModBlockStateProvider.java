package xyz.wheeple.contentcraft.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.block.wisteria.WisteriaVines;
import xyz.wheeple.contentcraft.init.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Contentcraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.XAENON_BLOCK);
        blockWithItem(ModBlocks.XAENON_CRYSTAL_BLOCK);
        blockWithItem(ModBlocks.BUDDING_XAENON_CRYSTAL_BLOCK);
        directionalBlock(
                ModBlocks.XAENON_CLUSTER.get(),
                models().getExistingFile(modLoc("block/xaenon_cluster"))
        );
        directionalBlock(
                ModBlocks.SMALL_XAENON_BUD.get(),
                models().getExistingFile(modLoc("block/small_xaenon_bud"))
        );
        directionalBlock(
                ModBlocks.MEDIUM_XAENON_BUD.get(),
                models().getExistingFile(modLoc("block/medium_xaenon_bud"))
        );
        directionalBlock(
                ModBlocks.LARGE_XAENON_BUD.get(),
                models().getExistingFile(modLoc("block/large_xaenon_bud"))
        );
        blockWithItem(ModBlocks.STEEL_BLOCK);

        blockWithItem(ModBlocks.MAPLE_PLANKS);

        blockItem(ModBlocks.MAPLE_LOG);
        blockItem(ModBlocks.MAPLE_WOOD);
        blockItem(ModBlocks.STRIPPED_MAPLE_LOG);
        blockItem(ModBlocks.STRIPPED_MAPLE_WOOD);

        logBlock(((RotatedPillarBlock) ModBlocks.MAPLE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.MAPLE_WOOD.get()), blockTexture(ModBlocks.MAPLE_LOG.get()), blockTexture(ModBlocks.MAPLE_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()));

        leavesBlock(ModBlocks.MAPLE_LEAVES);
        saplingBlock(ModBlocks.MAPLE_SAPLING);

        stairsBlock(ModBlocks.MAPLE_STAIRS.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        slabBlock(ModBlocks.MAPLE_SLAB.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        buttonBlock(ModBlocks.MAPLE_BUTTON.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        pressurePlateBlock(ModBlocks.MAPLE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        fenceBlock(ModBlocks.MAPLE_FENCE.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        fenceGateBlock(ModBlocks.MAPLE_FENCE_GATE.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.MAPLE_DOOR.get(), modLoc("block/maple_door_bottom"), modLoc("block/maple_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.MAPLE_TRAPDOOR.get(), modLoc("block/maple_trapdoor"), true, "cutout");

        blockItem(ModBlocks.MAPLE_STAIRS);
        blockItem(ModBlocks.MAPLE_SLAB);
        blockItem(ModBlocks.MAPLE_PRESSURE_PLATE);
        blockItem(ModBlocks.MAPLE_FENCE_GATE);
        blockItem(ModBlocks.MAPLE_TRAPDOOR, "_bottom");


        blockWithItem(ModBlocks.WISTERIA_PLANKS);

        blockItem(ModBlocks.WISTERIA_LOG);
        blockItem(ModBlocks.WISTERIA_WOOD);
        blockItem(ModBlocks.STRIPPED_WISTERIA_LOG);
        blockItem(ModBlocks.STRIPPED_WISTERIA_WOOD);

        logBlock(((RotatedPillarBlock) ModBlocks.WISTERIA_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.WISTERIA_WOOD.get()), blockTexture(ModBlocks.WISTERIA_LOG.get()), blockTexture(ModBlocks.WISTERIA_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_WISTERIA_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_WISTERIA_WOOD.get()), blockTexture(ModBlocks.STRIPPED_WISTERIA_LOG.get()), blockTexture(ModBlocks.STRIPPED_WISTERIA_LOG.get()));

        leavesBlock(ModBlocks.WISTERIA_LEAVES);
        saplingBlock(ModBlocks.WISTERIA_SAPLING);

        stairsBlock(ModBlocks.WISTERIA_STAIRS.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        slabBlock(ModBlocks.WISTERIA_SLAB.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        buttonBlock(ModBlocks.WISTERIA_BUTTON.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        pressurePlateBlock(ModBlocks.WISTERIA_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        fenceBlock(ModBlocks.WISTERIA_FENCE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        fenceGateBlock(ModBlocks.WISTERIA_FENCE_GATE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.WISTERIA_DOOR.get(), modLoc("block/wisteria_door_bottom"), modLoc("block/wisteria_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.WISTERIA_TRAPDOOR.get(), modLoc("block/wisteria_trapdoor"), true, "cutout");

        blockItem(ModBlocks.WISTERIA_STAIRS);
        blockItem(ModBlocks.WISTERIA_SLAB);
        blockItem(ModBlocks.WISTERIA_PRESSURE_PLATE);
        blockItem(ModBlocks.WISTERIA_FENCE_GATE);
        blockItem(ModBlocks.WISTERIA_TRAPDOOR, "_bottom");

        getVariantBuilder(ModBlocks.WISTERIA_VINES.get()).forAllStates(state -> {
            boolean isLit = state.getValue(WisteriaVines.LIT);
            // Use the registry name instead of the display name
            String name = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
            String suffix = isLit ? "_lit" : "";

            return ConfiguredModel.builder()
                    .modelFile(models().cross(name + suffix,
                            modLoc("block/" + name + suffix)).renderType("cutout"))
                    .build();
        });

        getVariantBuilder(ModBlocks.WISTERIA_VINES_PLANT.get()).forAllStates(state -> {
            boolean isLit = state.getValue(WisteriaVines.LIT);
            String name = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
            String suffix = isLit ? "_lit" : "";

            return ConfiguredModel.builder()
                    .modelFile(models().cross(name + suffix,
                            modLoc("block/" + name + suffix)).renderType("cutout"))
                    .build();
        });
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("contentcraft:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("contentcraft:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

}