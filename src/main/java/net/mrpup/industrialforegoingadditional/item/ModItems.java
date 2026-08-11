package net.mrpup.industrialforegoingadditional.item;


import com.buuz135.industrial.item.addon.EfficiencyAddonItem;
import com.buuz135.industrial.item.addon.ProcessingAddonItem;
import com.buuz135.industrial.item.addon.SpeedAddonItem;
import net.minecraft.world.item.*;
import net.mrpup.industrialforegoingadditional.config.item.PlasticElytraConfig;
// import net.mrpup.industrialforegoingadditional.item.sifter.SifterItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional.MOD_ID;
import static net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional.TAB_CORE_ADDITIONAL;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_3 = ITEMS.register("efficiency_addon_tier_3",
            () -> new EfficiencyAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_4 = ITEMS.register("efficiency_addon_tier_4",
            () -> new EfficiencyAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> EFFICIENCY_ADDON_TIER_5 = ITEMS.register("efficiency_addon_tier_5",
            () -> new EfficiencyAddonItem(5, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_3 = ITEMS.register("processing_addon_tier_3",
            () -> new ProcessingAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_4 = ITEMS.register("processing_addon_tier_4",
            () -> new ProcessingAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> PROCESSING_ADDON_TIER_5 = ITEMS.register("processing_addon_tier_5",
            () -> new ProcessingAddonItem(5, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_3 = ITEMS.register("speed_addon_tier_3",
            () -> new SpeedAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_4 = ITEMS.register("speed_addon_tier_4",
            () -> new SpeedAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final DeferredItem<Item> SPEED_ADDON_TIER_5 = ITEMS.register("speed_addon_tier_5",
            () -> new SpeedAddonItem(5, TAB_CORE_ADDITIONAL));



    public static final DeferredItem<Item> NETHERITE_GEAR = ITEMS.register("netherite_gear",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_DIAMOND = ITEMS.register("polished_diamond",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_EMERALD = ITEMS.register("polished_emerald",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_AMETHYST = ITEMS.register("polished_amethyst_shard",
            () -> new Item(new Item.Properties()));


/* Update
    public static final DeferredItem<SifterItem> WOODEN_SIFTER = ITEMS.register("wooden_sifter",
            () -> new SifterItem(SifterItem.SifterTier.WOOD, new Item.Properties()));

    public static final DeferredItem<SifterItem> IRON_SIFTER = ITEMS.register("iron_sifter",
            () -> new SifterItem(SifterItem.SifterTier.IRON, new Item.Properties()));

    public static final DeferredItem<SifterItem> DIAMOND_SIFTER = ITEMS.register("diamond_sifter",
            () -> new SifterItem(SifterItem.SifterTier.DIAMOND, new Item.Properties()));
 */

    public static final DeferredItem<Item> PLASTIC_ELYTRA = ITEMS.register("plastic_elytra",
            () -> new ElytraItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(PlasticElytraConfig.maxDurability)
                    .rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
