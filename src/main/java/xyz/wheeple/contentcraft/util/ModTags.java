package xyz.wheeple.contentcraft.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xyz.wheeple.contentcraft.Contentcraft;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_XAENON_TOOL = createTag("needs_xaenon_tool");
        public static final TagKey<Block> INCORRECT_FOR_XAENON_TOOL = createTag("incorrect_for_xaenon_tool");
        public static final TagKey<Block> HOSTILE_MOB_REPELLANT_BLOCKS = createTag("hostile_mob_repellent_blocks");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Contentcraft.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Contentcraft.MOD_ID, name));
        }
    }
}