package xyz.wheeple.contentcraft.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.block.alloyforge.AlloyForgeBlockEntity;
import xyz.wheeple.contentcraft.config.ContentcraftConfig;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipe;
import xyz.wheeple.contentcraft.screen.renderer.FluidTankRenderer;

import java.util.List;

public class AlloyForgeRecipeCategory implements IRecipeCategory<AlloyForgeRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("contentcraft", "alloy_forge");
    public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath("contentcraft", "textures/gui/alloy_forge/alloy_forge_gui.png");
    public static final RecipeType<AlloyForgeRecipe> RECIPE_TYPE = new RecipeType<>(UID, AlloyForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer renderer;
    public final int LAVASTACK_X = 26;
    public final int LAVASTACK_Y = 21;

    public AlloyForgeRecipeCategory(IGuiHelper helper) {
        // JEI 1.21+ uses helper.createDrawable. Width/Height should match your GUI top-part.
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 176, 81);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ALLOY_FORGE.get()));

        // Match these to your AlloyForgeBlockEntity constants
        this.renderer = new FluidTankRenderer(AlloyForgeBlockEntity.getMaxLava(), true, 16, 48);
    }

    public static int getLavaPerOperation() {
        return ContentcraftConfig.ALLOY_FORGE_LAVA_PER_OPERATION.get();
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
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyForgeRecipe recipe, IFocusGroup focuses) {
        // Inputs
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 30).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 48).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 30).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 48).addIngredients(recipe.getIngredients().get(3));

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 38).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    @Override
    public void draw(AlloyForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Render the fluid tank using your custom renderer
        FluidStack lavaStack = new FluidStack(Fluids.LAVA, getLavaPerOperation());
        renderer.render(guiGraphics, LAVASTACK_X, LAVASTACK_Y, lavaStack);

        // Optional text requirement
        Component text = Component.literal(getLavaPerOperation() + "mB per craft.");
        guiGraphics.drawString(Minecraft.getInstance().font, text, 8, 72, 0xFFFFFF, true);
    }

    @SuppressWarnings("removal")
    @Override
    public List<Component> getTooltipStrings(AlloyForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= LAVASTACK_X && mouseX <= LAVASTACK_X + renderer.getWidth() && mouseY >= LAVASTACK_Y && mouseY <= LAVASTACK_Y + renderer.getHeight()) {
            return renderer.getTooltip(new FluidStack(Fluids.LAVA, getLavaPerOperation()), Minecraft.getInstance().options.advancedItemTooltips ? net.minecraft.world.item.TooltipFlag.Default.ADVANCED : net.minecraft.world.item.TooltipFlag.Default.NORMAL);
        }
        return List.of();
    }
}