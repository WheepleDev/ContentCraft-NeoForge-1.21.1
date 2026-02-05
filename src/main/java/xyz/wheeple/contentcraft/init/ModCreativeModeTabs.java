package xyz.wheeple.contentcraft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.wheeple.contentcraft.Contentcraft;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Contentcraft.MOD_ID);

    // ITEMS TAB: Tools, Armor, and Guides
    public static final Supplier<CreativeModeTab> ITEMS_TAB =
            CREATIVE_TABS.register("items", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.contentcraft.items"))
                            .icon(() -> new ItemStack(ModItems.CONTENTCRAFT_GUIDE.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.CONTENTCRAFT_GUIDE.get());

                                output.accept(ModItems.XAENON_SWORD.get());
                                output.accept(ModItems.XAENON_PICKAXE.get());
                                output.accept(ModItems.XAENON_AXE.get());
                                output.accept(ModItems.XAENON_SHOVEL.get());
                                output.accept(ModItems.XAENON_HOE.get());

                                output.accept(ModItems.XAENON_HELMET.get());
                                output.accept(ModItems.XAENON_CHESTPLATE.get());
                                output.accept(ModItems.XAENON_LEGGINGS.get());
                                output.accept(ModItems.XAENON_BOOTS.get());
                            })
                            .build()
            );

    // BLOCKS TAB: All building blocks, machines, and wood
    public static final Supplier<CreativeModeTab> BLOCKS_TAB =
            CREATIVE_TABS.register("blocks", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.contentcraft.blocks"))
                            .icon(() -> new ItemStack(ModBlocks.STEEL_BLOCK.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModBlocks.ALLOY_FORGE.get());

                                output.accept(ModBlocks.XAENON_BLOCK.get());
                                output.accept(ModBlocks.XAENON_CRYSTAL_BLOCK.get());
                                output.accept(ModBlocks.BUDDING_XAENON_CRYSTAL_BLOCK.get());
                                output.accept(ModBlocks.SMALL_XAENON_BUD.get());
                                output.accept(ModBlocks.MEDIUM_XAENON_BUD.get());
                                output.accept(ModBlocks.LARGE_XAENON_BUD.get());
                                output.accept(ModBlocks.XAENON_CLUSTER.get());

                                output.accept(ModBlocks.STEEL_BLOCK.get());

                                output.accept(ModBlocks.MAPLE_LOG.get());
                                output.accept(ModBlocks.MAPLE_WOOD.get());
                                output.accept(ModBlocks.STRIPPED_MAPLE_LOG.get());
                                output.accept(ModBlocks.STRIPPED_MAPLE_WOOD.get());
                                output.accept(ModBlocks.MAPLE_PLANKS.get());
                                output.accept(ModBlocks.MAPLE_SAPLING.get());
                                output.accept(ModBlocks.MAPLE_LEAVES.get());
                            })
                            .build()
            );

    // MATERIALS TAB: Raw resources, ingots, and chunks
    public static final Supplier<CreativeModeTab> MATERIALS_TAB =
            CREATIVE_TABS.register("materials", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.contentcraft.materials"))
                            .icon(() -> new ItemStack(ModItems.XAENON_INGOT.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.XAENON_INGOT.get());
                                output.accept(ModItems.XAENON_SHARD.get());
                                output.accept(ModItems.STEEL_INGOT.get());
                                output.accept(ModItems.NETHERITE_CHUNK.get());
                            })
                            .build()
            );

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }
}