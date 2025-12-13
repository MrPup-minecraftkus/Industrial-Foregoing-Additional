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
import net.mrpup.industrialforegoingadditional.block.core.tile.UpgradedConstructorTile;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.plugin.jei.IndustrialRecipeTypesAdditional;
import net.mrpup.industrialforegoingadditional.recipe.PolishingMachineRecipe;
import net.mrpup.industrialforegoingadditional.recipe.SolidifierRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

public class SolidifierCategory implements IRecipeCategory<SolidifierRecipe> {

    private final IDrawable smallTank;
    private final IDrawable bigTank;

    public SolidifierCategory(IGuiHelper guiHelper) {
        this.smallTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 238, 4, 12, 13);
        this.bigTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4, 12, 50);
    }

    @Override
    public RecipeType<SolidifierRecipe> getRecipeType() {
        return IndustrialRecipeTypesAdditional.SOLIDIFIER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModuleCoreAdditional.SOLIDIFIER.getBlock().getDescriptionId());
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        return 82;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolidifierRecipe recipe, IFocusGroup focuses) {

        for (int i = 0; i < recipe.input.size(); ++i) {
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 33)
                    .addIngredients(recipe.input.get(i));;
        }

        if (!recipe.inputFluid.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 26, 15)
                    .setFluidRenderer(4000L, false, 12, 50)
                    .setOverlay(bigTank, 0, 0)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.inputFluid);
        }

        recipe.output.ifPresent(stack ->
                builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 16)
                        .addIngredient(VanillaTypes.ITEM_STACK, stack)
        );
    }

    @Override
    public void draw(SolidifierRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        var screen = Minecraft.getInstance().screen;
        var assets = DefaultAssetProvider.DEFAULT_PROVIDER;

        EnergyBarScreenAddon.drawBackground(guiGraphics, screen, assets, 0, 12, 0, 0);
        SlotsScreenAddon.drawAsset(guiGraphics, screen, assets, 28, 33, 0, 0, 1,
                UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true,
                i -> new Color(DyeColor.MAGENTA.getFireworkColor()), i -> true, 1);
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);

        AssetUtil.drawAsset(guiGraphics, screen, assets.getAsset(AssetTypes.TANK_NORMAL), 23, 12);
        AssetUtil.drawAsset(guiGraphics, screen, IAssetProvider.getAsset(assets, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 80, 33);

        int consumed = recipe.processingTime * DissolutionChamberConfig.powerPerTick;
        double maxEnergy = Math.max(50000F, consumed);
        EnergyBarScreenAddon.drawForeground(guiGraphics, screen, assets, 0, 12, 0, 0, consumed, maxEnergy);
    }
}
