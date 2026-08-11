package net.mrpup.industrialforegoingadditional.plugin.emi.category;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

public class PolishingMachineEmiCategory extends EmiRecipeCategory {
    public static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "polishing_machine");

    public PolishingMachineEmiCategory() {
        super(ID, EmiStack.of(ModuleCoreAdditional.POLISHING_MACHINE.asItem()));
    }
}
