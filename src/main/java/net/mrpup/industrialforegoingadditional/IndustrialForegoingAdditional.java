package net.mrpup.industrialforegoingadditional;

import com.hrznstudio.titanium.module.ModuleController;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.fluid.ModFluidTypes;
import net.mrpup.industrialforegoingadditional.fluid.ModFluids;
import net.mrpup.industrialforegoingadditional.item.ModCreativeModeTabs;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(IndustrialForegoingAdditional.MOD_ID)
public class IndustrialForegoingAdditional extends ModuleController  {

    public static final String MOD_ID = "industrialforegoingadditional";

    private static final Logger LOGGER = LogUtils.getLogger();
    public static IndustrialForegoingAdditional INSTANCE;


    public IndustrialForegoingAdditional(Dist dist, IEventBus modEventBus, ModContainer container)
    {
        super(container);
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Override
    protected void initModules() {
        INSTANCE = this;
        (new ModuleCoreAdditional()).generateFeatures(this.getRegistries());
        this.addCreativeTab("core", () -> new ItemStack(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getBlock()), "industrialforegoingadditional_core", ModuleCoreAdditional.TAB_CORE);

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_DARKEST_VOID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_DARKEST_VOID.get(), RenderType.translucent());
        }
    }
}
