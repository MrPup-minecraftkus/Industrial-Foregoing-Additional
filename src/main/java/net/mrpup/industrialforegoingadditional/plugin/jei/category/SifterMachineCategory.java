package net.mrpup.industrialforegoingadditional.plugin.jei.category;

/* Update
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.mrpup.industrialforegoingadditional.block.core.tile.UpgradedConstructorTile;
import net.mrpup.industrialforegoingadditional.config.machine.survival.SifterMachineConfig;
import net.mrpup.industrialforegoingadditional.module.ModuleCoreAdditional;
import net.mrpup.industrialforegoingadditional.plugin.jei.IndustrialRecipeTypesAdditional;
import net.mrpup.industrialforegoingadditional.recipe.output.Chance;
import net.mrpup.industrialforegoingadditional.recipe.survival.SifterMachineRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;

public class SifterMachineCategory implements IRecipeCategory<SifterMachineRecipe> {

    private final IDrawable smallTank;
    private final IDrawable bigTank;

    public SifterMachineCategory(IGuiHelper guiHelper) {
        this.smallTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 238, 4, 12, 13);
        this.bigTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4, 12, 50);
    }

    @Override
    public RecipeType<SifterMachineRecipe> getRecipeType() {
        return IndustrialRecipeTypesAdditional.SIFTER_MACHINE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModuleCoreAdditional.SIFTER_MACHINE.getBlock().getDescriptionId());
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
    public void setRecipe(IRecipeLayoutBuilder builder, SifterMachineRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.input1.size(); ++i) {
            builder.addSlot(RecipeIngredientRole.INPUT, 28, 34)
                    .addIngredients(recipe.input1.get(i));
        }

        for (int i = 0; i < recipe.input2.size(); ++i) {
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 34)
                    .addIngredients(recipe.input2.get(i));;
        }

        if (!recipe.inputFluid1.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 73, 35)
                    .setFluidRenderer(1000L, false, 12, 13)
                    .setOverlay(smallTank, 0, 0)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.inputFluid1);
        }

        if (recipe.outputs != null) {
            List<Chance> chanceOutputs = recipe.outputs;
            for (int i = 0; i < Math.min(3, chanceOutputs.size()); i++) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 16 + i * 18)
                        .addIngredient(VanillaTypes.ITEM_STACK, chanceOutputs.get(i).stack());
            }
        }

        recipe.outputFluid.ifPresent(fluid ->
                builder.addSlot(RecipeIngredientRole.OUTPUT, 142, 17)
                        .setFluidRenderer(1000L, false, 12, 50)
                        .setOverlay(bigTank, 0, 0)
                        .addIngredient(NeoForgeTypes.FLUID_STACK, fluid)
        );
    }

    @Override
    public void draw(SifterMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        var screen = Minecraft.getInstance().screen;
        var assets = DefaultAssetProvider.DEFAULT_PROVIDER;
        Font font = Minecraft.getInstance().font;

        EnergyBarScreenAddon.drawBackground(guiGraphics, screen, assets, 0, 12, 0, 0);
        SlotsScreenAddon.drawAsset(guiGraphics, screen, assets, 6, 34, 0, 0, 1, UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true, i -> new Color(DyeColor.BLUE.getFireworkColor()), i -> true, 1);
        SlotsScreenAddon.drawAsset(guiGraphics, screen, assets, 28, 34, 0, 0, 1, UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true, i -> new Color(DyeColor.MAGENTA.getFireworkColor()), i -> true, 1);
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);

        AssetUtil.drawAsset(guiGraphics, screen, assets.getAsset(AssetTypes.TANK_SMALL), 70, 32);
        AssetUtil.drawAsset(guiGraphics, screen, assets.getAsset(AssetTypes.TANK_NORMAL), 139, 14);
        AssetUtil.drawAsset(guiGraphics, screen, IAssetProvider.getAsset(assets, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 33);

        int consumed = recipe.processingTime * SifterMachineConfig.powerPerTick;
        double maxEnergy = Math.max(50000F, consumed);
        EnergyBarScreenAddon.drawForeground(guiGraphics, screen, assets, 0, 12, 0, 0, consumed, maxEnergy);

        if (recipe.outputs != null) {
            float scale = 0.75f;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale, 1.0f);

            for (int i = 0; i < Math.min(3, recipe.outputs.size()); i++) {
                float chance = recipe.outputs.get(i).chance();
                if (chance < 1.0f) {
                    String chanceText = (int)(chance * 100) + "%";

                    int xText = Math.round(120f / scale);
                    int yText = Math.round((23f + (i * 18)) / scale);

                    guiGraphics.drawString(font, chanceText, xText, yText, 0xFF4CD2FF, false);
                }
            }
            guiGraphics.pose().popPose();
        }
    }
}

 */
