package net.mrpup.industrialforegoingadditional.block.survival.tile;

/* Update

import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mrpup.industrialforegoingadditional.config.machine.survival.SifterMachineConfig;
import net.mrpup.industrialforegoingadditional.item.sifter.SifterItem;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.output.Chance;
import net.mrpup.industrialforegoingadditional.recipe.survival.SifterMachineRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidSifterMachineTile extends IndustrialProcessingTile<FluidSifterMachineTile> {
    private int maxProgress;
    private int powerPerTick;
    @Save
    private SidedInventoryComponent<FluidSifterMachineTile> inputFirst;
    @Save
    private SidedInventoryComponent<FluidSifterMachineTile> inputSecond;
    @Save
    private SidedFluidTankComponent<FluidSifterMachineTile> inputFluid1;
    @Save
    private SidedInventoryComponent<FluidSifterMachineTile> output;
    @Save
    private SidedFluidTankComponent<FluidSifterMachineTile> outputFluid;
    @Nullable
    private SifterMachineRecipe currentRecipe;

    public FluidSifterMachineTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.SIFTER_MACHINE, 102, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addInventory(this.inputFirst = (SidedInventoryComponent)(new SidedInventoryComponent("inputFirst", 35, 42, 1, 0)).setColor(DyeColor.BLUE).setSlotLimit(64).setOutputFilter((stack, integer) -> false).setOnSlotChanged((stack, integer) -> this.checkForRecipe()).setComponentHarness(this));
        this.addInventory(this.inputSecond = (SidedInventoryComponent)(new SidedInventoryComponent("inputSecond", 57, 42, 1, 1)).setColor(DyeColor.MAGENTA).setSlotLimit(1).setOutputFilter((stack, integer) -> false).setOnSlotChanged((stack, integer) -> this.checkForRecipe()).setComponentHarness(this));
        this.addTank(this.inputFluid1 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", SifterMachineConfig.maxInputTankSize, 57 + slotSpacing, 18 + slotSpacing, 2)).setColor(DyeColor.LIME).setTankType(FluidTankComponent.Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 129, 22, 3, 3)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.outputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("output_fluid", SifterMachineConfig.maxOutputTankSize, 149, 20, 4)).setColor(DyeColor.MAGENTA).setComponentHarness(this).setTankAction(FluidTankComponent.Action.DRAIN));

        this.maxProgress = SifterMachineConfig.maxProgress;
        this.powerPerTick = SifterMachineConfig.powerPerTick;
    }

    private void checkForRecipe() {
        if (this.isServer() && this.level != null) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.inputFirst, this.inputSecond, inputFluid1) ) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, ModuleCoreAdditional.SIFTER_MACHINE_TYPE.get())
                    .stream()
                    .filter(r -> r instanceof SifterMachineRecipe)
                    .map(r -> (SifterMachineRecipe) r)
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
        if (this.currentRecipe == null) return false;

        if (this.currentRecipe.outputs != null && !this.currentRecipe.outputs.isEmpty()) {
            ItemStack firstOutput = this.currentRecipe.outputs.get(0).stack();
            if (!ItemHandlerHelper.insertItem(this.output, firstOutput.copy(), true).isEmpty()) {
                return false;
            }
        }

        return this.currentRecipe.outputFluid.isEmpty()
                || this.outputFluid.fillForced(this.currentRecipe.outputFluid.get().copy(), IFluidHandler.FluidAction.SIMULATE) == this.currentRecipe.outputFluid.get().getAmount();
    }

    public Runnable onFinish() {
        return () -> {
            SifterMachineRecipe sifterMachineRecipe = this.currentRecipe;

            if (sifterMachineRecipe != null) {
                if (sifterMachineRecipe.inputFluid1 != null) {

                    for (int i = 0; i < this.inputFirst.getSlots(); ++i) {
                        this.inputFirst.getStackInSlot(i).shrink(1);
                    }

                    int fluidToDrain = sifterMachineRecipe.inputFluid1.getAmount();
                    ItemStack sifterStack = this.inputSecond.getStackInSlot(0);
                    if (sifterStack.getItem() instanceof SifterItem sifterItem) {
                        fluidToDrain = sifterItem.getTier().getFluidUsage();
                    }

                    if (this.inputFluid1 != null) {
                        FluidStack drainStack = new FluidStack(sifterMachineRecipe.inputFluid1.getFluid(), fluidToDrain);
                        this.inputFluid1.drainForced(drainStack, IFluidHandler.FluidAction.EXECUTE);
                    }

                    if (!sifterStack.isEmpty() && sifterStack.isDamageableItem()) {
                        if (this.level instanceof ServerLevel serverLevel) {
                            sifterStack.hurtAndBreak(1, serverLevel, null, item -> {
                                this.inputSecond.setStackInSlot(0, ItemStack.EMPTY);
                            });
                        }
                    }

                    if (sifterMachineRecipe.outputFluid.isPresent() && !sifterMachineRecipe.outputFluid.get().isEmpty()) {
                        this.outputFluid.fillForced(sifterMachineRecipe.outputFluid.get().copy(), IFluidHandler.FluidAction.EXECUTE);
                    }

                    RandomSource random = this.level.getRandom();

                    for (Chance output : sifterMachineRecipe.outputs) {
                        if (random.nextFloat() <= output.chance()) {
                            ItemStack outputStack = output.stack().copy();
                            outputStack.getItem().onCraftedBy(outputStack, this.level, null);

                            ItemHandlerHelper.insertItem(this.output, outputStack, false);
                        }
                    }

                    this.checkForRecipe();
                }
            }
        };
    }

    protected EnergyStorageComponent<FluidSifterMachineTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(SifterMachineConfig.maxStoredPower, 10, 20);
    }

    protected int getTickPower() {
        return this.powerPerTick;
    }

    @Override
    public int getMaxProgress() {
        return this.currentRecipe != null ? this.currentRecipe.getDynamicProcessingTime(this.inputSecond) : this.maxProgress;
    }

    @Nonnull
    public FluidSifterMachineTile getSelf() {
        return this;
    }
}

 */