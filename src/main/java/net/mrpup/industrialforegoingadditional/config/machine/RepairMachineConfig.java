package net.mrpup.industrialforegoingadditional.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.mrpup.industrialforegoingadditional.config.ModConfigMachine;

@ConfigFile.Child(ModConfigMachine.class)
public class RepairMachineConfig {

    @ConfigVal(
            comment = "Max Essence [mb] - Default: [64000 mb]"
    )
    public static int tankSize = 64000;
    @ConfigVal(
            comment = "Amount of Essence Per Repair [mb] - Default: [1 mb]"
    )
    public static int essencePerRepair = 1;
    @ConfigVal(
            comment = "Amount of Durability Per Repair [1] - Default: [1]"
    )
    public static int durabilityPerRepair = 1;
    @ConfigVal(
            comment = "Amount of Power Consumed per Tick - Default: [90FE]"
    )
    public static int powerPerTick = 90;
    @ConfigVal(
            comment = "Max Stored Power [FE] - Default: [10000 FE]"
    )
    public static int maxStoredPower = 10000;
}
