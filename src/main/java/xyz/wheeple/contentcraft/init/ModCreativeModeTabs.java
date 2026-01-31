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

    public static final Supplier<CreativeModeTab> CONTENTCRAFT_TAB =
            CREATIVE_TABS.register("contentcraft", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.contentcraft.Contentcraft"))
                            .icon(() -> new ItemStack(ModItems.XAENON_INGOT.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.CONTENTCRAFT_GUIDE.get());
                                output.accept(ModItems.XAENON_INGOT.get());
                                output.accept(ModItems.XAENON_SHARD.get());
                                output.accept(ModBlocks.XAENON_BLOCK.get());
                                output.accept(ModBlocks.XAENON_CRYSTAL_BLOCK.get());
                                output.accept(ModBlocks.BUDDING_XAENON_CRYSTAL_BLOCK.get());
                                output.accept(ModBlocks.SMALL_XAENON_BUD.get());
                                output.accept(ModBlocks.MEDIUM_XAENON_BUD.get());
                                output.accept(ModBlocks.LARGE_XAENON_BUD.get());
                                output.accept(ModBlocks.XAENON_CLUSTER.get());
                                output.accept(ModBlocks.ALLOY_FORGE.get());
                                output.accept(ModItems.NETHERITE_CHUNK.get());

                            })
                            .build()
            );

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }
}
