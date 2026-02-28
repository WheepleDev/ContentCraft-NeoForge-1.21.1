package xyz.wheeple.contentcraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALLOY_FORGE.get())
                .pattern("IBI")
                .pattern("IBI")
                .pattern("DDD")
                .define('D', Blocks.DEEPSLATE)
                .define('I', Items.IRON_INGOT)
                .define('B', Blocks.BLAST_FURNACE)
                .unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STEEL_BLOCK.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModItems.STEEL_INGOT.get())
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STEEL_INGOT.get(), 9)
                .requires(ModBlocks.STEEL_BLOCK)
                .unlockedBy("has_steel_block", has(ModBlocks.STEEL_BLOCK)).save(recipeOutput);

        stairBuilder(ModBlocks.MAPLE_STAIRS.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS)).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MAPLE_SLAB.get(), ModBlocks.MAPLE_PLANKS.get());

        buttonBuilder(ModBlocks.MAPLE_BUTTON.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS.get())).save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.MAPLE_PRESSURE_PLATE.get(), ModBlocks.MAPLE_PLANKS.get());

        fenceBuilder(ModBlocks.MAPLE_FENCE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS.get())).save(recipeOutput);
        fenceGateBuilder(ModBlocks.MAPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS.get())).save(recipeOutput);

        doorBuilder(ModBlocks.MAPLE_DOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS.get())).save(recipeOutput);
        trapdoorBuilder(ModBlocks.MAPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())).group("maple")
                .unlockedBy("has_maple_planks", has(ModBlocks.MAPLE_PLANKS.get())).save(recipeOutput);

        stairBuilder(ModBlocks.WISTERIA_STAIRS.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS)).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WISTERIA_SLAB.get(), ModBlocks.WISTERIA_PLANKS.get());

        buttonBuilder(ModBlocks.WISTERIA_BUTTON.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS.get())).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS.get())).save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.WISTERIA_PRESSURE_PLATE.get(), ModBlocks.WISTERIA_PLANKS.get());

        fenceBuilder(ModBlocks.WISTERIA_FENCE.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS.get())).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS.get())).save(recipeOutput);
        fenceGateBuilder(ModBlocks.WISTERIA_FENCE_GATE.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS.get())).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS.get())).save(recipeOutput);

        doorBuilder(ModBlocks.WISTERIA_DOOR.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS.get())).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS.get())).save(recipeOutput);
        trapdoorBuilder(ModBlocks.WISTERIA_TRAPDOOR.get(), Ingredient.of(ModBlocks.WISTERIA_PLANKS.get())).group("wisteria")
                .unlockedBy("has_wisteria_planks", has(ModBlocks.WISTERIA_PLANKS.get())).save(recipeOutput);


//        List<ItemLike> XAENON_SMELTABLES = List.of(ModItems.RAW_XAENON, ModBlocks.XAENON_ORE);
//
//        oreSmelting(recipeOutput, XAENON_SMELTABLES, RecipeCategory.MISC, ModItems.XAENON_INGOT.get(), 0.25f, 200, "xaenon");
//        oreBlasting(recipeOutput, XAENON_SMELTABLES, RecipeCategory.MISC, ModItems.XAENON_INGOT.get(), 0.25f, 100, "xaenon");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Contentcraft.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
