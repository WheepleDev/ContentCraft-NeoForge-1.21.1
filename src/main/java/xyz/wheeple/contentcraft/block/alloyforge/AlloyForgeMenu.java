package xyz.wheeple.contentcraft.block.alloyforge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModMenuTypes;

public class AlloyForgeMenu extends AbstractContainerMenu {
    private static final int BLOCK_SLOT_COUNT = 6;
    private static final int PLAYER_INV_START = BLOCK_SLOT_COUNT;
    private static final int PLAYER_INV_END   = PLAYER_INV_START + 36;

    private static final int OUTPUT_SLOT = 4;
    private static final int LAVA_SLOT = 5;

    public final AlloyForgeBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public AlloyForgeMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(4));
    }

    public AlloyForgeMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ALLOY_FORGE_MENU.get(), id);
        this.blockEntity = (AlloyForgeBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 55, 30));
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 55, 48));
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 73, 30));
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 3, 73, 48));

        this.addSlot(new OutputSlot(blockEntity.itemHandler, OUTPUT_SLOT, 124, 38));
        this.addSlot(new LavaSlot(blockEntity.itemHandler, LAVA_SLOT, 8, 53));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data);
    }


    public FluidStack getFluid() {
        // Pull directly from the BlockEntity's tank instead of the 'data' array
        return blockEntity.getLavaTank().getFluid();
    }

    public int getMaxLava() {
        // Pull directly from the BlockEntity instead of the 'data' array
        // This avoids the Short (32,767) limit of ContainerData
        return AlloyForgeBlockEntity.getMaxLava();
    }

    public boolean isCrafting() { return data.get(0) > 0; }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int max = data.get(1);
        return max > 0 && progress > 0 ? progress * 24 / max : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < PLAYER_INV_START) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (stack.getCapability(Capabilities.FluidHandler.ITEM) != null || stack.is(Items.LAVA_BUCKET)) {
                if (!moveItemStackTo(stack, LAVA_SLOT, LAVA_SLOT + 1, false)) {
                    if (!moveItemStackTo(stack, 0, 4, false)) return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack, 0, 4, false)) return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.ALLOY_FORGE.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 9; x++)
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int x = 0; x < 9; x++)
            this.addSlot(new Slot(inv, x, 8 + x * 18, 142));
    }

    private static class OutputSlot extends SlotItemHandler {
        public OutputSlot(IItemHandler handler, int index, int x, int y) { super(handler, index, x, y); }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }

    private static class LavaSlot extends SlotItemHandler {
        public LavaSlot(IItemHandler handler, int index, int x, int y) { super(handler, index, x, y); }
        @Override public boolean mayPlace(ItemStack stack) {
            return stack.getCapability(Capabilities.FluidHandler.ITEM) != null || stack.is(Items.LAVA_BUCKET);
        }
    }
}