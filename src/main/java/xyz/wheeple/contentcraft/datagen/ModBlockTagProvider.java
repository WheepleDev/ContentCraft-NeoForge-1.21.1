package xyz.wheeple.contentcraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.util.ModTags;

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
                .add(ModBlocks.STEEL_BLOCK.get())

        ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.XAENON_CRYSTAL_BLOCK.get())
                .add(ModBlocks.BUDDING_XAENON_CRYSTAL_BLOCK.get())
                .add(ModBlocks.XAENON_CLUSTER.get())
                .add(ModBlocks.SMALL_XAENON_BUD.get())
                .add(ModBlocks.MEDIUM_XAENON_BUD.get())
                .add(ModBlocks.LARGE_XAENON_BUD.get())

        ;

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.MAPLE_LOG.get())
                .add(ModBlocks.MAPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get())
                .add(ModBlocks.MAPLE_PLANKS.get())
                .add(ModBlocks.MAPLE_STAIRS.get())
                .add(ModBlocks.MAPLE_SLAB.get())
                .add(ModBlocks.MAPLE_FENCE.get())
                .add(ModBlocks.MAPLE_FENCE_GATE.get())
                .add(ModBlocks.MAPLE_PRESSURE_PLATE.get())
                .add(ModBlocks.MAPLE_BUTTON.get())
                .add(ModBlocks.WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_WOOD.get())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get())
                .add(ModBlocks.WISTERIA_PLANKS.get())
                .add(ModBlocks.WISTERIA_STAIRS.get())
                .add(ModBlocks.WISTERIA_SLAB.get())
                .add(ModBlocks.WISTERIA_FENCE.get())
                .add(ModBlocks.WISTERIA_FENCE_GATE.get())
                .add(ModBlocks.WISTERIA_PRESSURE_PLATE.get())
                .add(ModBlocks.WISTERIA_BUTTON.get())

        ;

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.MAPLE_LOG.get())
                .add(ModBlocks.MAPLE_WOOD.get())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get())
                .add(ModBlocks.WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_WOOD.get())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get())

        ;

        tag(BlockTags.PLANKS)
                .add(ModBlocks.MAPLE_PLANKS.get())
                .add(ModBlocks.WISTERIA_PLANKS.get())
        
        ;

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.MAPLE_SLAB.get())
                .add(ModBlocks.WISTERIA_SLAB.get())

        ;

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.MAPLE_STAIRS.get())
                .add(ModBlocks.WISTERIA_STAIRS.get())

        ;

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.MAPLE_PRESSURE_PLATE.get())
                .add(ModBlocks.WISTERIA_PRESSURE_PLATE.get())

        ;

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.MAPLE_BUTTON.get())
                .add(ModBlocks.WISTERIA_BUTTON.get())

        ;

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.MAPLE_FENCE.get())
                .add(ModBlocks.WISTERIA_FENCE.get())

        ;

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.MAPLE_FENCE_GATE.get())
                .add(ModBlocks.WISTERIA_FENCE_GATE.get())

        ;

        tag(BlockTags.CLIMBABLE)
                .add(ModBlocks.WISTERIA_VINES.get())
                .add(ModBlocks.WISTERIA_VINES_PLANT.get())

        ;

        tag(BlockTags.LEAVES)
                .add(ModBlocks.MAPLE_LEAVES.get())
                .add(ModBlocks.WISTERIA_LEAVES.get())

        ;

        tag(ModTags.Blocks.HOSTILE_MOB_REPELLANT_BLOCKS)
                .add(ModBlocks.WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_WOOD.get())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get())
                .add(ModBlocks.WISTERIA_PLANKS.get())
                .add(ModBlocks.WISTERIA_STAIRS.get())
                .add(ModBlocks.WISTERIA_SLAB.get())
                .add(ModBlocks.WISTERIA_FENCE.get())
                .add(ModBlocks.WISTERIA_FENCE_GATE.get())
                .add(ModBlocks.WISTERIA_PRESSURE_PLATE.get())
                .add(ModBlocks.WISTERIA_BUTTON.get())
                .add(ModBlocks.WISTERIA_LEAVES.get())
                .add(ModBlocks.WISTERIA_VINES.get())
                .add(ModBlocks.WISTERIA_VINES_PLANT.get())
        ;
    }
}