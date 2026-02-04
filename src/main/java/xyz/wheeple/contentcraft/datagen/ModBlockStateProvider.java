package xyz.wheeple.contentcraft.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import xyz.wheeple.contentcraft.Contentcraft;
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

        blockItem(ModBlocks.MAPLE_LOG);
        blockItem(ModBlocks.MAPLE_WOOD);
        blockItem(ModBlocks.STRIPPED_MAPLE_LOG);
        blockItem(ModBlocks.STRIPPED_MAPLE_WOOD);

        logBlock(((RotatedPillarBlock) ModBlocks.MAPLE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.MAPLE_WOOD.get()), blockTexture(ModBlocks.MAPLE_LOG.get()), blockTexture(ModBlocks.MAPLE_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()));


        blockWithItem(ModBlocks.MAPLE_PLANKS);

        leavesBlock(ModBlocks.MAPLE_LEAVES);
        saplingBlock(ModBlocks.MAPLE_SAPLING);



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
