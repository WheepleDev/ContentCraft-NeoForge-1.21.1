package xyz.wheeple.contentcraft;

import net.minecraft.resources.ResourceLocation;
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
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import xyz.wheeple.contentcraft.config.ContentcraftConfig;
import xyz.wheeple.contentcraft.init.*;
import xyz.wheeple.contentcraft.util.LangTooltips;
import xyz.wheeple.contentcraft.worldgen.biome.MapleForestRegion;
import xyz.wheeple.contentcraft.worldgen.biome.MapleForestRuleSurfaceData;
import xyz.wheeple.contentcraft.worldgen.biome.WisteriaGroveRegion;
import xyz.wheeple.contentcraft.worldgen.biome.WisteriaGroveRuleSurfaceData;

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

        Contentcraft.LOGGER.info("Registering Tooltips...");
        LangTooltips.init();


        ModTreeDecorators.TREE_DECORATOR_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, ContentcraftConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Contentcraft.LOGGER.info(STARTUP_TEXT.formatted(version));

        event.enqueueWork(() -> {
            Regions.register(new MapleForestRegion(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "overworld_maple"), 5));

            SurfaceRuleManager.addSurfaceRules(
                    SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, MapleForestRuleSurfaceData.makeRules());

            Regions.register(new WisteriaGroveRegion(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "overworld_wisteria"), 5));

            SurfaceRuleManager.addSurfaceRules(
                    SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, WisteriaGroveRuleSurfaceData.makeRules());
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }



    String version = ModList.get()
            .getModContainerById("contentcraft")
            .map(container -> container.getModInfo().getVersion().toString())
            .orElse("UNKNOWN");

}
