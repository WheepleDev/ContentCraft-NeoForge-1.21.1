package xyz.wheeple.contentcraft;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import xyz.wheeple.contentcraft.block.alloyforge.AlloyForgeScreen;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModMenuTypes;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Contentcraft.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Contentcraft.MOD_ID, value = Dist.CLIENT)
public class ContentcraftClient {

    public ContentcraftClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_XAENON_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_XAENON_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_XAENON_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.XAENON_CLUSTER.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ALLOY_FORGE_MENU.get(), AlloyForgeScreen::new);

    }
}
