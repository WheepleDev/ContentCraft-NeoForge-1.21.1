package xyz.wheeple.contentcraft.init;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import xyz.wheeple.contentcraft.util.ModTags;

public class ModToolTiers {
    public static final Tier XAENON = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_XAENON_TOOL,
            1400, 4f, 3f, 28, () -> Ingredient.of(ModItems.XAENON_INGOT));

}