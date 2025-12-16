package net.mrpup.industrialforegoingadditional.block.core.tile;

import net.mrpup.industrialforegoingadditional.config.machine.PolishingMachineConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.PolishingMachineRecipe;
import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Action;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Type;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class PolishingMachineTile extends IndustrialProcessingTile<PolishingMachineTile> {
    private int maxProgress;
    private int powerPerTick;
    @Save
    private SidedInventoryComponent<PolishingMachineTile> inputFirst;
    @Save
    private SidedInventoryComponent<PolishingMachineTile> inputSecond;
    @Save
    private SidedFluidTankComponent<PolishingMachineTile> inputFluid1;
    @Save
    private SidedInventoryComponent<PolishingMachineTile> output;
    @Save
    private SidedFluidTankComponent<PolishingMachineTile> outputFluid;
    @Nullable
    private PolishingMachineRecipe currentRecipe;

    public PolishingMachineTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.POLISHING_MACHINE, 102, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addInventory(this.inputFirst = (SidedInventoryComponent)(new SidedInventoryComponent("inputFirst", 57, 30, 1, 0)).setColor(DyeColor.BLUE).setSlotLimit(64).setOutputFilter((stack, integer) -> false).setOnSlotChanged((stack, integer) -> this.checkForRecipe()).setComponentHarness(this));
        this.addInventory(this.inputSecond = (SidedInventoryComponent)(new SidedInventoryComponent("inputSecond", 57, 54, 1, 1)).setColor(DyeColor.MAGENTA).setSlotLimit(1).setOutputFilter((stack, integer) -> false).setOnSlotChanged((stack, integer) -> this.checkForRecipe()).setComponentHarness(this));
        this.addTank(this.inputFluid1 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", PolishingMachineConfig.maxInputTankSize, 57 + slotSpacing, 18 + slotSpacing, 2)).setColor(DyeColor.LIME).setTankType(Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 129, 22, 3, 3)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.outputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("output_fluid", PolishingMachineConfig.maxOutputTankSize, 149, 20, 4)).setColor(DyeColor.MAGENTA).setComponentHarness(this).setTankAction(Action.DRAIN));

        this.maxProgress = PolishingMachineConfig.maxProgress;
        this.powerPerTick = PolishingMachineConfig.powerPerTick;
    }

    private void checkForRecipe() {
        if (this.isServer() && this.level != null) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.inputFirst, this.inputSecond, inputFluid1) ) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, ModuleCoreAdditional.POLISHING_MACHINE_TYPE.get())
                    .stream()
                    .filter(r -> r instanceof PolishingMachineRecipe)
                    .map(r -> (PolishingMachineRecipe) r)
                    .filter(r -> r.matches(this.inputFirst, this.inputSecond, inputFluid1))
                    .findFirst()
                    .orElse(null);
        }
    }

    public void setChanged() {
        super.setChanged();
        this.checkForRecipe();
    }

    public void setLevel(Level level) {
        super.setLevel(level);
        this.checkForRecipe();
    }

    public boolean canIncrease() {
        return this.currentRecipe != null
                && ItemHandlerHelper.insertItem(this.output, ((ItemStack)this.currentRecipe.output.orElse(ItemStack.EMPTY)).copy(), true).isEmpty()
                && (this.currentRecipe.outputFluid.isEmpty()
                || this.outputFluid.fillForced(((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).copy(), FluidAction.SIMULATE) == ((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).getAmount());
    }

    public Runnable onFinish() {
        return () -> {
            PolishingMachineRecipe polishingMachineRecipe = this.currentRecipe;

            if (polishingMachineRecipe != null) {
                if (polishingMachineRecipe.inputFluid1 != null) {

                    for (int i = 0; i < this.inputFirst.getSlots(); ++i) {
                        this.inputFirst.getStackInSlot(i).shrink(1);
                    }
                    for (int i = 0; i < this.inputSecond.getSlots(); ++i) {
                        this.inputSecond.getStackInSlot(i).shrink(0);
                    }

                    if (this.inputFluid1 != null) {
                        this.inputFluid1.drainForced(polishingMachineRecipe.inputFluid1, FluidAction.EXECUTE);
                    }

                    if (polishingMachineRecipe.outputFluid.isPresent() && !polishingMachineRecipe.outputFluid.get().isEmpty()) {
                        this.outputFluid.fillForced(polishingMachineRecipe.outputFluid.get().copy(), FluidAction.EXECUTE);
                    }

                    ItemStack outputStack = polishingMachineRecipe.output.get().copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);

                    this.checkForRecipe();
                }
            }
        };
    }

    protected EnergyStorageComponent<PolishingMachineTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(PolishingMachineConfig.maxStoredPower, 10, 20);
    }

    protected int getTickPower() {
        return this.powerPerTick;
    }

    public int getMaxProgress() {
        return this.currentRecipe != null ? this.currentRecipe.processingTime : this.maxProgress;
    }

    @Nonnull
    public PolishingMachineTile getSelf() {
        return this;
    }
}
