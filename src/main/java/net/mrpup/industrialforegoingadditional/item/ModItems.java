package net.mrpup.industrialforegoingadditional.item;


import com.buuz135.industrial.item.addon.EfficiencyAddonItem;
import com.buuz135.industrial.item.addon.ProcessingAddonItem;
import com.buuz135.industrial.item.addon.SpeedAddonItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.mrpup.industrialforegoingadditional.fluid.ModFluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional.TAB_CORE;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IndustrialForegoingAdditional.MOD_ID);

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_3 = ITEMS.register("efficiency_addon_tier_3",
            () -> new EfficiencyAddonItem(3, TAB_CORE));

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_4 = ITEMS.register("efficiency_addon_tier_4",
            () -> new EfficiencyAddonItem(4, TAB_CORE));

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_5 = ITEMS.register("efficiency_addon_tier_5",
            () -> new EfficiencyAddonItem(5, TAB_CORE));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_3 = ITEMS.register("processing_addon_tier_3",
            () -> new ProcessingAddonItem(3, TAB_CORE));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_4 = ITEMS.register("processing_addon_tier_4",
            () -> new ProcessingAddonItem(4, TAB_CORE));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_5 = ITEMS.register("processing_addon_tier_5",
            () -> new ProcessingAddonItem(5, TAB_CORE));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_3 = ITEMS.register("speed_addon_tier_3",
            () -> new SpeedAddonItem(3, TAB_CORE));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_4 = ITEMS.register("speed_addon_tier_4",
            () -> new SpeedAddonItem(4, TAB_CORE));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_5 = ITEMS.register("speed_addon_tier_5",
            () -> new SpeedAddonItem(5, TAB_CORE));

    public static final DeferredItem<Item> NETHERITE_GEAR = ITEMS.register("netherite_gear",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_DIAMOND = ITEMS.register("polished_diamond",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_EMERALD = ITEMS.register("polished_emerald",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_AMETHYST = ITEMS.register("polished_amethyst_shard",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<BucketItem> DARKEST_VOID_BUCKET = ITEMS.register("darkest_void_bucket",
            () -> new BucketItem(ModFluids.SOURCE_DARKEST_VOID.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
