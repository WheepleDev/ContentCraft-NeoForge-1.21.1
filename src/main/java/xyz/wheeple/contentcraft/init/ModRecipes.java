package xyz.wheeple.contentcraft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Contentcraft.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Contentcraft.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyForgeRecipe>> ALLOY_FORGE_SERIALIZER =
            SERIALIZERS.register("alloy_forge", AlloyForgeRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyForgeRecipe>> ALLOY_FORGE_TYPE =
            TYPES.register("alloy_forge", () -> new RecipeType<AlloyForgeRecipe>() {
                @Override
                public String toString() {
                    return "alloy_forge";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}