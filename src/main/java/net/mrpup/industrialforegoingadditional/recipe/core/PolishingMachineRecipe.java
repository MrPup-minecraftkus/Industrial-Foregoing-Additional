package net.mrpup.industrialforegoingadditional.recipe.core;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import java.util.ArrayList;
import java.util.List;

public class PolishingMachineRecipe extends SerializableRecipe {

    public static List<PolishingMachineRecipe> RECIPES = new ArrayList<>();


    public Ingredient.Value[] inputFirst, inputSecond;
    public FluidStack inputFluid1;
    public int processingTime;
    public ItemStack output;
    public FluidStack outputFluid;

    public PolishingMachineRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public PolishingMachineRecipe(ResourceLocation resourceLocation, Ingredient.Value[] input, FluidStack inputFluid1, FluidStack inputFluid2, int processingTime, ItemStack output, FluidStack outputFluid) {
        super(resourceLocation);
        this.inputFirst = inputFirst;
        this.inputSecond = inputSecond;
        this.inputFluid1 = inputFluid1;
        this.processingTime = processingTime;
        this.output = output;
        this.output.getItem().onCraftedBy(this.output, null, null);
        this.outputFluid = outputFluid;
        RECIPES.add(this);
    }


    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    public static ResourceLocation generateRL(String key) {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "polishing_machine/" + key);
    }

    public boolean matches(IItemHandler handler1, IItemHandler handler2, FluidTankComponent fluidTank) {
        if (this.inputFirst == null || this.inputSecond == null || this.inputFluid1 == null || fluidTank == null) {
            return false;
        }
        List<ItemStack> handlerItems1 = new ArrayList<>();

        for (int i = 0; i < handler1.getSlots(); ++i) {
            if (!handler1.getStackInSlot(i).isEmpty()) {
                handlerItems1.add(handler1.getStackInSlot(i).copy());
            }
        }

        List<ItemStack> handlerItems2 = new ArrayList<>();

        for (int i = 0; i < handler2.getSlots(); ++i) {
            if (!handler2.getStackInSlot(i).isEmpty()) {
                handlerItems2.add(handler2.getStackInSlot(i).copy());
            }
        }


        for (Ingredient.Value iItemList : inputFirst) {
            boolean matched = false;

            for (ItemStack stack : iItemList.getItems()) {
                int matchIndex = -1;
                for (int i = 0; i < handlerItems1.size(); ++i) {
                    if (ItemStack.isSameItem(handlerItems1.get(i), stack)) {
                        matched = true;
                        matchIndex = i;
                        break;
                    }
                }

                if (matched && matchIndex != -1) {
                    handlerItems1.remove(matchIndex);
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        for (Ingredient.Value iItemList : inputSecond) {
            boolean matched = false;

            for (ItemStack stack : iItemList.getItems()) {
                int matchIndex = -1;
                for (int i = 0; i < handlerItems2.size(); ++i) {
                    if (ItemStack.isSameItem(handlerItems2.get(i), stack)) {
                        matched = true;
                        matchIndex = i;
                        break;
                    }
                }

                if (matched && matchIndex != -1) {
                    handlerItems2.remove(matchIndex);
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        FluidStack tankFluid = fluidTank.getFluid();
        return tankFluid.getAmount() >= this.inputFluid1.getAmount();
    }


    @Override
    public ItemStack assemble(Container inv, RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return (GenericSerializer<? extends SerializableRecipe>) ModuleCoreAdditional.POLISHING_MACHINE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.POLISHING_MACHINE_TYPE.get();
    }
}

