package net.mrpup.industrialforegoingadditional.recipe.survival;

/* Update

import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.item.sifter.SifterItem;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.output.Chance;
import net.mrpup.industrialforegoingadditional.recipe.output.SifterOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SifterMachineRecipe implements Recipe<CraftingInput> {

    public static final MapCodec<SifterMachineRecipe> CODEC = RecordCodecBuilder.mapCodec((in) -> in.group(
            Ingredient.CODEC.listOf(0, 8).fieldOf("input1").forGetter(o -> o.input1),
            Ingredient.CODEC.listOf(0, 8).fieldOf("input2").forGetter(o -> o.input2),
            FluidStack.CODEC.fieldOf("inputFluid1").forGetter(o -> o.inputFluid1),
            Codec.INT.fieldOf("processingTime").forGetter(o -> o.processingTime),
            Chance.CODEC.listOf().optionalFieldOf("outputs", List.of()).forGetter(o -> o.outputs),
            FluidStack.CODEC.optionalFieldOf("outputFluid").forGetter(o -> o.outputFluid)
    ).apply(in, SifterMachineRecipe::new));

    public List<Ingredient> input1;
    public List<Ingredient> input2;
    public FluidStack inputFluid1;
    public int processingTime;
    public List<Chance> outputs;
    public Optional<FluidStack> outputFluid;

    public SifterMachineRecipe(List<Ingredient> input1, List<Ingredient> input2, FluidStack inputFluid1, int processingTime, List<Chance> outputs, Optional<FluidStack> outputFluid) {
        this.input1 = input1;
        this.input2 = input2;
        this.inputFluid1 = inputFluid1;
        this.processingTime = processingTime;
        this.outputs = outputs;
        this.outputFluid = outputFluid;
    }

    public SifterMachineRecipe() {

    }

    public static void createRecipe(RecipeOutput recipeOutput, String name, SifterMachineRecipe recipe) {
        ResourceLocation rl = generateRL(name);
        AdvancementHolder advancementHolder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl))
                .rewards(AdvancementRewards.Builder.recipe(rl))
                .requirements(AdvancementRequirements.Strategy.OR)
                .build(rl);

        List<ICondition> conditions = new ArrayList<>();
        if (!recipe.outputs.isEmpty()) {
            conditions.add(new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(recipe.outputs.get(0).stack().getItem())));
        }

        recipeOutput.accept(rl, recipe, advancementHolder, conditions.toArray(new ICondition[0]));
    }

    public static void createSifterRecipes(RecipeOutput consumer, String name, Ingredient input, FluidStack inputFluid, Optional<FluidStack> outputFluid, SifterOutput... output) {
        for (SifterItem.SifterTier tier : SifterItem.SifterTier.values()) {
            List<Chance> tierChances = Arrays.stream(output)
                    .filter(d -> d.appliesTo(tier))
                    .map(d -> d.toChance(tier))
                    .toList();

            createRecipe(consumer, name + "_with_" + tier.name().toLowerCase() + "_sifter",
                    new SifterMachineRecipe(
                            List.of(input),
                            List.of(Ingredient.of(getSifterItemForTier(tier))),
                            inputFluid,
                            tier.getProcessingTime(),
                            tierChances,
                            outputFluid
                    ));
        }
    }

    private static Item getSifterItemForTier(SifterItem.SifterTier tier) {
        return switch (tier) {
            case WOOD -> ModItems.WOODEN_SIFTER.get();
            case IRON -> ModItems.IRON_SIFTER.get();
            case DIAMOND -> ModItems.DIAMOND_SIFTER.get();
        };
    }

    public static ResourceLocation generateRL(String key) {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "sifter_machine/" + key);
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

        SifterItem sifterItem = null;
        for (Ingredient ingredient : input2) {
            boolean matched = false;
            for (int i = 0; i < handler2.getSlots(); i++) {
                ItemStack stack = handler2.getStackInSlot(i);
                if (ingredient.test(stack)) {
                    matched = true;
                    if (stack.getItem() instanceof SifterItem si) {
                        sifterItem = si;
                    }
                    break;
                }
            }
            if (!matched) return false;
        }

        int requiredFluidAmount = this.inputFluid1.getAmount();
        if (sifterItem != null) {
            requiredFluidAmount = sifterItem.getTier().getFluidUsage();
        }

        FluidStack tankFluid = fluidTank.getFluid();
        return tankFluid.getFluid() == this.inputFluid1.getFluid() && tankFluid.getAmount() >= requiredFluidAmount;
    }

    public int getDynamicProcessingTime(IItemHandler handler2) {
        for (int i = 0; i < handler2.getSlots(); i++) {
            ItemStack stack = handler2.getStackInSlot(i);
            if (stack.getItem() instanceof SifterItem sifterItem) {
                return sifterItem.getTier().getProcessingTime();
            }
        }
        return this.processingTime;
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
        return this.outputs.isEmpty() ? ItemStack.EMPTY : this.outputs.get(0).stack().copy();
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModuleCoreAdditional.SIFTER_MACHINE.getBlock());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModuleCoreAdditional.SIFTER_MACHINE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModuleCoreAdditional.SIFTER_MACHINE_TYPE.get();
    }
}

 */
