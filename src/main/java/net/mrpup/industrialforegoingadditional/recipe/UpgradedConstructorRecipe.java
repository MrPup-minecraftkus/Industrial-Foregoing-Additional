package net.mrpup.industrialforegoingadditional.recipe;

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.items.IItemHandler;

public class UpgradedConstructorRecipe implements Recipe<CraftingInput> {
    public static final MapCodec<UpgradedConstructorRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> in.group(
            Ingredient.CODEC.listOf(0, 8).fieldOf("input").forGetter(o -> o.input),
            FluidStack.CODEC.fieldOf("inputFluid1").forGetter(o -> o.inputFluid1),
            Codec.INT.fieldOf("processingTime").forGetter(o -> o.processingTime),
            ItemStack.CODEC.optionalFieldOf("output").forGetter(o -> o.output),
            FluidStack.CODEC.optionalFieldOf("outputFluid").forGetter(o -> o.outputFluid)
    ).apply(in, UpgradedConstructorRecipe::new));

    public List<Ingredient> input;
    public FluidStack inputFluid1;
    public int processingTime;
    public Optional<ItemStack> output;
    public Optional<FluidStack> outputFluid;

    public UpgradedConstructorRecipe(List<Ingredient> input, FluidStack inputFluid1, int processingTime, Optional<ItemStack> output, Optional<FluidStack> outputFluid) {
        this.input = input;
        this.inputFluid1 = inputFluid1;
        this.processingTime = processingTime;
        this.output = output;
        this.outputFluid = outputFluid;
    }

    public UpgradedConstructorRecipe() {}

    public static void createRecipe(RecipeOutput recipeOutput, String name, UpgradedConstructorRecipe recipe) {
        ResourceLocation rl = generateRL(name);
        AdvancementHolder advancementHolder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl))
                .rewards(Builder.recipe(rl))
                .requirements(Strategy.OR)
                .build(rl);

        List<ICondition> conditions = new ArrayList<>();
        if (recipe.output.isPresent()) {
            conditions.add(new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(recipe.output.get().getItem())));
        }

        recipeOutput.accept(rl, recipe, advancementHolder, conditions.toArray(new ICondition[0]));
    }

    public static ResourceLocation generateRL(String key) {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "upgraded_constructor/" + key);
    }

    public boolean matches(IItemHandler handler, FluidTankComponent input1) {
        if (this.input != null && input1 != null && this.inputFluid1 != null) {
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
                    && input1.drainForced(this.inputFluid1, FluidAction.SIMULATE).getAmount() == this.inputFluid1.getAmount();
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
        return new ItemStack(ModuleCoreAdditional.UPGRADED_CONSTRUCTOR.getBlock());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModuleCoreAdditional.UPGRADED_CONSTRUCTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.UPGRADED_CONSTRUCTOR_TYPE.get();
    }
}
