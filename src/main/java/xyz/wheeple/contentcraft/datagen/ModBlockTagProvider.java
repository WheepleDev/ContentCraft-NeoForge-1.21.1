package xyz.wheeple.contentcraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Contentcraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.XAENON_BLOCK.get())
                .add(ModBlocks.XAENON_CRYSTAL_BLOCK.get())
                .add(ModBlocks.BUDDING_XAENON_CRYSTAL_BLOCK.get())
                .add(ModBlocks.XAENON_CLUSTER.get())
                .add(ModBlocks.SMALL_XAENON_BUD.get())
                .add(ModBlocks.MEDIUM_XAENON_BUD.get())
                .add(ModBlocks.LARGE_XAENON_BUD.get())
                .add(ModBlocks.ALLOY_FORGE.get())


        ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.XAENON_CRYSTAL_BLOCK.get())
                .add(ModBlocks.XAENON_CLUSTER.get())



                ;
    }
}
