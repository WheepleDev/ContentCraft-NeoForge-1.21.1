package xyz.wheeple.contentcraft.block.xaenon;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import xyz.wheeple.contentcraft.init.ModBlocks;

public class BuddingXaenonBlock extends AmethystBlock {
    public static final MapCodec<BuddingXaenonBlock> CODEC = simpleCodec(BuddingXaenonBlock::new);
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    public MapCodec<BuddingXaenonBlock> codec() {
        return CODEC;
    }

    public BuddingXaenonBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(GROWTH_CHANCE) == 0) {
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = null;

            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_XAENON_BUD.get();
            } else if (blockstate.is(ModBlocks.SMALL_XAENON_BUD) && blockstate.getValue(XaenonClusterBlock.FACING) == direction) {
                block = ModBlocks.MEDIUM_XAENON_BUD.get();
            } else if (blockstate.is(ModBlocks.MEDIUM_XAENON_BUD) && blockstate.getValue(XaenonClusterBlock.FACING) == direction) {
                block = ModBlocks.LARGE_XAENON_BUD.get();
            } else if (blockstate.is(ModBlocks.LARGE_XAENON_BUD) && blockstate.getValue(XaenonClusterBlock.FACING) == direction) {
                block = ModBlocks.XAENON_CLUSTER.get();
            }

            if (block != null) {
                BlockState newState = block.defaultBlockState()
                        .setValue(XaenonClusterBlock.FACING, direction)
                        .setValue(XaenonClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                level.setBlockAndUpdate(blockpos, newState);
            }
        }
    }


    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
