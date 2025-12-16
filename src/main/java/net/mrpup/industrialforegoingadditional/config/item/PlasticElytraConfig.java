package net.mrpup.industrialforegoingadditional.config.item;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.mrpup.industrialforegoingadditional.config.ModConfigItem;

@ConfigFile.Child(ModConfigItem.class)
public class PlasticElytraConfig {

    @ConfigVal(
            comment = "Max Speed - Default: [3], Classic Elytra = 1"
    )
    public static int MaxSpeed = 3;
    @ConfigVal(
            comment = "Max Durability - Default: [1200]"
    )
    public static int maxDurability = 1200;

}
