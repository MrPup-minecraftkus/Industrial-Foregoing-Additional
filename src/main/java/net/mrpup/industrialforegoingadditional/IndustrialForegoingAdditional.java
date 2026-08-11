package net.mrpup.industrialforegoingadditional;
import com.hrznstudio.titanium.module.ModuleController;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.item.ModCreativeModeTabs;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.provide.IndustrialRecipeProviderAdditional;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mod(IndustrialForegoingAdditional.MOD_ID)
public class IndustrialForegoingAdditional extends ModuleController  {

    public static final String MOD_ID = "industrialforegoingadditional";

    private static final Logger LOGGER = LogUtils.getLogger();
    public static IndustrialForegoingAdditional INSTANCE;


    public IndustrialForegoingAdditional(Dist dist, IEventBus modEventBus, ModContainer container)
    {
        super(container);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        BestCat();
    }

    private void BestCat() {
        LOGGER.debug("all cats are beautiful, my cat -> assets/industrialforegoingadditional/cat.png");
    }

    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        Lazy<List<Block>> blocksToProcess = Lazy.of(() -> (List) BuiltInRegistries.BLOCK.stream().filter((block) -> !block.getClass().equals(LiquidBlock.class)).filter((basicBlock) -> Optional.ofNullable(BuiltInRegistries.BLOCK.getKey(basicBlock)).map(ResourceLocation::getNamespace).filter("industrialforegoingadditional"::equalsIgnoreCase).isPresent()).collect(Collectors.toList()));
        event.getGenerator().addProvider(true, new IndustrialRecipeProviderAdditional(event.getGenerator(), blocksToProcess, event.getLookupProvider()));
    }

    @Override
    protected void initModules() {
        INSTANCE = this;
        (new ModuleCoreAdditional()).generateFeatures(this.getRegistries());
        this.addCreativeTab("core", () -> new ItemStack(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getBlock()), "industrialforegoingadditional_core", ModuleCoreAdditional.TAB_CORE_ADDITIONAL);
        this.addCreativeTab("survival", () -> new ItemStack(ModuleCoreAdditional.BLOCK_DETECTOR.getBlock()), "industrialforegoingadditional_survival", ModuleCoreAdditional.TAB_SURVIVAL);

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
