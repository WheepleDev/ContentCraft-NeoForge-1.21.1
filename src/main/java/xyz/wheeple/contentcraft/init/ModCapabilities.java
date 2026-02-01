package xyz.wheeple.contentcraft.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import xyz.wheeple.contentcraft.Contentcraft;

@EventBusSubscriber(modid = Contentcraft.MOD_ID)
public class ModCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.ALLOY_FORGE_BLOCK_ENTITY.get(),
                (be, side) -> be.getItemHandler(side)
        );

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.ALLOY_FORGE_BLOCK_ENTITY.get(),
                (be, side) -> be.getFluidHandler(side)
        );
    }
}