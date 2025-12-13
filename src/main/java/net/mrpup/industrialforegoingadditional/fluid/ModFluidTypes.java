package net.mrpup.industrialforegoingadditional.fluid;

import net.minecraft.resources.ResourceLocation;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final ResourceLocation DARKEST_VOID_OVERLAY_RL = ResourceLocation.fromNamespaceAndPath(IndustrialForegoingAdditional.MOD_ID, "misc/darkest_void");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, IndustrialForegoingAdditional.MOD_ID);

    public static final Supplier<FluidType> DARKEST_VOID_FLUID_TYPE = register("darkest_void",
            FluidType.Properties.create().lightLevel(0).density(100).viscosity(100));



    private static Supplier<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, DARKEST_VOID_OVERLAY_RL,
                0xFF000000, new Vector3f(0f / 255f, 0f / 255f, 0f / 255f), properties));
    }


    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
