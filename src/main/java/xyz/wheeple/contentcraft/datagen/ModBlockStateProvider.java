package xyz.wheeple.contentcraft.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
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





    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
