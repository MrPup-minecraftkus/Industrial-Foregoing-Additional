package net.mrpup.industrialforegoingadditional.plugin.jei.category;

import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
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
import net.mrpup.industrialforegoingadditional.recipe.UpgradedConstructorRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;

public class UpgradedConstructorCategory implements IRecipeCategory<UpgradedConstructorRecipe> {

    private final IGuiHelper guiHelper;
    private final IDrawable smallTank;
    private final IDrawable bigTank;

    public UpgradedConstructorCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.smallTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 238, 4, 12, 13);
        this.bigTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4, 12, 50);
    }

    @Override
    public RecipeType<UpgradedConstructorRecipe> getRecipeType() {
        return IndustrialRecipeTypesAdditional.UPGRADED_CONSTRUCTOR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModuleCoreAdditional.UPGRADED_CONSTRUCTOR.getBlock().getDescriptionId());
    }


    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModuleCoreAdditional.UPGRADED_CONSTRUCTOR.getBlock()));
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
    public void setRecipe(IRecipeLayoutBuilder builder, UpgradedConstructorRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.input.size(); ++i) {
            Pair<Integer, Integer> pos = UpgradedConstructorTile.getSlotPos(i);
            builder.addSlot(RecipeIngredientRole.INPUT, 24 + pos.getLeft(), 11 + pos.getRight())
                    .addIngredients(recipe.input.get(i));
        }

        if (recipe.inputFluid1 != null && !recipe.inputFluid1.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 48, 35).setFluidRenderer(1000L, false, 12, 13).setOverlay(this.smallTank, 0, 0).addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(recipe.inputFluid1));
        }

        if (!recipe.output.isEmpty()) {
            ItemStack stack = (ItemStack)recipe.output.get();
            builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 16).addIngredient(VanillaTypes.ITEM_STACK, stack);
        }

        recipe.outputFluid.ifPresent(outputFluid ->
                builder.addSlot(RecipeIngredientRole.OUTPUT, 142, 17)
                        .setFluidRenderer(1000, false, 12, 50)
                        .setOverlay(bigTank, 0, 0)
                        .addIngredient(NeoForgeTypes.FLUID_STACK, outputFluid)
        );
    }

    @Override
    public void draw(UpgradedConstructorRecipe recipe, IRecipeSlotsView view, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        var screen = Minecraft.getInstance().screen;
        var asset = DefaultAssetProvider.DEFAULT_PROVIDER;

        EnergyBarScreenAddon.drawBackground(guiGraphics, screen, asset, 0, 12, 0, 0);
        SlotsScreenAddon.drawAsset(guiGraphics, screen, asset, 24, 11, 0, 0, 4,
                UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true,
                i -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), i -> true, 1);
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);

        AssetUtil.drawAsset(guiGraphics, screen, asset.getAsset(AssetTypes.TANK_SMALL), 45, 32);
        AssetUtil.drawAsset(guiGraphics, screen, asset.getAsset(AssetTypes.TANK_NORMAL), 139, 14);
        AssetUtil.drawAsset(guiGraphics, screen, asset.getAsset(AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 33);

        int consumed = recipe.processingTime * DissolutionChamberConfig.powerPerTick;
        EnergyBarScreenAddon.drawForeground(guiGraphics, screen, asset, 0, 12, 0, 0,
                consumed, Math.max(50000, consumed));
    }
}
