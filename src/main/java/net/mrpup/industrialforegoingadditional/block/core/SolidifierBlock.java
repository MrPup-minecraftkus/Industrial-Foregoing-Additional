package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.SolidifierTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class SolidifierBlock extends IndustrialBlock<SolidifierTile> {
    public SolidifierBlock() {
        super("solidifier", Properties.ofFullCopy(Blocks.IRON_BLOCK), SolidifierTile.class, ModuleCoreAdditional.TAB_CORE);
    }

    public BlockEntityType.BlockEntitySupplier<SolidifierTile> getTileEntityFactory() {
        return SolidifierTile::new;
    }

    @Nonnull
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}