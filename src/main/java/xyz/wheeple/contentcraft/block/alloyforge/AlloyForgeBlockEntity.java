package xyz.wheeple.contentcraft.block.alloyforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.Nullable;
import xyz.wheeple.contentcraft.config.ContentcraftConfig;
import xyz.wheeple.contentcraft.init.ModBlockEntities;
import xyz.wheeple.contentcraft.init.ModRecipes;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipe;
import xyz.wheeple.contentcraft.recipe.alloyforge.AlloyForgeRecipeInput;

import java.util.Optional;

public class AlloyForgeBlockEntity extends BlockEntity implements MenuProvider {

    public static final int INPUT_SLOT_0 = 0;
    public static final int INPUT_SLOT_1 = 1;
    public static final int INPUT_SLOT_2 = 2;
    public static final int INPUT_SLOT_3 = 3;
    public static final int OUTPUT_SLOT  = 4;
    public static final int FLUID_SLOT   = 5;
    public static final int SLOT_COUNT   = 6;

    private static final int DEFAULT_MAX_PROGRESS = 300;

    public final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case OUTPUT_SLOT -> false;
                case FLUID_SLOT -> stack.getCapability(Capabilities.FluidHandler.ITEM) != null || stack.is(Items.LAVA_BUCKET);
                default -> true;
            };
        }
    };

    // --- Ranged Wrappers for Automation ---
    // Slots 0, 1, 2, 3 (The crafting inputs)
    private final IItemHandler inputHandler = new RangedWrapper(itemHandler, 0, 4);
    // Slot 4 (The output)
    private final IItemHandler outputHandler = new RangedWrapper(itemHandler, 4, 5);
    // Slot 5 (The fluid/bucket slot)
    private final IItemHandler fluidItemHandler = new RangedWrapper(itemHandler, 5, 6);

    private int progress = 0;
    private int maxProgress = DEFAULT_MAX_PROGRESS;

    private final FluidTank lavaTank = new FluidTank(getMaxLava()) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;
        }
    };

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> lavaTank.getFluidAmount();
                case 3 -> getMaxLava();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) progress = value;
            if (index == 1) maxProgress = value;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public AlloyForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FORGE_BLOCK_ENTITY.get(), pos, state);
    }

    /**
     * This is the logic that handles Hoppers/Pipes.
     * DOWN (Bottom) -> Output Slot
     * NORTH, SOUTH, EAST, WEST, UP -> Input Slots
     */
    public IItemHandler getItemHandler(@Nullable Direction side) {
        if (side == null) return itemHandler; // Internal/GUI access

        return switch (side) {
            case DOWN -> outputHandler;  // Bottom extracts output
            default -> inputHandler;     // All other sides (Top/Sides) insert ingredients
        };
    }

    public IFluidHandler getFluidHandler(@Nullable Direction side) {
        return lavaTank;
    }

    public FluidTank getLavaTank() {
        return this.lavaTank;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        saveAdditional(nbt, registries);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        CompoundTag nbt = pkt.getTag();
        if (nbt != null) {
            loadAdditional(nbt, registries);
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public static int getMaxLava() {
        return ContentcraftConfig.ALLOY_FORGE_MAX_LAVA.get();
    }

    public static int getLavaPerOperation() {
        return ContentcraftConfig.ALLOY_FORGE_LAVA_PER_OPERATION.get();
    }

    public boolean addLavaFromBucket(Player player, ItemStack stack) {
        if (stack.getItem() != Items.LAVA_BUCKET) return false;
        if (lavaTank.getFluidAmount() + 1000 > getMaxLava()) return false;

        lavaTank.fill(new FluidStack(Fluids.LAVA, 1000), IFluidHandler.FluidAction.EXECUTE);

        if (!player.isCreative()) {
            player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.BUCKET));
        }
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.contentcraft.alloy_forge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new AlloyForgeMenu(id, inv, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("max_progress", maxProgress);
        lavaTank.writeToNBT(registries, tag);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("max_progress");
        lavaTank.readFromNBT(registries, tag);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasFluidStackInSlot()) {
            transferFluidToTank();
        }

        if (hasRecipe() && isOutputSlotEmptyOrReceivable()) {
            progress++;
            if (!state.getValue(AlloyForgeBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(AlloyForgeBlock.LIT, true));
            }

            if (progress >= maxProgress) {
                craftItem();
                lavaTank.drain(getLavaPerOperation(), IFluidHandler.FluidAction.EXECUTE);
                resetProgress();
            }
        } else {
            if (progress > 0) resetProgress();
            if (state.getValue(AlloyForgeBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(AlloyForgeBlock.LIT, false));
            }
        }
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private boolean hasRecipe() {
        Optional<AlloyForgeRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        if (progress == 0) {
            maxProgress = recipe.get().getProgress();
        }

        ItemStack output = recipe.get().getResultItem(level.registryAccess());
        return lavaTank.getFluidAmount() >= getLavaPerOperation()
                && canInsertIntoOutput(output);
    }

    private Optional<AlloyForgeRecipe> getCurrentRecipe() {
        if (level == null) return Optional.empty();

        ItemStack[] inputs = {
                itemHandler.getStackInSlot(0),
                itemHandler.getStackInSlot(1),
                itemHandler.getStackInSlot(2),
                itemHandler.getStackInSlot(3)
        };

        return level.getRecipeManager()
                .getRecipeFor(ModRecipes.ALLOY_FORGE_TYPE.get(), new AlloyForgeRecipeInput(inputs), level)
                .map(RecipeHolder::value);
    }

    private void craftItem() {
        Optional<AlloyForgeRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack output = recipe.get().getResultItem(level.registryAccess());

        for (int i = 0; i < 4; i++) {
            itemHandler.extractItem(i, 1, false);
        }

        ItemStack current = itemHandler.getStackInSlot(OUTPUT_SLOT);
        if (current.isEmpty()) itemHandler.setStackInSlot(OUTPUT_SLOT, output.copy());
        else current.grow(output.getCount());
    }

    private boolean canInsertIntoOutput(ItemStack result) {
        ItemStack current = itemHandler.getStackInSlot(OUTPUT_SLOT);
        if (current.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(current, result)) return false;
        return current.getCount() + result.getCount() <= current.getMaxStackSize();
    }

    private boolean hasFluidStackInSlot() {
        ItemStack stack = itemHandler.getStackInSlot(FLUID_SLOT);
        if (stack.isEmpty()) return false;
        var cap = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return cap != null && cap.getFluidInTank(0).getFluid() == Fluids.LAVA;
    }

    private void transferFluidToTank() {
        ItemStack stack = itemHandler.getStackInSlot(FLUID_SLOT);
        if (stack.isEmpty()) return;

        FluidActionResult result = FluidUtil.tryEmptyContainer(
                stack,
                lavaTank,
                getMaxLava() - lavaTank.getFluidAmount(),
                null,
                true
        );

        if (result.isSuccess()) {
            itemHandler.setStackInSlot(FLUID_SLOT, result.getResult());
        }
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        ItemStack stack = itemHandler.getStackInSlot(OUTPUT_SLOT);
        return stack.isEmpty() || stack.getCount() < stack.getMaxStackSize();
    }
}