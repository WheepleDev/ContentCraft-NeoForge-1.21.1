package xyz.wheeple.contentcraft;

import net.neoforged.fml.ModList;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import xyz.wheeple.contentcraft.config.ContentcraftConfig;
import xyz.wheeple.contentcraft.init.*;

@Mod(Contentcraft.MOD_ID)
public class Contentcraft {
    public static final String MOD_ID = "contentcraft";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String STARTUP_TEXT = """
    
        ############################################
        ##                                        ##
        ##           |- Contentcraft -|           ##
        ##                V.%s                 ##
        ##            Made by Wheeple             ##
        ##                                        ##
        ############################################""";

    public Contentcraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        Contentcraft.LOGGER.info("Registering Items...");
        ModItems.register(modEventBus);

        Contentcraft.LOGGER.info("Registering Blocks...");
        ModBlocks.register(modEventBus);

        Contentcraft.LOGGER.info("Registering Creative Mode Tabs...");
        ModCreativeModeTabs.register(modEventBus);

        Contentcraft.LOGGER.info("Registering Block Entities...");
        ModBlockEntities.register(modEventBus);

        Contentcraft.LOGGER.info("Registering Menu Types...");
        ModMenuTypes.register(modEventBus);

        Contentcraft.LOGGER.info("Registering Recipes...");
        ModRecipes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, ContentcraftConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Contentcraft.LOGGER.info(STARTUP_TEXT.formatted(version));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }



    String version = ModList.get()
            .getModContainerById("contentcraft")
            .map(container -> container.getModInfo().getVersion().toString())
            .orElse("UNKNOWN");

}
