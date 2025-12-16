package net.mrpup.industrialforegoingadditional.block.core.tile;

import net.mrpup.industrialforegoingadditional.config.machine.FactoryConstructorConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.FactoryConstructorRecipe;
import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
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

public class FactoryConstructorTile extends IndustrialProcessingTile<FactoryConstructorTile> {
    private int maxProgress;
    private int powerPerTick;
    @Save
    private LockableInventoryBundle<FactoryConstructorTile> input;
    @Save
    private SidedFluidTankComponent<FactoryConstructorTile> inputFluid1;
    @Save
    private SidedFluidTankComponent<FactoryConstructorTile> inputFluid2;
    @Save
    private SidedInventoryComponent<FactoryConstructorTile> output;
    @Save
    private SidedFluidTankComponent<FactoryConstructorTile> outputFluid;
    @Nullable
    private FactoryConstructorRecipe currentRecipe;

    public FactoryConstructorTile(BlockPos blockPos, BlockState blockState) {
        super(ModuleCoreAdditional.FACTORY_CONSTRUCTOR, 102, 41, blockPos, blockState);
        int slotSpacing = 22;

        this.addBundle(this.input = new LockableInventoryBundle(this, (new SidedInventoryComponent("input", 34, 19, 6, 0)).setColor(DyeColor.LIGHT_BLUE).setSlotPosition((integer) -> getSlotPos((Integer) integer)).setSlotLimit(1).setOutputFilter((stack, integer) -> false).setComponentHarness(this).setInputFilter((stack, integer) -> !this.canIncrease()).setOnSlotChanged((stack, integer) -> this.checkForRecipe()), 100, 64, false));
        this.addTank(this.inputFluid1 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", FactoryConstructorConfig.maxInputTankSize, 10 + slotSpacing, 18 + slotSpacing, 1)).setColor(DyeColor.LIME).setTankType(Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addTank(this.inputFluid2 = (SidedFluidTankComponent)(new SidedFluidTankComponent("input_fluid", FactoryConstructorConfig.maxInputTankSize, 56 + slotSpacing, 18 + slotSpacing, 1)).setColor(DyeColor.LIME).setTankType(Type.SMALL).setComponentHarness(this).setOnContentChange(() -> this.checkForRecipe()));
        this.addInventory(this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 129, 22, 3, 2)).setColor(DyeColor.ORANGE).setRange(1, 3).setInputFilter((stack, integer) -> false).setComponentHarness(this));
        this.addTank(this.outputFluid = (SidedFluidTankComponent)(new SidedFluidTankComponent("output_fluid", FactoryConstructorConfig.maxOutputTankSize, 149, 20, 3)).setColor(DyeColor.MAGENTA).setComponentHarness(this).setTankAction(Action.DRAIN));

        this.maxProgress = FactoryConstructorConfig.maxProgress;
        this.powerPerTick = FactoryConstructorConfig.powerPerTick;
    }

    private void checkForRecipe() {
        if (this.isServer() && this.level != null) {
            if (this.currentRecipe != null && this.currentRecipe.matches(this.input.getInventory(), inputFluid1, inputFluid2)) {
                return;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, ModuleCoreAdditional.FACTORY_CONSTRUCTOR_TYPE.get())
                    .stream()
                    .filter(r -> r instanceof FactoryConstructorRecipe)
                    .map(r -> (FactoryConstructorRecipe) r)
                    .filter(r -> r.matches(this.input.getInventory(), inputFluid1, inputFluid2))
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
        return this.currentRecipe != null && ItemHandlerHelper.insertItem(this.output, ((ItemStack)this.currentRecipe.output.orElse(ItemStack.EMPTY)).copy(), true).isEmpty() && (this.currentRecipe.outputFluid.isEmpty() || this.outputFluid.fillForced(((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).copy(), FluidAction.SIMULATE) == ((FluidStack)this.currentRecipe.outputFluid.orElse(FluidStack.EMPTY)).getAmount());
    }


    public Runnable onFinish() {
        return () -> {
            FactoryConstructorRecipe factoryConstructorRecipe = this.currentRecipe;

            if (factoryConstructorRecipe != null) {
                if (factoryConstructorRecipe.inputFluid1 != null && factoryConstructorRecipe.inputFluid2 != null) {

                    for (int i = 0; i < this.input.getInventory().getSlots(); ++i) {
                        this.input.getInventory().getStackInSlot(i).shrink(1);
                    }

                    if (this.inputFluid1 != null) {
                        this.inputFluid1.drainForced(factoryConstructorRecipe.inputFluid1, FluidAction.EXECUTE);
                    }

                    if (this.inputFluid2 != null) {
                        this.inputFluid2.drainForced(factoryConstructorRecipe.inputFluid2, FluidAction.EXECUTE);
                    }

                    if (factoryConstructorRecipe.outputFluid.isPresent() && !factoryConstructorRecipe.outputFluid.get().isEmpty()) {
                        this.outputFluid.fillForced(factoryConstructorRecipe.outputFluid.get().copy(), FluidAction.EXECUTE);
                    }

                    ItemStack outputStack = factoryConstructorRecipe.output.get().copy();
                    outputStack.getItem().onCraftedBy(outputStack, this.level, null);
                    ItemHandlerHelper.insertItem(this.output, outputStack, false);

                    this.checkForRecipe();
                }
            }
        };
    }





    protected EnergyStorageComponent<FactoryConstructorTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(FactoryConstructorConfig.maxStoredPower, 10, 20);
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
            case 1 -> { return Pair.of(slotSpacing, -offset); }
            case 2 -> { return Pair.of(slotSpacing * 2, 0); }
            case 3 -> { return Pair.of(0, slotSpacing * 2); }
            case 4 -> { return Pair.of(slotSpacing, slotSpacing * 2 + offset); }
            case 5 -> { return Pair.of(slotSpacing * 2, slotSpacing * 2); }
            default -> { return Pair.of(0, 0); }
        }
    }

    @Nonnull
    public FactoryConstructorTile getSelf() {
        return this;
    }

    public void loadSettings(Player player, CompoundTag tag) {
        if (tag.contains("FC_locked")) {
            this.input.setLocked(tag.getBoolean("FC_locked"));
        }

        if (tag.contains("FC_filter")) {
            for (String key : tag.getCompound("FC_filter").getAllKeys()) {
                this.input.getFilter()[Integer.parseInt(key)] =
                        ItemStack.parseOptional(this.level.registryAccess(), tag.getCompound("FC_filter").getCompound(key));
            }
        }
        super.loadSettings(player, tag);
    }

    public void saveSettings(Player player, CompoundTag tag) {
        tag.putBoolean("FC_locked", this.input.isLocked());
        CompoundTag filterTag = new CompoundTag();
        for (int i = 0; i < this.input.getFilter().length; i++) {
            if (this.input.getFilter()[i] != null) {
                filterTag.put(String.valueOf(i), this.input.getFilter()[i].saveOptional(this.level.registryAccess()));
            }
        }
        tag.put("FC_filter", filterTag);
        super.saveSettings(player, tag);
    }
}