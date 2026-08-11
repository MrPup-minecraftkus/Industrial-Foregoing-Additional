package net.mrpup.industrialforegoingadditional.recipe.survival;

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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import java.util.ArrayList;
import java.util.List;

public class SolidifierRecipe extends SerializableRecipe {

    public static List<SerializableRecipe> RECIPES = new ArrayList<>();

    public Ingredient.Value[] input;
    public FluidStack inputFluid;
    public int processingTime;
    public ItemStack output;

    public SolidifierRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public SolidifierRecipe(ResourceLocation resourceLocation, Ingredient.Value[] input, FluidStack inputFluid1, FluidStack inputFluid2, int processingTime, ItemStack output, FluidStack outputFluid) {
        super(resourceLocation);
        this.input = input;
        this.inputFluid = inputFluid;
        this.processingTime = processingTime;
        this.output = output;
        this.output.getItem().onCraftedBy(this.output, null, null);
        RECIPES.add(this);
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    public static ResourceLocation generateRL(String key) {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "solidifier/" + key);
    }


    public boolean matches(IItemHandler handler, FluidTankComponent fluidTank) {
        if (this.input != null && fluidTank != null && this.inputFluid != null) {
            List<ItemStack> handlerItems = new ArrayList<>();

            for (int i = 0; i < handler.getSlots(); ++i) {
                if (!handler.getStackInSlot(i).isEmpty()) {
                    handlerItems.add(handler.getStackInSlot(i).copy());
                }
            }

            for (Ingredient.Value iItemList : input) {
                boolean found = false;

                for (ItemStack stack : iItemList.getItems()) {
                    int matchIndex = -1;
                    for (int i = 0; i < handlerItems.size(); ++i) {
                        if (ItemStack.isSameItem(handlerItems.get(i), stack)) {
                            found = true;
                            matchIndex = i;
                            break;
                        }
                    }

                    if (found && matchIndex != -1) {
                        handlerItems.remove(matchIndex);
                        break;
                    }
                }

                if (!found) {
                    return false;
                }
            }

            return handlerItems.isEmpty()
                    && fluidTank.drainForced(this.inputFluid, IFluidHandler.FluidAction.SIMULATE).getAmount() == this.inputFluid.getAmount();
        } else {
            return false;
        }
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
        return (GenericSerializer<? extends SerializableRecipe>) ModuleCoreAdditional.SOLIDIFIER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.SOLIDIFIER_TYPE.get();
    }
}
