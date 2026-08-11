package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.UpgradedConstructorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class UpgradedConstructorBlock extends IndustrialBlock<UpgradedConstructorTile> {
    public UpgradedConstructorBlock() {
        super("upgraded_constructor", Properties.copy(Blocks.IRON_BLOCK), UpgradedConstructorTile.class, ModuleCoreAdditional.TAB_CORE_ADDITIONAL);
    }

    public BlockEntityType.BlockEntitySupplier<UpgradedConstructorTile> getTileEntityFactory() {
        return UpgradedConstructorTile::new;
    }

    @Nonnull
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}