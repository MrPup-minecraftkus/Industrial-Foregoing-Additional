package net.mrpup.industrialforegoingadditional.plugin.jei;


import mezz.jei.api.recipe.RecipeType;
import net.mrpup.industrialforegoingadditional.recipe.core.FactoryConstructorRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.PolishingMachineRecipe;
import net.mrpup.industrialforegoingadditional.recipe.survival.SolidifierRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.UpgradedConstructorRecipe;

public class IndustrialRecipeTypesAdditional {
    public static RecipeType<FactoryConstructorRecipe> FACTORY_CONSTRUCTOR = RecipeType.create("industrialforegoingadditional", "factory_constructor", FactoryConstructorRecipe.class);
    public static RecipeType<UpgradedConstructorRecipe> UPGRADED_CONSTRUCTOR = RecipeType.create("industrialforegoingadditional", "upgraded_constructor", UpgradedConstructorRecipe.class);
    public static RecipeType<PolishingMachineRecipe> POLISHING_MACHINE = RecipeType.create("industrialforegoingadditional", "polishing_machine", PolishingMachineRecipe.class);
    public static RecipeType<SolidifierRecipe> SOLIDIFIER = RecipeType.create("industrialforegoingadditional", "solidifier", SolidifierRecipe.class);
}

