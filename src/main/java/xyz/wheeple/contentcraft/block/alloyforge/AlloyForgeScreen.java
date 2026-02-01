package xyz.wheeple.contentcraft.block.alloyforge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.screen.renderer.FluidTankRenderer;
import xyz.wheeple.contentcraft.util.MouseUtil;

import java.util.Optional;

public class AlloyForgeScreen extends AbstractContainerScreen<AlloyForgeMenu> {

    private static final ResourceLocation GUI =
            ResourceLocation.fromNamespaceAndPath(
                    Contentcraft.MOD_ID,
                    "textures/gui/alloy_forge/alloy_forge_gui.png"
            );

    private static final ResourceLocation ARROW =
            ResourceLocation.fromNamespaceAndPath(
                    Contentcraft.MOD_ID,
                    "textures/gui/alloy_forge/arrow_progress.png"
            );

    private static final int FUEL_X = 26;
    private static final int FUEL_Y = 19;

    private final FluidTankRenderer fluidRenderer;

    public AlloyForgeScreen(AlloyForgeMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);

        // This will now correctly receive 64000
        this.fluidRenderer = new FluidTankRenderer(
                menu.getMaxLava(),
                true,
                16,
                50
        );
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        g.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);

        // Render Lava with a safety check
        FluidStack lava = menu.getFluid();
        if (fluidRenderer != null && !lava.isEmpty()) {
            fluidRenderer.render(g, x + FUEL_X, y + FUEL_Y, lava);
        }

        // Render Progress
        if (menu.isCrafting()) {
            g.blit(
                    ARROW,
                    x + 93,
                    y + 39,
                    0,
                    0,
                    menu.getScaledArrowProgress(),
                    16,
                    24,
                    16
            );
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (fluidRenderer != null) {
            renderFluidTooltip(g, mouseX, mouseY, x, y);
        }
    }

    private void renderFluidTooltip(GuiGraphics g, int mouseX, int mouseY, int guiX, int guiY) {
        if (isMouseOverFuelBar(mouseX, mouseY, guiX, guiY)) {
            g.renderTooltip(
                    this.font,
                    fluidRenderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL),
                    Optional.empty(),
                    mouseX - guiX,
                    mouseY - guiY
            );
        }
    }

    private boolean isMouseOverFuelBar(int mouseX, int mouseY, int guiX, int guiY) {
        return MouseUtil.isMouseOver(
                mouseX,
                mouseY,
                guiX + FUEL_X,
                guiY + FUEL_Y,
                fluidRenderer.getWidth(),
                fluidRenderer.getHeight()
        );
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        renderTooltip(g, mouseX, mouseY);
    }
}