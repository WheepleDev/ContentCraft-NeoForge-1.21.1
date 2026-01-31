package xyz.wheeple.contentcraft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.block.alloyforge.AlloyForgeBlock;
import xyz.wheeple.contentcraft.block.xaenon.BuddingXaenonBlock;
import xyz.wheeple.contentcraft.block.xaenon.XaenonClusterBlock;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Contentcraft.MOD_ID);

    public static final DeferredBlock<Block> XAENON_BLOCK = registerBlock("xaenon_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> XAENON_CRYSTAL_BLOCK = registerBlock("xaenon_crystal_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> BUDDING_XAENON_CRYSTAL_BLOCK = registerBlock("budding_xaenon_crystal_block",
            () -> new BuddingXaenonBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .noLootTable()
                    .randomTicks()
            ));

    public static final DeferredBlock<XaenonClusterBlock> XAENON_CLUSTER = registerBlock("xaenon_cluster",
            () -> new XaenonClusterBlock(7.0F, 3.0F,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .forceSolidOn()
                            .noOcclusion()
                            .sound(SoundType.AMETHYST_CLUSTER)
                            .strength(1.5F)
                            .lightLevel(p -> 5)
                            .pushReaction(PushReaction.DESTROY)
            ));

    public static final DeferredBlock<XaenonClusterBlock> SMALL_XAENON_BUD = registerBlock("small_xaenon_bud",
            () -> new XaenonClusterBlock(3.0F, 4.0F,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .noOcclusion()
                            .strength(1f)
                            .sound(SoundType.AMETHYST_CLUSTER)
                            .noLootTable()
            ));

    public static final DeferredBlock<XaenonClusterBlock> MEDIUM_XAENON_BUD = registerBlock("medium_xaenon_bud",
            () -> new XaenonClusterBlock(4.0F, 3.0F,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .noOcclusion()
                            .strength(1f)
                            .sound(SoundType.AMETHYST_CLUSTER)
                            .noLootTable()
            ));

    public static final DeferredBlock<XaenonClusterBlock> LARGE_XAENON_BUD = registerBlock("large_xaenon_bud",
            () -> new XaenonClusterBlock(5.0F, 3.0F,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .noOcclusion()
                            .strength(1f)
                            .sound(SoundType.AMETHYST_CLUSTER)
                            .noLootTable()
            ));

    public static final DeferredBlock<Block> ALLOY_FORGE = registerBlock("alloy_forge",
            () -> new AlloyForgeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLAST_FURNACE)));











    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> deferred = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(deferred.get(), new Item.Properties()));
        return deferred;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
