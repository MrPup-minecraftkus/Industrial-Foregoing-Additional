package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.PolishingMachineTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class PolishingMachineBlock extends IndustrialBlock<PolishingMachineTile> {
    public PolishingMachineBlock() {
        super("polishing_machine", Properties.copy(Blocks.IRON_BLOCK), PolishingMachineTile.class, ModuleCoreAdditional.TAB_CORE_ADDITIONAL);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<PolishingMachineTile> getTileEntityFactory() {
      return PolishingMachineTile::new;
    }

   @Nonnull
   @Override
   public RotationType getRotationType() {
      return RotationType.FOUR_WAY;
   }
}