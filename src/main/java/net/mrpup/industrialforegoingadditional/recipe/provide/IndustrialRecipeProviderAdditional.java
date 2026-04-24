package net.mrpup.industrialforegoingadditional.recipe.provide;

import com.buuz135.industrial.module.ModuleCore;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.neoforged.neoforge.common.util.Lazy;

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

        IndustrialSerializableProvideAdditional.init(consumer);
    }
}
