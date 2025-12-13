package net.mrpup.industrialforegoingadditional.block.core.tile;

import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.buuz135.industrial.config.machine.misc.EnchantmentApplicatorConfig;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public class RepairMachineTile extends IndustrialProcessingTile<RepairMachineTile> {

    @Save
    private SidedInventoryComponent<RepairMachineTile> input;
    @Save
    private SidedFluidTankComponent<RepairMachineTile> tank;

    private final int ESSENCE_PER_REPAIR = 1;
    private final int DURABILITY_PER_REPAIR = 1;
    private final int ENERGY_PER_TICK = 10;

    public RepairMachineTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.REPAIR_MACHINE, -99999999, -99999999, blockPos, blockState);

        this.addInventory(this.input = (SidedInventoryComponent<RepairMachineTile>) new SidedInventoryComponent("input", 78, 40, 1, 0).setColor(DyeColor.BLUE).setSlotLimit(1).setOutputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.tank = (SidedFluidTankComponent)(new SidedFluidTankComponent("essence", EnchantmentApplicatorConfig.tankSize, 34, 20, 1)).setColor(DyeColor.LIME).setComponentHarness(this).setOnContentChange(() -> this.syncObject(this.tank)).setValidator((fluidStack) -> TagUtil.hasTag(BuiltInRegistries.FLUID, fluidStack.getFluid(), IndustrialTags.Fluids.EXPERIENCE)));
    }

    @Save
    private int progress = 0;

    public void tick() {
        if (!level.isClientSide) {
            if (canIncrease() && this.getEnergyStorage().getEnergyStored() >= 20) {
                this.progress++;

                if (this.progress >= getMaxProgress()) {
                    this.onFinish().run();
                    this.progress = 0;
                }
                syncObject(this);
            } else {
                if (this.progress != 0) {
                    this.progress = 0;
                    syncObject(this);
                }
            }
        }
    }

    public int getProgress() {
        return progress;
    }

    public void resetProgress() {
        progress = 0;
    }

    @Override
    public boolean canIncrease() {
        ItemStack item = this.input.getStackInSlot(0);
        return !item.isEmpty()
                && item.isDamaged()
                && this.tank.getFluidAmount() >= ESSENCE_PER_REPAIR;
    }

    @Override
    public Runnable onFinish() {
        return () -> {
            ItemStack item = this.input.getStackInSlot(0);
            if (!item.isEmpty() && item.isDamaged() && this.tank.getFluidAmount() >= ESSENCE_PER_REPAIR) {

                int damageToRepair = Math.min(DURABILITY_PER_REPAIR, item.getDamageValue());

                item.setDamageValue(item.getDamageValue() - damageToRepair);

                this.getEnergyStorage().extractEnergy(20, false);

                this.tank.drainForced(
                        new FluidStack(this.tank.getFluid().getFluid(), ESSENCE_PER_REPAIR),
                        FluidAction.EXECUTE
                );
            }
        };
    }

    protected EnergyStorageComponent<RepairMachineTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(DissolutionChamberConfig.maxStoredPower, 10, 20);
    }

    @Override
    public int getMaxProgress() {
        return 1;
    }


    @Override
    public int getTickPower() {
        return 10;
    }

    @Override
    @javax.annotation.Nonnull
    public RepairMachineTile getSelf() {
        return this;
    }
}
