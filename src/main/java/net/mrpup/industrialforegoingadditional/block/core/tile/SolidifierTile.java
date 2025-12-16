package net.mrpup.industrialforegoingadditional.block.core.tile;

import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mrpup.industrialforegoingadditional.config.machine.SolidifierConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.SolidifierRecipe;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SolidifierTile extends IndustrialProcessingTile<SolidifierTile> {

    @Save
    private SidedInventoryComponent<SolidifierTile> input;
    @Save
    private SidedFluidTankComponent<SolidifierTile> inputFluid;
    @Save
    private SidedInventoryComponent<SolidifierTile> output;

    @Nullable
    private SolidifierRecipe currentRecipe;

    public SolidifierTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.SOLIDIFIER, 108, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addInventory(this.input = (SidedInventoryComponent)(new SidedInventoryComponent("input", 74, 40, 1, 1)).setColor(DyeColor.BLUE).setSlotLimit(64).setOutputFilter((stack, integer) -> false).setOnSlotChanged((stack, integer) -> this.checkForRecipe()).setComponentHarness(this));
        this.addTank(this.inputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", SolidifierConfig.tankSize, 34, 20, 0)).setColor(DyeColor.LIME).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 148, 22, 3, 2)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
    }

    private void checkForRecipe() {
        if (this.isServer() && this.level != null) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.input, inputFluid)) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, ModuleCoreAdditional.SOLIDIFIER_TYPE.get())
                    .stream()
                    .filter(r -> r instanceof SolidifierRecipe)
                    .map(r -> (SolidifierRecipe) r)
                    .filter(r -> r.matches(this.input, inputFluid))
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
        return this.currentRecipe != null && ItemHandlerHelper.insertItem(this.output, ((ItemStack)this.currentRecipe.output.orElse(ItemStack.EMPTY)).copy(), true).isEmpty();
    }

    public Runnable onFinish() {
        return () -> {
            SolidifierRecipe SolidifierRecipe = this.currentRecipe;
            if (SolidifierRecipe != null) {
                if (SolidifierRecipe.inputFluid != null) {

                    for (int i = 0; i < this.input.getSlots(); ++i) {
                        this.input.getStackInSlot(i).shrink(1);
                    }

                    if (this.inputFluid != null) {
                        this.inputFluid.drainForced(SolidifierRecipe.inputFluid, FluidAction.EXECUTE);
                    }

                    ItemStack outputStack = SolidifierRecipe.output.get().copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);

                    this.checkForRecipe();
                }
            }
        };
    }

    protected EnergyStorageComponent<SolidifierTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(SolidifierConfig.maxStoredPower, 10, 20);
    }

    protected int getTickPower() {
        return SolidifierConfig.powerPerTick;
    }

    public int getMaxProgress() {
        return this.currentRecipe != null ? this.currentRecipe.processingTime : SolidifierConfig.maxProgress;
    }

    @Nonnull
    public SolidifierTile getSelf() {
        return this;
    }
}