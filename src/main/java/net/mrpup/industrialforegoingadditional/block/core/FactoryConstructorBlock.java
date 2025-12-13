package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import javax.annotation.Nonnull;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.FactoryConstructorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

public class FactoryConstructorBlock extends IndustrialBlock<FactoryConstructorTile> {
    public FactoryConstructorBlock() {
        super("factory_constructor", Properties.ofFullCopy(Blocks.IRON_BLOCK), FactoryConstructorTile.class, ModuleCoreAdditional.TAB_CORE);
    }

    public BlockEntityType.BlockEntitySupplier<FactoryConstructorTile> getTileEntityFactory() {
        return FactoryConstructorTile::new;
    }

    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}