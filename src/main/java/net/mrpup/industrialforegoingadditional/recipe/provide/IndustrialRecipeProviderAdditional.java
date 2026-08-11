package net.mrpup.industrialforegoingadditional.recipe.provide;

import com.buuz135.industrial.module.ModuleCore;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.util.Lazy;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

/* Update

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.recipe.output.SifterOutput;
import net.mrpup.industrialforegoingadditional.recipe.survival.SifterMachineRecipe;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import java.util.Optional;

 */

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IndustrialRecipeProviderAdditional extends RecipeProvider {
    private final Lazy<List<Block>> blocks;

    public IndustrialRecipeProviderAdditional(DataGenerator generator, Lazy<List<Block>> blocksToProcess, CompletableFuture<HolderLookup.Provider> prov) {
        super(generator.getPackOutput(), prov);
        this.blocks = blocksToProcess;
    }

    public void buildRecipes(RecipeOutput consumer) {

        TitaniumShapedRecipeBuilder.shapedRecipe((ItemLike) ModuleCoreAdditional.BLOCK_DETECTOR).pattern("PGP").pattern("DMD").pattern("SRS")
                .define('P', ModuleCore.PLASTIC.get())
                .define('G', ModuleCore.GOLD_GEAR.get())
                .define('M', ModuleCore.PITY.get())
                .define('D', Items.REDSTONE_BLOCK)
                .define('S', ModuleCore.IRON_GEAR.get())
                .define('R', Items.REDSTONE)
                .save(consumer);


        /* Update
        SifterMachineRecipe.createSifterRecipes(consumer, "gravel",
                Ingredient.of(Items.GRAVEL),
                new FluidStack(Fluids.WATER, 1000),
                Optional.of(new FluidStack(ModuleCoreAdditional.DIRTY_MINERAL_WATER.getSourceFluid(), 1000)),
                SifterOutput.of(new ItemStack(Items.FLINT), 1.0f, ModItems.WOODEN_SIFTER),
                SifterOutput.of(new ItemStack(Items.IRON_NUGGET, 2), 0.4f, ModItems.IRON_SIFTER),
                SifterOutput.of(new ItemStack(Items.DIAMOND), 0.02f, ModItems.DIAMOND_SIFTER)
        );

         */


        IndustrialSerializableProvideAdditional.init(consumer);
    }
}
