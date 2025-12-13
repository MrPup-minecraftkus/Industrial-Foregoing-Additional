package net.mrpup.industrialforegoingadditional.plugin.jei;

import com.buuz135.industrial.gui.conveyor.GuiConveyor;
import com.buuz135.industrial.gui.transporter.GuiTransporter;
import com.hrznstudio.titanium.util.RecipeUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;

import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.plugin.jei.category.FactoryConstructorCategory;
import net.mrpup.industrialforegoingadditional.plugin.jei.category.PolishingMachineCategory;
import net.mrpup.industrialforegoingadditional.plugin.jei.category.SolidifierCategory;
import net.mrpup.industrialforegoingadditional.plugin.jei.category.UpgradedConstructorCategory;

import java.util.*;
import java.util.stream.Collectors;

@JeiPlugin
public class JEICustomPluginAdditional implements IModPlugin {
    private static IRecipesGui recipesGui;
    private FactoryConstructorCategory factoryConstructorJEICategory;
    private UpgradedConstructorCategory upgradedConstructorJEICategory;
    private PolishingMachineCategory polishingMachineJEICategory;
    private SolidifierCategory solidifierJEICategory;

    public static void showUses(ItemStack stack) {
    }

    public void registerIngredients(IModIngredientRegistration registry) {
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(GuiConveyor.class, new IGhostIngredientHandler<GuiConveyor>() {
            public <I> List<IGhostIngredientHandler.Target<I>> getTargetsTyped(GuiConveyor guiConveyor, ITypedIngredient<I> i, boolean b) {
                return i.getIngredient() instanceof ItemStack ? (List)guiConveyor.getGhostSlots().stream().map((ghostSlot) -> new IGhostIngredientHandler.Target<I>() {
                    public Rect2i getArea() {
                        return ghostSlot.getArea();
                    }

                    public void accept(I stack) {
                        ghostSlot.accept((ItemStack)stack);
                    }
                }).collect(Collectors.toList()) : Collections.emptyList();
            }

            public void onComplete() {
            }
        });
        registration.addGhostIngredientHandler(GuiTransporter.class, new IGhostIngredientHandler<GuiTransporter>() {
            public <I> List<IGhostIngredientHandler.Target<I>> getTargetsTyped(GuiTransporter guiConveyor, ITypedIngredient<I> i, boolean b) {
                return i.getIngredient() instanceof ItemStack ? (List)guiConveyor.getGhostSlots().stream().map((ghostSlot) -> new IGhostIngredientHandler.Target<I>() {
                    public Rect2i getArea() {
                        return ghostSlot.getArea();
                    }

                    public void accept(I stack) {
                        ghostSlot.accept((ItemStack)stack);
                    }
                }).collect(Collectors.toList()) : Collections.emptyList();
            }

            public void onComplete() {
            }
        });
    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        this.factoryConstructorJEICategory = new FactoryConstructorCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(new IRecipeCategory[]{this.factoryConstructorJEICategory});

        this.upgradedConstructorJEICategory = new UpgradedConstructorCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(new IRecipeCategory[]{this.upgradedConstructorJEICategory});

        this.polishingMachineJEICategory = new PolishingMachineCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(new IRecipeCategory[]{this.polishingMachineJEICategory});

        this.solidifierJEICategory = new SolidifierCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(new IRecipeCategory[]{this.solidifierJEICategory});


    }

    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(IndustrialRecipeTypesAdditional.FACTORY_CONSTRUCTOR, RecipeUtil.getRecipes(Minecraft.getInstance().level, (net.minecraft.world.item.crafting.RecipeType) ModuleCoreAdditional.FACTORY_CONSTRUCTOR_TYPE.get()));
        registration.addRecipes(IndustrialRecipeTypesAdditional.UPGRADED_CONSTRUCTOR, RecipeUtil.getRecipes(Minecraft.getInstance().level, (net.minecraft.world.item.crafting.RecipeType) ModuleCoreAdditional.UPGRADED_CONSTRUCTOR_TYPE.get()));
        registration.addRecipes(IndustrialRecipeTypesAdditional.POLISHING_MACHINE, RecipeUtil.getRecipes(Minecraft.getInstance().level, (net.minecraft.world.item.crafting.RecipeType) ModuleCoreAdditional.POLISHING_MACHINE_TYPE.get()));
        registration.addRecipes(IndustrialRecipeTypesAdditional.SOLIDIFIER, RecipeUtil.getRecipes(Minecraft.getInstance().level, (net.minecraft.world.item.crafting.RecipeType) ModuleCoreAdditional.SOLIDIFIER_TYPE.get()));

    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getBlock()), new mezz.jei.api.recipe.RecipeType[]{IndustrialRecipeTypesAdditional.FACTORY_CONSTRUCTOR});
        registration.addRecipeCatalyst(new ItemStack(ModuleCoreAdditional.UPGRADED_CONSTRUCTOR.getBlock()), new mezz.jei.api.recipe.RecipeType[]{IndustrialRecipeTypesAdditional.UPGRADED_CONSTRUCTOR});
        registration.addRecipeCatalyst(new ItemStack(ModuleCoreAdditional.POLISHING_MACHINE.getBlock()), new mezz.jei.api.recipe.RecipeType[]{IndustrialRecipeTypesAdditional.POLISHING_MACHINE});
        registration.addRecipeCatalyst(new ItemStack(ModuleCoreAdditional.SOLIDIFIER.getBlock()), new mezz.jei.api.recipe.RecipeType[]{IndustrialRecipeTypesAdditional.SOLIDIFIER});

    }

    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "default");
    }
}
