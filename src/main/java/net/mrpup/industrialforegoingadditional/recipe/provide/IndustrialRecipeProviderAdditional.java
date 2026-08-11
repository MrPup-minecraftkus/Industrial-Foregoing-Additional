package net.mrpup.industrialforegoingadditional.recipe.provide;

import com.buuz135.industrial.module.ModuleCore;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.NonNullLazy;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import java.util.List;
import java.util.function.Consumer;

public class IndustrialRecipeProviderAdditional extends TitaniumRecipeProvider {
    private final NonNullLazy<List<Block>> blocks;

    public IndustrialRecipeProviderAdditional(DataGenerator generatorIn, NonNullLazy<List<Block>> blocks) {
        super(generatorIn);
        this.blocks = blocks;
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe((ItemLike) ModuleCoreAdditional.BLOCK_DETECTOR.getLeft().get()).pattern("PGP").pattern("DMD").pattern("SRS")
                .define('P', ModuleCore.PLASTIC.get())
                .define('G', ModuleCore.GOLD_GEAR.get())
                .define('M', ModuleCore.PITY.get())
                .define('D', Items.REDSTONE_BLOCK)
                .define('S', ModuleCore.IRON_GEAR.get())
                .define('R', Items.REDSTONE)
                .save(consumer);
    }
}
