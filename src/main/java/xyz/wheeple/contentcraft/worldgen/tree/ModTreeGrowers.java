package xyz.wheeple.contentcraft.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower MAPLE = new TreeGrower(Contentcraft.MOD_ID + ":maple",
            Optional.empty(), Optional.of(ModConfiguredFeatures.MAPLE_KEY), Optional.empty());

}
