package xyz.wheeple.contentcraft.recipe.alloyforge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlloyForgeRecipeInput(ItemStack[] input) implements RecipeInput {
    public ItemStack getItem(int pIndex) {
        return pIndex >= 0 && pIndex < this.input.length ? this.input[pIndex] : ItemStack.EMPTY;
    }

    public int size() {
        return this.input.length;
    }

    public ItemStack[] getInputs() {
        return this.input;
    }
}
