package xyz.wheeple.contentcraft.recipe.alloyforge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import xyz.wheeple.contentcraft.init.ModRecipes;

public record AlloyForgeRecipe(
        Ingredient inputItem,
        Ingredient inputItem1,
        Ingredient inputItem2,
        Ingredient inputItem3,
        ItemStack output,
        int progress
) implements Recipe<AlloyForgeRecipeInput> {

    public int getProgress() {
        return progress;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        list.add(inputItem1);
        list.add(inputItem2);
        list.add(inputItem3);
        return list;
    }

    @Override
    public boolean matches(AlloyForgeRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        boolean[] used = new boolean[4];
        Ingredient[] ingredients = { inputItem, inputItem1, inputItem2, inputItem3 };

        for (Ingredient ingredient : ingredients) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                if (!used[i] && ingredient.test(input.getItem(i))) {
                    used[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(AlloyForgeRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOY_FORGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALLOY_FORGE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AlloyForgeRecipe> {

        public static final MapCodec<AlloyForgeRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(AlloyForgeRecipe::inputItem),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(AlloyForgeRecipe::inputItem1),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(AlloyForgeRecipe::inputItem2),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient3").forGetter(AlloyForgeRecipe::inputItem3),
                        ItemStack.CODEC.fieldOf("result").forGetter(AlloyForgeRecipe::output),
                        Codec.INT.optionalFieldOf("progress", 300).forGetter(AlloyForgeRecipe::progress)
                ).apply(inst, AlloyForgeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyForgeRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, AlloyForgeRecipe::inputItem,
                        Ingredient.CONTENTS_STREAM_CODEC, AlloyForgeRecipe::inputItem1,
                        Ingredient.CONTENTS_STREAM_CODEC, AlloyForgeRecipe::inputItem2,
                        Ingredient.CONTENTS_STREAM_CODEC, AlloyForgeRecipe::inputItem3,
                        ItemStack.STREAM_CODEC, AlloyForgeRecipe::output,
                        ByteBufCodecs.INT, AlloyForgeRecipe::progress,
                        AlloyForgeRecipe::new
                );


        @Override
        public MapCodec<AlloyForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyForgeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
