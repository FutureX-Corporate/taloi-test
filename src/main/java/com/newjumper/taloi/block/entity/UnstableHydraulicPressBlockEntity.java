package com.newjumper.taloi.block.entity;

import com.newjumper.taloi.recipe.UnstablePressingRecipe;
import com.newjumper.taloi.screen.UnstableHydraulicPressMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class UnstableHydraulicPressBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private int litTime = 0;
    private int currentProgress = 0;
    private int maxProgress = 50;
    private final RecipeType<? extends UnstablePressingRecipe> recipeType = UnstablePressingRecipe.Type.INSTANCE;
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return UnstableHydraulicPressBlockEntity.this.litTime;
                case 1:
                    return UnstableHydraulicPressBlockEntity.this.currentProgress;
                case 2:
                    return UnstableHydraulicPressBlockEntity.this.maxProgress;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    UnstableHydraulicPressBlockEntity.this.litTime = value;
                    break;
                case 1:
                    UnstableHydraulicPressBlockEntity.this.currentProgress = value;
                    break;
                case 2:
                    UnstableHydraulicPressBlockEntity.this.maxProgress = value;
                    break;
            }
        }

        public int getCount() {
            return 3;
        }
    };

    public UnstableHydraulicPressBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.UNSTABLE_HYDRAULIC_PRESS.get(), pWorldPosition, pBlockState);
    }
    private boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.uhp");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new UnstableHydraulicPressMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("uhp.litTime", this.litTime);
        pTag.putInt("uhp.currentProgress", this.currentProgress);
        pTag.putInt("uhp.maxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.litTime = nbt.getInt("uhp.litTime");
        this.currentProgress = nbt.getInt("uhp.currentProgress");
        this.maxProgress = nbt.getInt("uhp.maxProgress");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @javax.annotation.Nullable Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, UnstableHydraulicPressBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.currentProgress++;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.currentProgress >= pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(UnstableHydraulicPressBlockEntity blockEntity) {
        Level level = blockEntity.level;
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<UnstablePressingRecipe> match = level.getRecipeManager().getRecipeFor(UnstablePressingRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canPress(inventory, match.get().getResultItem()) && hasFuel(blockEntity);
    }

    private static boolean hasFuel(UnstableHydraulicPressBlockEntity blockEntity) {
        return AbstractFurnaceBlockEntity.isFuel(blockEntity.itemHandler.getStackInSlot(0));
    }

    private static void craftItem(UnstableHydraulicPressBlockEntity blockEntity) {
        Level level = blockEntity.level;
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<UnstablePressingRecipe> match = level.getRecipeManager().getRecipeFor(UnstablePressingRecipe.Type.INSTANCE, inventory, level);
        if(match.isPresent()) {
            blockEntity.itemHandler.extractItem(0,1, false);
            blockEntity.itemHandler.extractItem(1,1, false);
            blockEntity.itemHandler.extractItem(2,1, false);
            blockEntity.itemHandler.extractItem(3,1, false);
            blockEntity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItem().getItem(), blockEntity.itemHandler.getStackInSlot(4).getCount() + 1));

            blockEntity.resetProgress();
        }
    }

    private void resetProgress() {
        this.currentProgress = 0;
    }

    private static boolean canPress(SimpleContainer container, ItemStack result) {
        return (container.getItem(4).getItem() == result.getItem() || container.getItem(4).isEmpty()) &&
               (container.getItem(4).getCount() < container.getItem(4).getMaxStackSize());
    }
}
