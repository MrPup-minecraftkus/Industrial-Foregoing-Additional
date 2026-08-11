package net.mrpup.industrialforegoingadditional.recipe.provide;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.*;
import com.buuz135.industrial.recipe.data.EntityData;
import com.buuz135.industrial.utils.IndustrialTags;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IndustrialSerializableProvideAdditional {

    public static void init(RecipeOutput output) {

        //darkest_void
        createRecipeLaserDrillFluid(output, "darkest_void", "minecraft", new LaserDrillFluidRecipe(new SizedFluidIngredient(FluidIngredient.of(new Fluid[]{(Fluid)ModuleCoreAdditional.DARKEST_VOID.getSourceFluid().get()}), 5), 15, Optional.of(EntityData.of(EntityType.WARDEN)), new LaserDrillRarity[]{new LaserDrillRarity(new LaserDrillRarity.BiomeRarity(new ArrayList(), new ArrayList()), new LaserDrillRarity.DimensionRarity(new ArrayList(), new ArrayList()), -64, 256, 8)}));

        // factory_constructor
        DissolutionChamberRecipe.createRecipe(output, "factory_constructor", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(ModBlocks.ULTIMATE_MACHINE_FRAME.get()),
                        Ingredient.of(ModuleCore.PLASTIC.get()),
                        Ingredient.of(ModuleCore.DISSOLUTION_CHAMBER),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.ECHO_SHARD),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.ECHO_SHARD)
                ),
                new FluidStack(ModuleCoreAdditional.DARKEST_VOID.getSourceFluid(), 250),
                1000,
                Optional.of(new ItemStack(ModuleCoreAdditional.FACTORY_CONSTRUCTOR)),
                Optional.empty()
        ));

        // machine_frame_ultimate
        DissolutionChamberRecipe.createRecipe(output, "machine_frame_ultimate", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(IndustrialTags.Items.PLASTIC),
                        Ingredient.of(ModuleCore.SUPREME.get()),
                        Ingredient.of(IndustrialTags.Items.PLASTIC),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.ECHO_SHARD),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.ECHO_SHARD)
                ),
                new FluidStack(ModuleCoreAdditional.DARKEST_VOID.getSourceFluid(), 100),
                500,
                Optional.of(new ItemStack(ModBlocks.ULTIMATE_MACHINE_FRAME.get())),
                Optional.empty()
        ));

        // netherite_gear
        DissolutionChamberRecipe.createRecipe(output, "netherite_gear", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(IndustrialTags.Items.GEAR_DIAMOND),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(IndustrialTags.Items.PLASTIC),
                        Ingredient.of(IndustrialTags.Items.PLASTIC),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(IndustrialTags.Items.GEAR_DIAMOND),
                        Ingredient.of(Items.NETHERITE_INGOT)
                ),
                new FluidStack(ModuleCore.LATEX.getSourceFluid().get(), 250),
                1000,
                Optional.of(new ItemStack(ModItems.NETHERITE_GEAR.get(), 2)),
                Optional.empty()
        ));

        // plastic_elytra
        DissolutionChamberRecipe.createRecipe(output, "plastic_elytra", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.END_STONE),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.END_ROD),
                        Ingredient.of(Items.ELYTRA),
                        Ingredient.of(Items.ELYTRA),
                        Ingredient.of(Items.END_ROD),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Blocks.END_STONE)
                ),
                new FluidStack(ModuleCore.LATEX.getSourceFluid().get(), 1500),
                250,
                Optional.of(new ItemStack(ModItems.PLASTIC_ELYTRA.get())),
                Optional.empty()
        ));

        // efficiency_addon_tier_3
        DissolutionChamberRecipe.createRecipe(output, "efficiency_addon_tier_3", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.BLAZE_ROD),
                        Ingredient.of(Items.BLAZE_ROD)
                ),
                new FluidStack(ModuleCore.LATEX.getSourceFluid().get(), 1500),
                250,
                Optional.of(new ItemStack(ModItems.EFFICIENCY_ADDON_TIER_3.get())),
                Optional.empty()
        ));

        // efficiency_addon_tier_4
        DissolutionChamberRecipe.createRecipe(output, "efficiency_addon_tier_4", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.EFFICIENCY_ADDON_TIER_3.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.NETHER_STAR)
                ),
                new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 100),
                300,
                Optional.of(new ItemStack(ModItems.EFFICIENCY_ADDON_TIER_4.get())),
                Optional.empty()
        ));

        // processing_addon_tier_3
        DissolutionChamberRecipe.createRecipe(output, "processing_addon_tier_3", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Blocks.FURNACE),
                        Ingredient.of(Blocks.CRAFTING_TABLE)
                ),
                new FluidStack(ModuleCore.LATEX.getSourceFluid().get(), 1500),
                250,
                Optional.of(new ItemStack(ModItems.PROCESSING_ADDON_TIER_3.get())),
                Optional.empty()
        ));

        // processing_addon_tier_4
        DissolutionChamberRecipe.createRecipe(output, "processing_addon_tier_4", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.PROCESSING_ADDON_TIER_3.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.NETHER_STAR)
                ),
                new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 100),
                300,
                Optional.of(new ItemStack(ModItems.PROCESSING_ADDON_TIER_4.get())),
                Optional.empty()
        ));

        // speed_addon_tier_3
        DissolutionChamberRecipe.createRecipe(output, "speed_addon_tier_3", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.SUGAR)
                ),
                new FluidStack(ModuleCore.LATEX.getSourceFluid().get(), 1500),
                250,
                Optional.of(new ItemStack(ModItems.SPEED_ADDON_TIER_3.get())),
                Optional.empty()
        ));

        // speed_addon_tier_4
        DissolutionChamberRecipe.createRecipe(output, "speed_addon_tier_4", new DissolutionChamberRecipe(
                List.of(
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Blocks.REDSTONE_BLOCK),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(Items.GLASS_PANE),
                        Ingredient.of(ModItems.SPEED_ADDON_TIER_3.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(ModItems.NETHERITE_GEAR.get()),
                        Ingredient.of(Items.NETHER_STAR)
                ),
                new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 100),
                300,
                Optional.of(new ItemStack(ModItems.SPEED_ADDON_TIER_4.get())),
                Optional.empty()
        ));
    }

    public static void createRecipeLaserDrillFluid(RecipeOutput recipeOutput, String name, String modIdCondition, LaserDrillFluidRecipe recipe) {
        ResourceLocation rl = generateRLLaserDrillFluid(name);
        AdvancementHolder advancementHolder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(AdvancementRequirements.Strategy.OR).build(rl);
        recipeOutput.accept(rl, recipe, advancementHolder, new ICondition[]{new ModLoadedCondition(modIdCondition)});
    }

    public static ResourceLocation generateRLLaserDrillFluid(String key) {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoing", "laser_drill_fluid/" + key);
    }
}
