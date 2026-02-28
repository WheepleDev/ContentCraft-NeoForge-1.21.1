package xyz.wheeple.contentcraft.item;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.wheeple.contentcraft.init.ModBlocks;

public class WisteriaPodItem extends BlockItem {

    public WisteriaPodItem(Item.Properties properties) {
        super(ModBlocks.WISTERIA_VINES.get(), properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() == Direction.DOWN) {
            return super.useOn(context);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        return context.getClickedFace() == Direction.DOWN && super.canPlace(context, state);
    }
}