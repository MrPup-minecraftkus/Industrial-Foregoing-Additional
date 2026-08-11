package net.mrpup.industrialforegoingadditional.config.machine.survival;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.mrpup.industrialforegoingadditional.config.ModConfigMachine;

@ConfigFile.Child(ModConfigMachine.class)
public class BlockDetectorConfig {
    @ConfigVal(
            comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [100 (5s)]"
    )
    public static int maxProgress = 100;
    @ConfigVal(
            comment = "Amount of Power Consumed per Tick - Default: [40FE]"
    )
    public static int powerPerOperation = 40;
    @ConfigVal(
            comment = "Max Stored Power [FE] - Default: [10000 FE]"
    )
    public static int maxStoredPower = 10000;
}
