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
import net.neoforged.neoforge.items.IItemHandler;

public class PolishingMachineRecipe implements Recipe<CraftingInput> {
    public static final MapCodec<PolishingMachineRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> in.group(
            Ingredient.CODEC.listOf(0, 8).fieldOf("input1").forGetter(o -> o.input1),
            Ingredient.CODEC.listOf(0, 8).fieldOf("input2").forGetter(o -> o.input2),
            FluidStack.CODEC.fieldOf("inputFluid1").forGetter(o -> o.inputFluid1),
            Codec.INT.fieldOf("processingTime").forGetter(o -> o.processingTime),
            ItemStack.CODEC.optionalFieldOf("output").forGetter(o -> o.output),
            FluidStack.CODEC.optionalFieldOf("outputFluid").forGetter(o -> o.outputFluid)
    ).apply(in, PolishingMachineRecipe::new));

    public List<Ingredient> input1;
    public List<Ingredient> input2;
    public FluidStack inputFluid1;
    public int processingTime;
    public Optional<ItemStack> output;
    public Optional<FluidStack> outputFluid;

    public PolishingMachineRecipe(List<Ingredient> input1, List<Ingredient> input2, FluidStack inputFluid1, int processingTime, Optional<ItemStack> output, Optional<FluidStack> outputFluid) {
        this.input1 = input1;
        this.input2 = input2;
        this.inputFluid1 = inputFluid1;
        this.processingTime = processingTime;
        this.output = output;
        this.outputFluid = outputFluid;
    }

    public PolishingMachineRecipe() {}

    public static void createRecipe(RecipeOutput recipeOutput, String name, PolishingMachineRecipe recipe) {
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
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "polishing_machine/" + key);
    }

    public boolean matches(IItemHandler handler1, IItemHandler handler2, FluidTankComponent fluidTank) {
        if (this.input1 == null || this.inputFluid1 == null || fluidTank == null) {
            return false;
        }
        for (Ingredient ingredient : input1) {
            boolean matched = false;
            for (int i = 0; i < handler1.getSlots(); i++) {
                ItemStack stack = handler1.getStackInSlot(i);
                if (ingredient.test(stack)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }

        for (Ingredient ingredient : input2) {
            boolean matched = false;
            for (int i = 0; i < handler2.getSlots(); i++) {
                ItemStack stack = handler2.getStackInSlot(i);
                if (ingredient.test(stack)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }

        FluidStack tankFluid = fluidTank.getFluid();
        return tankFluid.getAmount() >= this.inputFluid1.getAmount();
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
        return new ItemStack(ModuleCoreAdditional.POLISHING_MACHINE.getBlock());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModuleCoreAdditional.POLISHING_MACHINE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.POLISHING_MACHINE_TYPE.get();
    }
}

