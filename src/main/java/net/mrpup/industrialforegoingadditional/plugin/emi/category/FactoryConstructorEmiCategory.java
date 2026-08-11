package net.mrpup.industrialforegoingadditional.plugin.emi.category;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;

public class FactoryConstructorEmiCategory extends EmiRecipeCategory {
    public static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "factory_constructor");

    public FactoryConstructorEmiCategory() {
        super(ID, EmiStack.of(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.asItem()));
    }
}
