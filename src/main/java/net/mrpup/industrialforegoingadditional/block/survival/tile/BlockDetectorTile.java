package net.mrpup.industrialforegoingadditional.block.survival.tile;

import com.buuz135.industrial.block.tile.IndustrialAreaWorkingTile;
import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.buuz135.industrial.block.tile.RangeManager;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.mrpup.industrialforegoingadditional.config.machine.survival.BlockDetectorConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class BlockDetectorTile extends IndustrialAreaWorkingTile<BlockDetectorTile> {

    private static final int SLOT_COUNT = 18;

    private int getMaxProgress;
    private int getPowerPerOperation;

    @Save
    private SidedInventoryComponent<BlockDetectorTile> output;

    private int currentSignal = 0;

    public BlockDetectorTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.BLOCK_DETECTOR, RangeManager.RangeType.BEHIND, false,
                BlockDetectorConfig.powerPerOperation, blockPos, blockState);

        this.addInventory(this.output = (SidedInventoryComponent<BlockDetectorTile>)
                (new SidedInventoryComponent<BlockDetectorTile>("output", 54, 22, SLOT_COUNT, 0))
                        .setColor(DyeColor.ORANGE)
                        .setRange(6, 3)
                        .setInputFilter((stack, integer) -> true)
                        .setSlotLimit(15));

        this.getMaxProgress = BlockDetectorConfig.maxProgress;
        this.getPowerPerOperation = BlockDetectorConfig.powerPerOperation;
    }

    @Override
    public IndustrialWorkingTile<BlockDetectorTile>.WorkAction work() {
        if (this.hasEnergy(this.getPowerPerOperation)) {
            BlockPos pointed = this.getPointedBlockPos();

            if (this.isLoaded(pointed)) {
                int newSignal = 0;
                BlockState state = this.level.getBlockState(pointed);

                if (!state.isAir()) {
                    ItemStack blockAsItem = new ItemStack(state.getBlock().asItem());

                    for (int i = 0; i < SLOT_COUNT; i++) {
                        ItemStack slotStack = this.output.getStackInSlot(i);
                        if (!slotStack.isEmpty() && ItemStack.isSameItem(slotStack, blockAsItem)) {
                            newSignal = Math.min(15, slotStack.getCount());
                            break;
                        }
                    }
                }

                updateSignal(newSignal);
            }

            this.increasePointer();

            return new IndustrialWorkingTile.WorkAction(1.0F, this.getPowerPerOperation);
        }

        return new IndustrialWorkingTile.WorkAction(1.0F, 0);
    }

    private void updateSignal(int newSignal) {
        if (newSignal != currentSignal) {
            currentSignal = newSignal;
            this.level.updateNeighborsAt(this.worldPosition, this.getBlockState().getBlock());
            this.markForUpdate();
        }
    }

    @Override
    public void saveAdditional(net.minecraft.nbt.CompoundTag compound, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("CurrentSignal", this.currentSignal);
    }

    @Override
    public void loadAdditional(net.minecraft.nbt.CompoundTag compound, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        this.currentSignal = compound.getInt("CurrentSignal");
    }

    public int getSignal() {
        return currentSignal;
    }

    @Override
    protected EnergyStorageComponent<BlockDetectorTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(BlockDetectorConfig.maxStoredPower, 10, 20);
    }

    @Override
    public int getMaxProgress() {
        return this.getMaxProgress;
    }

    @Nonnull
    @Override
    public BlockDetectorTile getSelf() {
        return this;
    }
}