package xyz.wheeple.contentcraft.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import xyz.wheeple.contentcraft.block.alloyforge.AlloyForgeScreen;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModRecipes;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipe;

import java.util.List;

@JeiPlugin
public class JEIContentcraftPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("contentcraft", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlloyForgeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<AlloyForgeRecipe> alloyForgeRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.ALLOY_FORGE_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        registration.addRecipes(AlloyForgeRecipeCategory.RECIPE_TYPE, alloyForgeRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(
                AlloyForgeScreen.class,
                90, 34, 25, 20,
                AlloyForgeRecipeCategory.RECIPE_TYPE
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.ALLOY_FORGE.get().asItem()),
                AlloyForgeRecipeCategory.RECIPE_TYPE
        );
    }
}
