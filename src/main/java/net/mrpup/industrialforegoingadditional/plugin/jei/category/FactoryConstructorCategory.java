package net.mrpup.industrialforegoingadditional.plugin.jei.category;

import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.mrpup.industrialforegoingadditional.block.core.tile.FactoryConstructorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.plugin.jei.IndustrialRecipeTypesAdditional;
import net.mrpup.industrialforegoingadditional.recipe.FactoryConstructorRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;


import java.awt.Color;

public class FactoryConstructorCategory implements IRecipeCategory<FactoryConstructorRecipe> {
    private final IGuiHelper guiHelper;
    private final IDrawable smallTank;
    private final IDrawable bigTank;

    public FactoryConstructorCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.smallTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 238, 4, 12, 13);
        this.bigTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4, 12, 50);
    }

    public RecipeType<FactoryConstructorRecipe> getRecipeType() {
        return IndustrialRecipeTypesAdditional.FACTORY_CONSTRUCTOR;
    }

    public Component getTitle() {
        return Component.translatable(ModuleCoreAdditional.FACTORY_CONSTRUCTOR.getBlock().getDescriptionId());
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        return 82;
    }

    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, FactoryConstructorRecipe recipe, IFocusGroup focuses) {
        for(int i = 0; i < recipe.input.size(); ++i) {
            builder.addSlot(RecipeIngredientRole.INPUT, 24 + (Integer) FactoryConstructorTile.getSlotPos(i).getLeft(), 11 + (Integer)FactoryConstructorTile.getSlotPos(i).getRight()).addIngredients((Ingredient)recipe.input.get(i));
        }

        if (recipe.inputFluid1 != null && !recipe.inputFluid1.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 73, 35).setFluidRenderer(1000L, false, 12, 13).setOverlay(this.smallTank, 0, 0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.inputFluid1);
        }

        if (recipe.inputFluid2 != null && !recipe.inputFluid2.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 23, 35).setFluidRenderer(1000L, false, 12, 13).setOverlay(this.smallTank, 0, 0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.inputFluid2);
        }

        if (!recipe.output.isEmpty()) {
            ItemStack stack = (ItemStack)recipe.output.get();
            builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 16).addIngredient(VanillaTypes.ITEM_STACK, stack);
        }

        if (recipe.outputFluid != null && !recipe.outputFluid.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 142, 17).setFluidRenderer(1000L, false, 12, 50).setOverlay(this.bigTank, 0, 0).addIngredient(NeoForgeTypes.FLUID_STACK, (FluidStack)recipe.outputFluid.get());
        }

    }

    public void draw(FactoryConstructorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        EnergyBarScreenAddon.drawBackground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0);
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 24, 11, 0, 0, 8, FactoryConstructorTile::getSlotPos, (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), (integer) -> true, 1);
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 20, 32);
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 70, 32);
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL), 139, 14);
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 33);
        int consumed = recipe.processingTime * DissolutionChamberConfig.powerPerTick;
        EnergyBarScreenAddon.drawForeground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0, (double)consumed, (double)((int)Math.max((double)50000.0F, Math.ceil((double)consumed))));
    }

}