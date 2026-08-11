package net.mrpup.industrialforegoingadditional.item;

import com.buuz135.industrial.item.addon.EfficiencyAddonItem;
import com.buuz135.industrial.item.addon.ProcessingAddonItem;
import com.buuz135.industrial.item.addon.SpeedAddonItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.mrpup.industrialforegoingadditional.config.item.PlasticElytraConfig;
import static net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional.TAB_CORE_ADDITIONAL;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialForegoingAdditional.MOD_ID);

    public static final RegistryObject<Item> EFFICIENCY_ADDON_TIER_3 = ITEMS.register("efficiency_addon_tier_3",
            () -> new EfficiencyAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> EFFICIENCY_ADDON_TIER_4 = ITEMS.register("efficiency_addon_tier_4",
            () -> new EfficiencyAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> EFFICIENCY_ADDON_TIER_5 = ITEMS.register("efficiency_addon_tier_5",
            () -> new EfficiencyAddonItem(5, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> PROCESSING_ADDON_TIER_3 = ITEMS.register("processing_addon_tier_3",
            () -> new ProcessingAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> PROCESSING_ADDON_TIER_4 = ITEMS.register("processing_addon_tier_4",
            () -> new ProcessingAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> PROCESSING_ADDON_TIER_5 = ITEMS.register("processing_addon_tier_5",
            () -> new ProcessingAddonItem(5, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> SPEED_ADDON_TIER_3 = ITEMS.register("speed_addon_tier_3",
            () -> new SpeedAddonItem(3, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> SPEED_ADDON_TIER_4 = ITEMS.register("speed_addon_tier_4",
            () -> new SpeedAddonItem(4, TAB_CORE_ADDITIONAL));

    public static final RegistryObject<Item> SPEED_ADDON_TIER_5 = ITEMS.register("speed_addon_tier_5",
            () -> new SpeedAddonItem(5, TAB_CORE_ADDITIONAL));



    public static final RegistryObject<Item> NETHERITE_GEAR = ITEMS.register("netherite_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POLISHED_DIAMOND = ITEMS.register("polished_diamond",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POLISHED_EMERALD = ITEMS.register("polished_emerald",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POLISHED_AMETHYST = ITEMS.register("polished_amethyst_shard",
            () -> new Item(new Item.Properties()));



    public static final RegistryObject<Item> PLASTIC_ELYTRA = ITEMS.register("plastic_elytra",
            () -> new ElytraItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(PlasticElytraConfig.maxDurability)
                    .rarity(Rarity.EPIC)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
