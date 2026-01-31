package xyz.wheeple.contentcraft.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModItems;

@EventBusSubscriber(modid = Contentcraft.MOD_ID)
public class GrantGuidebookOnFirstJoin {

    private static final String GUIDEBOOK_RECEIVED_TAG = "contentcraftGuidebookReceived";

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // Only give the guidebook if the player is in Survival or Adventure mode
        GameType mode = player.gameMode.getGameModeForPlayer();
        if (mode != GameType.SURVIVAL && mode != GameType.ADVENTURE) return;

        var data = player.getPersistentData();
        if (data.getBoolean(GUIDEBOOK_RECEIVED_TAG)) return;

        // Create an ItemStack of your custom guidebook
        ItemStack guidebook = new ItemStack(ModItems.CONTENTCRAFT_GUIDE.get());

        // Give the guidebook to the player
        boolean success = player.addItem(guidebook);
        if (success) {
            Contentcraft.LOGGER.info("Gave ContentCraft guidebook to {}", player.getName().getString());
        } else {
            Contentcraft.LOGGER.warn("Failed to give ContentCraft guidebook to {} (inventory full?)", player.getName().getString());
        }

        // Mark as received
        data.putBoolean(GUIDEBOOK_RECEIVED_TAG, true);
    }
}
