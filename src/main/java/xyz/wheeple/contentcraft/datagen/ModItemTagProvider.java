package xyz.wheeple.contentcraft.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Contentcraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.SWORDS)
                .add(ModItems.XAENON_SWORD.get())
        
        ;
        
        tag(ItemTags.PICKAXES)
                .add(ModItems.XAENON_PICKAXE.get())
        
        ;
        
        tag(ItemTags.SHOVELS)
                .add(ModItems.XAENON_SHOVEL.get())
        
        ;
        
        tag(ItemTags.AXES)
                .add(ModItems.XAENON_AXE.get())
        
        ;
        
        tag(ItemTags.HOES)
                .add(ModItems.XAENON_HOE.get())
        
        ;
        
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.XAENON_HELMET.get())
                .add(ModItems.XAENON_CHESTPLATE.get())
                .add(ModItems.XAENON_LEGGINGS.get())
                .add(ModItems.XAENON_BOOTS.get())
        
        ;

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.XAENON_INGOT.get())
        
        ;


        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.MAPLE_LOG.get().asItem())
                .add(ModBlocks.MAPLE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get().asItem())
                .add(ModBlocks.WISTERIA_LOG.get().asItem())
                .add(ModBlocks.WISTERIA_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get().asItem())
        
        ;

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.MAPLE_PLANKS.asItem())
                .add(ModBlocks.WISTERIA_PLANKS.asItem())

        ;
    }
}
