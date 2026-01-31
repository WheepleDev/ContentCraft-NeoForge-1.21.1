package xyz.wheeple.contentcraft.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipe;

public class AlloyForgeRecipeCategory implements IRecipeCategory<AlloyForgeRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("contentcraft", "alloy_forge");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("contentcraft", "textures/gui/alloy_forge/alloy_forge_gui_jei.png");
    public static final RecipeType<AlloyForgeRecipe> RECIPE_TYPE = new RecipeType<>(UID, AlloyForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public AlloyForgeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike) ModBlocks.ALLOY_FORGE.get()));
    }

    @Override
    public RecipeType<AlloyForgeRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.contentcraft.alloy_forge");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyForgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 30).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 48).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 30).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 48).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 38).addItemStack(recipe.getResultItem((HolderLookup.Provider)null));
    }
}
