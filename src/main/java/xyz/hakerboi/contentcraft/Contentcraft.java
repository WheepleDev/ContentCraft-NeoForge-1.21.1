package xyz.hakerboi.contentcraft;

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
import xyz.hakerboi.contentcraft.config.ContentcraftConfig;

@Mod(Contentcraft.MOD_ID)
public class Contentcraft {
    public static final String MOD_ID = "contentcraft";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String STARTUP_TEXT = """
    
        ############################################
        ##                                        ##
        ##           |- Contentcraft -|           ##
        ##                V.%s                 ##
        ##            Made by Hakerboi            ##
        ##                                        ##
        ############################################""";

    public Contentcraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);


        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, ContentcraftConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Contentcraft.LOGGER.info(STARTUP_TEXT.formatted(version));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info(STARTUP_TEXT.formatted(version));
    }



    String version = ModList.get()
            .getModContainerById("contentcraft")
            .map(container -> container.getModInfo().getVersion().toString())
            .orElse("UNKNOWN");

}
