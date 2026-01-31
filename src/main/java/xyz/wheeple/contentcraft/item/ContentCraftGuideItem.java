package xyz.wheeple.contentcraft.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import vazkii.patchouli.api.PatchouliAPI;

public class ContentCraftGuideItem extends Item {

    public ContentCraftGuideItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player instanceof ServerPlayer serverPlayer) {
            PatchouliAPI.get().openBookGUI(serverPlayer, net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(this));
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
