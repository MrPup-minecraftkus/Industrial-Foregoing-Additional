package net.mrpup.industrialforegoingadditional.plugin.jei;

import net.minecraft.world.item.ItemStack;

public class JEIHelperAdditional {

    public static boolean isInstalled() {
        return false;
    }

    public static void openBlockUses(ItemStack stack) {
        if (isInstalled()) {
            JEICustomPluginAdditional.showUses(stack);
        }
    }
}

