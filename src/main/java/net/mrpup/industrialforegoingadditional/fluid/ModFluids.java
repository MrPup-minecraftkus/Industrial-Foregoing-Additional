package net.mrpup.industrialforegoingadditional.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, IndustrialForegoingAdditional.MOD_ID);

    public static final Supplier<FlowingFluid> SOURCE_DARKEST_VOID = FLUIDS.register("darkest_void",
            () -> new BaseFlowingFluid.Source(ModFluids.DARKEST_VOID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_DARKEST_VOID = FLUIDS.register("flowing_darkest_void",
            () -> new BaseFlowingFluid.Flowing(ModFluids.DARKEST_VOID_PROPERTIES));


    public static final BaseFlowingFluid.Properties DARKEST_VOID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.DARKEST_VOID_FLUID_TYPE, SOURCE_DARKEST_VOID::get, FLOWING_DARKEST_VOID::get)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.DARKEST_VOID_BLOCK).bucket(ModItems.DARKEST_VOID_BUCKET);



    public static void  register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
