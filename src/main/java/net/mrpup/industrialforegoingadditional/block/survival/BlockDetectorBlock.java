package net.mrpup.industrialforegoingadditional.block.survival;

import com.buuz135.industrial.block.IndustrialBlock;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.mrpup.industrialforegoingadditional.block.survival.tile.BlockDetectorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class BlockDetectorBlock extends IndustrialBlock<BlockDetectorTile> {
    public BlockDetectorBlock() {
        super("block_detector", Properties.ofFullCopy(Blocks.IRON_BLOCK), BlockDetectorTile.class, ModuleCoreAdditional.TAB_SURVIVAL);
    }

    public BlockEntityType.BlockEntitySupplier<BlockDetectorTile> getTileEntityFactory() {
        return BlockDetectorTile::new;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity te = level.getBlockEntity(pos);
        if (te instanceof BlockDetectorTile detector) {
            return detector.getSignal();
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotationType.SIX_WAY;
    }
}
