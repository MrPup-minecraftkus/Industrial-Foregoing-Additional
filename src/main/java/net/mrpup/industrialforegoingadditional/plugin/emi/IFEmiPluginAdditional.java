package net.mrpup.industrialforegoingadditional.plugin.emi;

import com.buuz135.industrial.plugin.emi.category.*;
import com.buuz135.industrial.plugin.emi.recipe.*;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.plugin.emi.category.*;
import net.mrpup.industrialforegoingadditional.plugin.emi.recipe.FactoryConstructorEmiRecipe;
import net.mrpup.industrialforegoingadditional.plugin.emi.recipe.PolishingMachineEmiRecipe;
import net.mrpup.industrialforegoingadditional.plugin.emi.recipe.SolidifierEmiRecipe;
import net.mrpup.industrialforegoingadditional.plugin.emi.recipe.UpgradedConstructorEmiRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.FactoryConstructorRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.PolishingMachineRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.UpgradedConstructorRecipe;
import net.mrpup.industrialforegoingadditional.recipe.survival.SolidifierRecipe;

@EmiEntrypoint
public class IFEmiPluginAdditional implements EmiPlugin {
    public static final FactoryConstructorEmiCategory FACTORY_CONSTRUCTOR_EMI_CATEGORY = new FactoryConstructorEmiCategory();
    public static final PolishingMachineEmiCategory POLISHING_MACHINE_EMI_CATEGORY = new PolishingMachineEmiCategory();
    //public static final SifterMachineEmiCategory SIFTER_MACHINE_EMI_CATEGORY = new SifterMachineEmiCategory();
    public static final SolidifierEmiCategory SOLIDIFIER_EMI_CATEGORY = new SolidifierEmiCategory();
    public static final UpgradedConstructorEmiCategory UPGRADED_CONSTRUCTOR_EMI_CATEGORY = new UpgradedConstructorEmiCategory();

    public void initialize(EmiInitRegistry registry) {

    }

    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        registry.addCategory(FACTORY_CONSTRUCTOR_EMI_CATEGORY);
        registry.addWorkstation(FACTORY_CONSTRUCTOR_EMI_CATEGORY, EmiIngredient.of(Ingredient.of(new ItemLike[]{ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getBlock()})));

        for(Object recipe : manager.getAllRecipesFor((RecipeType) ModuleCoreAdditional.FACTORY_CONSTRUCTOR_TYPE.get())) {
            registry.addRecipe(new FactoryConstructorEmiRecipe((RecipeHolder<FactoryConstructorRecipe>) recipe));
        }

        registry.addCategory(POLISHING_MACHINE_EMI_CATEGORY);
        registry.addWorkstation(POLISHING_MACHINE_EMI_CATEGORY, EmiIngredient.of(Ingredient.of(new ItemLike[]{ModuleCoreAdditional.POLISHING_MACHINE.getBlock()})));

        for(Object recipe : manager.getAllRecipesFor((RecipeType) ModuleCoreAdditional.POLISHING_MACHINE_TYPE.get())) {
            registry.addRecipe(new PolishingMachineEmiRecipe((RecipeHolder<PolishingMachineRecipe>) recipe));
        }

        registry.addCategory(SOLIDIFIER_EMI_CATEGORY);
        registry.addWorkstation(SOLIDIFIER_EMI_CATEGORY, EmiIngredient.of(Ingredient.of(new ItemLike[]{ModuleCoreAdditional.SOLIDIFIER.getBlock()})));

        for(Object recipe : manager.getAllRecipesFor((RecipeType) ModuleCoreAdditional.SOLIDIFIER_TYPE.get())) {
            registry.addRecipe(new SolidifierEmiRecipe((RecipeHolder<SolidifierRecipe>) recipe));
        }

        registry.addCategory(UPGRADED_CONSTRUCTOR_EMI_CATEGORY);
        registry.addWorkstation(UPGRADED_CONSTRUCTOR_EMI_CATEGORY, EmiIngredient.of(Ingredient.of(new ItemLike[]{ModuleCoreAdditional.UPGRADED_CONSTRUCTOR.getBlock()})));

        for(Object recipe : manager.getAllRecipesFor((RecipeType) ModuleCoreAdditional.UPGRADED_CONSTRUCTOR_TYPE.get())) {
            registry.addRecipe(new UpgradedConstructorEmiRecipe((RecipeHolder<UpgradedConstructorRecipe>) recipe));
        }

    }
}
