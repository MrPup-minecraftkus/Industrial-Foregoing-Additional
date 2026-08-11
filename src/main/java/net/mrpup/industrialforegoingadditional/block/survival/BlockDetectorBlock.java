package net.mrpup.industrialforegoingadditional.block.survival;

import com.buuz135.industrial.block.IndustrialBlock;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.mrpup.industrialforegoingadditional.block.survival.tile.BlockDetectorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class BlockDetectorBlock extends IndustrialBlock<BlockDetectorTile> {
    public BlockDetectorBlock() {
        super("block_detector", Properties.copy(Blocks.IRON_BLOCK), BlockDetectorTile.class, ModuleCoreAdditional.TAB_SURVIVAL);
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
    public RotationType getRotationType() {
        return RotationType.SIX_WAY;
    }

    public void registerRecipe(Consumer<FinishedRecipe> consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this).pattern("PCP").pattern("BMB").pattern("GDG").define('P', IndustrialTags.Items.PLASTIC).define('C', Tags.Items.CHESTS_WOODEN).define('B', net.minecraft.world.item.Items.BUCKET).define('M', IndustrialTags.Items.MACHINE_FRAME_PITY).define('G', Tags.Items.INGOTS_GOLD).define('D', TagUtil.getItemTag(ResourceLocation.fromNamespaceAndPath("c", "gears/diamond"))).save(consumer);
    }
}
