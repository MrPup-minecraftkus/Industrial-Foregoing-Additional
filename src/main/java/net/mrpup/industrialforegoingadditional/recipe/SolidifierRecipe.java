package net.mrpup.industrialforegoingadditional.recipe;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SolidifierRecipe implements Recipe<CraftingInput> {

    public static final MapCodec<SolidifierRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> in.group(
            Ingredient.CODEC.listOf(0, 8).fieldOf("input").forGetter(o -> o.input),
            FluidStack.CODEC.fieldOf("inputFluid").forGetter(o -> o.inputFluid),
            Codec.INT.fieldOf("processingTime").forGetter(o -> o.processingTime),
            ItemStack.CODEC.optionalFieldOf("output").forGetter(o -> o.output)
    ).apply(in, SolidifierRecipe::new));

    public List<Ingredient> input;
    public FluidStack inputFluid;
    public int processingTime;
    public Optional<ItemStack> output;

    public SolidifierRecipe(List<Ingredient> input, FluidStack inputFluid, int processingTime, Optional<ItemStack> output) {
        this.input = input;
        this.inputFluid = inputFluid;
        this.processingTime = processingTime;
        this.output = output;
    }

    public static void createRecipe(RecipeOutput recipeOutput, String name, SolidifierRecipe recipe) {
        ResourceLocation rl = generateRL(name);
        AdvancementHolder advancementHolder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(Builder.recipe(rl)).requirements(Strategy.OR).build(rl);
        List<ICondition> conditions = new ArrayList();
        if (recipe.output.isPresent()) {
            conditions.add(new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(((ItemStack)recipe.output.get()).getItem())));
        }

        recipeOutput.accept(rl, recipe, advancementHolder, (ICondition[])conditions.toArray(new ICondition[conditions.size()]));
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

            for (Ingredient ingredient : this.input) {
                boolean found = false;

                for (ItemStack stack : ingredient.getItems()) {
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
    public boolean matches(CraftingInput craftingInput, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output.orElse(ItemStack.EMPTY).copy();
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModuleCoreAdditional.SOLIDIFIER.getBlock());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModuleCoreAdditional.SOLIDIFIER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.SOLIDIFIER_TYPE.get();
    }
}
