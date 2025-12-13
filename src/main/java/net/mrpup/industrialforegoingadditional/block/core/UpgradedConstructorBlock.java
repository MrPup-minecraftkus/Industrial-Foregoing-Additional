package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.UpgradedConstructorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class UpgradedConstructorBlock extends IndustrialBlock<UpgradedConstructorTile> {
    public UpgradedConstructorBlock() {
        super("upgraded_constructor", Properties.ofFullCopy(Blocks.IRON_BLOCK), UpgradedConstructorTile.class, ModuleCoreAdditional.TAB_CORE);
    }

    public BlockEntityType.BlockEntitySupplier<UpgradedConstructorTile> getTileEntityFactory() {
        return UpgradedConstructorTile::new;
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}