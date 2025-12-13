package net.mrpup.industrialforegoingadditional.block.core;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mrpup.industrialforegoingadditional.block.core.tile.PolishingMachineTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

import javax.annotation.Nonnull;

public class PolishingMachineBlock extends IndustrialBlock<PolishingMachineTile> {
  public PolishingMachineBlock() {
    super("polishing_machine", Properties.ofFullCopy(Blocks.IRON_BLOCK), PolishingMachineTile.class, ModuleCoreAdditional.TAB_CORE);
         }

 public BlockEntityType.BlockEntitySupplier<PolishingMachineTile> getTileEntityFactory() {
      return PolishingMachineTile::new;
   }

   @Nonnull
   public RotatableBlock.RotationType getRotationType() {
      return RotationType.FOUR_WAY;
  }
}