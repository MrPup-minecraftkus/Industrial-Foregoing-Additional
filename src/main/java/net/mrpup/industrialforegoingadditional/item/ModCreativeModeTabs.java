package net.mrpup.industrialforegoingadditional.item;

import net.minecraft.resources.ResourceLocation;
import net.mrpup.industrialforegoingadditional.block.ModBlocks;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ModCreativeModeTabs {

    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceLocation tabId = event.getTabKey().location();

        if (tabId.equals(ModuleCoreAdditional.TAB_CORE.getResourceLocation())) {
            event.accept(ModBlocks.ULTIMATE_MACHINE_FRAME);
            event.accept(ModItems.NETHERITE_GEAR);
            event.accept(ModItems.DARKEST_VOID_BUCKET);
            event.accept(ModItems.POLISHED_DIAMOND);
            event.accept(ModItems.POLISHED_EMERALD);
            event.accept(ModItems.POLISHED_AMETHYST);
            event.accept(ModItems.PLASTIC_ELYTRA);
        }
    }


    public static void register(IEventBus eventBus) {
        eventBus.register(ModCreativeModeTabs.class);
    }
}
