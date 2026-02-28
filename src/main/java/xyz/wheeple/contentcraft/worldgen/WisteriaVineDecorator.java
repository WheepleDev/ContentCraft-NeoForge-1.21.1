package xyz.wheeple.contentcraft.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModTreeDecorators;

public class WisteriaVineDecorator extends TreeDecorator {
    public static final MapCodec<WisteriaVineDecorator> CODEC = MapCodec.unit(new WisteriaVineDecorator());

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.WISTERIA_VINE_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        context.leaves().forEach(pos -> {
            if (random.nextFloat() < 0.65F) {
                BlockPos targetPos = pos.below();
                if (context.isAir(targetPos)) {
                    // random.nextInt(4) + 2 results in a length between 2 and 5 blocks
                    int length = random.nextInt(4) + 2;
                    for (int i = 0; i < length; i++) {
                        BlockPos currentPos = targetPos.below(i);
                        if (context.isAir(currentPos)) {
                            boolean isTip = (i == length - 1);

                            if (isTip) {
                                context.setBlock(currentPos, ModBlocks.WISTERIA_VINES.get().defaultBlockState()
                                        .setValue(net.minecraft.world.level.block.GrowingPlantHeadBlock.AGE, 0));
                            } else {
                                context.setBlock(currentPos, ModBlocks.WISTERIA_VINES_PLANT.get().defaultBlockState());
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        });
    }
}