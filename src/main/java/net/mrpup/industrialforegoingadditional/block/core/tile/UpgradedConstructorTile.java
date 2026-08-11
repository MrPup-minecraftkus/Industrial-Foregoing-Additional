package net.mrpup.industrialforegoingadditional.block.core.tile;

import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.bundle.LockableInventoryBundle;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Action;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Type;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.mrpup.industrialforegoingadditional.config.machine.core.UpgradedConstructorConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.core.UpgradedConstructorRecipe;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpgradedConstructorTile extends IndustrialProcessingTile<UpgradedConstructorTile> {

    private int maxProgress;
    private int powerPerTick;
    @Save
    private LockableInventoryBundle<UpgradedConstructorTile> input;
    @Save
    private SidedFluidTankComponent<UpgradedConstructorTile> inputFluid1;
    @Save
    private SidedInventoryComponent<UpgradedConstructorTile> output;
    @Save
    private SidedFluidTankComponent<UpgradedConstructorTile> outputFluid;
    @Nullable
    private UpgradedConstructorRecipe currentRecipe;

    public UpgradedConstructorTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.UPGRADED_CONSTRUCTOR, 102, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addBundle(this.input = new LockableInventoryBundle(this, (new SidedInventoryComponent("input", 34, 19, 4, 0)).setColor(DyeColor.LIGHT_BLUE).setSlotPosition((integer) -> getSlotPos((Integer) integer)).setSlotLimit(1).setOutputFilter((stack, integer) -> false).setComponentHarness(this).setInputFilter((stack, integer) -> !this.canIncrease()).setOnSlotChanged((stack, integer) -> this.checkForRecipe()), 100, 64, false));
        this.addTank(this.inputFluid1 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", UpgradedConstructorConfig.maxInputTankSize, 33 + slotSpacing, 18 + slotSpacing, 1)).setColor(DyeColor.LIME).setTankType(Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 129, 22, 3, 2)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.outputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("output_fluid", UpgradedConstructorConfig.maxOutputTankSize, 149, 20, 3)).setColor(DyeColor.MAGENTA).setComponentHarness(this).setTankAction(Action.DRAIN));

        this.maxProgress = UpgradedConstructorConfig.maxProgress;
        this.powerPerTick = UpgradedConstructorConfig.powerPerTick;
    }

    private void checkForRecipe() {
        if (this.isServer() && this.level != null) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.input.getInventory(), inputFluid1)) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, ModuleCoreAdditional.UPGRADED_CONSTRUCTOR_TYPE.get())
                    .stream()
                    .filter(r -> r instanceof UpgradedConstructorRecipe)
                    .map(r -> (UpgradedConstructorRecipe) r)
                    .filter(r -> r.matches(this.input.getInventory(), inputFluid1))
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
        return this.currentRecipe != null && ItemHandlerHelper.insertItem(output, currentRecipe.output.copy(), true).isEmpty()
                && (this.currentRecipe.outputFluid.isEmpty() || this.outputFluid.fillForced(currentRecipe.outputFluid.copy(), IFluidHandler.FluidAction.SIMULATE) == currentRecipe.outputFluid.getAmount());
    }

    public Runnable onFinish() {
        return () -> {
            UpgradedConstructorRecipe upgradedConstructorRecipe = this.currentRecipe;

            if (upgradedConstructorRecipe != null) {
                if (upgradedConstructorRecipe.inputFluid1 != null) {

                    for (int i = 0; i < this.input.getInventory().getSlots(); ++i) {
                        this.input.getInventory().getStackInSlot(i).shrink(1);
                    }

                    if (this.inputFluid1 != null) {
                        this.inputFluid1.drainForced(upgradedConstructorRecipe.inputFluid1, IFluidHandler.FluidAction.EXECUTE);
                    }

                    if (upgradedConstructorRecipe.outputFluid != null && !upgradedConstructorRecipe.outputFluid.isEmpty()) {
                        this.outputFluid.fillForced(upgradedConstructorRecipe.outputFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
                    }

                    ItemStack outputStack = upgradedConstructorRecipe.output.copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);

                    this.checkForRecipe();
                }
            }
        };
    }

    protected EnergyStorageComponent<UpgradedConstructorTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(UpgradedConstructorConfig.maxStoredPower, 10, 20);
    }

    protected int getTickPower() {
        return this.powerPerTick;
    }

    public int getMaxProgress() {
        return this.currentRecipe != null ? this.currentRecipe.processingTime : this.maxProgress;
    }

    public static Pair<Integer, Integer> getSlotPos(int slot) {
        int slotSpacing = 22;
        int offset = 2;
        switch (slot) {
            case 1 -> { return Pair.of(slotSpacing, slotSpacing * 2 + offset); }
            case 2 -> { return Pair.of(slotSpacing * 2, slotSpacing ); }
            case 3 -> { return Pair.of(0, slotSpacing ); }
            default -> { return Pair.of(slotSpacing, 0); }
        }
    }

    @Nonnull
    public UpgradedConstructorTile getSelf() {
        return this;
    }

    @Override
    public void loadSettings(Player player, CompoundTag tag) {
        if (tag.contains("FC_locked")) {
            this.input.setLocked(tag.getBoolean("FC_locked"));
        }

        if (tag.contains("DC_filter")) {
            for (String psFilter : tag.getCompound("DC_filter").getAllKeys()) {
                input.getFilter()[Integer.parseInt(psFilter)] = ItemStack.of(tag.getCompound("DC_filter").getCompound(psFilter));
            }
        }
        super.loadSettings(player, tag);
    }

    @Override
    public void saveSettings(Player player, CompoundTag tag) {
        tag.putBoolean("DC_locked", input.isLocked());
        CompoundTag filterTag = new CompoundTag();
        for (int i = 0; i < input.getFilter().length; i++) {
            filterTag.put(i + "", input.getFilter()[i].serializeNBT());
        }
        tag.put("DC_filter", filterTag);
        super.saveSettings(player, tag);
    }
}

