package net.mrpup.industrialforegoingadditional;

import com.hrznstudio.titanium.module.ModuleController;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.item.ModCreativeModeTabs;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.recipe.provide.IndustrialRecipeProviderAdditional;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mod(IndustrialForegoingAdditional.MOD_ID)
public class IndustrialForegoingAdditional extends ModuleController
{
    public static final String MOD_ID = "industrialforegoingadditional";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static IndustrialForegoingAdditional INSTANCE;


    public IndustrialForegoingAdditional(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        BestCat();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void BestCat() {
        LOGGER.debug("all cats are beautiful, my cat -> assets/industrialforegoingadditional/cat.png");
    }

    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        NonNullLazy<List<Block>> blocksToProcess = NonNullLazy.of(() -> (List) ForgeRegistries.BLOCKS.getValues().stream().filter((block) -> !block.getClass().equals(LiquidBlock.class)).filter((basicBlock) -> Optional.ofNullable(ForgeRegistries.BLOCKS.getKey(basicBlock)).map(ResourceLocation::getNamespace).filter("industrialforegoingadditional"::equalsIgnoreCase).isPresent()).collect(Collectors.toList()));
        event.getGenerator().addProvider(true, new IndustrialRecipeProviderAdditional(event.getGenerator(), blocksToProcess));
    }

    @Override
    protected void initModules() {
        INSTANCE = this;
        new ModuleCoreAdditional().generateFeatures(getRegistries());
        this.addCreativeTab("core", () ->  new ItemStack(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getLeft().orElse(Blocks.STONE)), IndustrialForegoingAdditional.MOD_ID + "_core", ModuleCoreAdditional.TAB_CORE_ADDITIONAL);
        this.addCreativeTab("survival", () -> new ItemStack(ModuleCoreAdditional.BLOCK_DETECTOR.getLeft().orElse(Blocks.STONE)), "industrialforegoingadditional_survival", ModuleCoreAdditional.TAB_SURVIVAL);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
