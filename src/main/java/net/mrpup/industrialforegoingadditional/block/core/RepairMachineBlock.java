package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.RepairMachineTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class RepairMachineBlock extends IndustrialBlock<RepairMachineTile> {
    public RepairMachineBlock() {
        super("repair_machine", Properties.ofFullCopy(Blocks.IRON_BLOCK), RepairMachineTile.class, ModuleCoreAdditional.TAB_CORE);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<RepairMachineTile> getTileEntityFactory() {
        return RepairMachineTile::new;
    }

    @Nonnull
    @Override
    public RotatableBlock.RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, tile) -> {
            if (tile instanceof RepairMachineTile repairMachineTile) {
                repairMachineTile.tick();
            }
        };
    }
}
