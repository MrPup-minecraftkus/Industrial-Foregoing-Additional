package net.mrpup.industrialforegoingadditional.block.core.tile;

import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.UpgradedConstructorRecipe;
import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.bundle.LockableInventoryBundle;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Action;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent.Type;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.util.RecipeUtil;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.Pair;

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
        this.addTank(this.inputFluid1 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", DissolutionChamberConfig.maxInputTankSize, 33 + slotSpacing, 18 + slotSpacing, 1)).setColor(DyeColor.LIME).setTankType(Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 129, 22, 3, 2)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.outputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("output_fluid", DissolutionChamberConfig.maxOutputTankSize, 149, 20, 3)).setColor(DyeColor.MAGENTA).setComponentHarness(this).setTankAction(Action.DRAIN));

        this.maxProgress = DissolutionChamberConfig.maxProgress;
        this.powerPerTick = DissolutionChamberConfig.powerPerTick;
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
        return this.currentRecipe != null && ItemHandlerHelper.insertItem(this.output, ((ItemStack)this.currentRecipe.output.orElse(ItemStack.EMPTY)).copy(), true).isEmpty()
                && (this.currentRecipe.outputFluid.isEmpty() || this.outputFluid.fillForced(((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).copy(), FluidAction.SIMULATE) == ((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).getAmount());
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
                        this.inputFluid1.drainForced(upgradedConstructorRecipe.inputFluid1, FluidAction.EXECUTE);
                    }

                    if (upgradedConstructorRecipe.outputFluid.isPresent() && !upgradedConstructorRecipe.outputFluid.get().isEmpty()) {
                        this.outputFluid.fillForced(upgradedConstructorRecipe.outputFluid.get().copy(), FluidAction.EXECUTE);
                    }

                    ItemStack outputStack = upgradedConstructorRecipe.output.get().copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);

                    this.checkForRecipe();
                }
            }
        };
    }

    protected EnergyStorageComponent<UpgradedConstructorTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(DissolutionChamberConfig.maxStoredPower, 10, 20);
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

    public void loadSettings(Player player, CompoundTag tag) {
        if (tag.contains("UC_locked")) {
            this.input.setLocked(tag.getBoolean("UC_locked"));
        }

        if (tag.contains("UC_filter")) {
            for (String key : tag.getCompound("UC_filter").getAllKeys()) {
                this.input.getFilter()[Integer.parseInt(key)] =
                        ItemStack.parseOptional(this.level.registryAccess(), tag.getCompound("UC_filter").getCompound(key));
            }
        }
        super.loadSettings(player, tag);
    }

    public void saveSettings(Player player, CompoundTag tag) {
        tag.putBoolean("UC_locked", this.input.isLocked());
        CompoundTag filterTag = new CompoundTag();
        for (int i = 0; i < this.input.getFilter().length; i++) {
            if (this.input.getFilter()[i] != null) {
                filterTag.put(String.valueOf(i), this.input.getFilter()[i].saveOptional(this.level.registryAccess()));
            }
        }
        tag.put("UC_filter", filterTag);
        super.saveSettings(player, tag);
    }
}

