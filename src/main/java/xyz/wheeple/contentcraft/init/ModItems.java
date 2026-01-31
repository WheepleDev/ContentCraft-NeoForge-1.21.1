package xyz.wheeple.contentcraft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.item.ContentCraftGuideItem;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Contentcraft.MOD_ID);


    public static final DeferredItem<Item> XAENON_INGOT = ITEMS.register("xaenon_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> XAENON_SHARD = ITEMS.register("xaenon_shard",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> NETHERITE_CHUNK = ITEMS.register("netherite_chunk",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CONTENTCRAFT_GUIDE = ITEMS.register("contentcraft_guide",
            () -> new ContentCraftGuideItem(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
