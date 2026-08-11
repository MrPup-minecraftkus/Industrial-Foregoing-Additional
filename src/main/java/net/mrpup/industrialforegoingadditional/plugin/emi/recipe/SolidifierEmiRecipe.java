package net.mrpup.industrialforegoingadditional.plugin.emi.recipe;

import com.buuz135.industrial.config.machine.core.DissolutionChamberConfig;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.mrpup.industrialforegoingadditional.block.core.tile.UpgradedConstructorTile;
import net.mrpup.industrialforegoingadditional.config.machine.survival.SolidifierConfig;
import net.mrpup.industrialforegoingadditional.plugin.emi.IFEmiPluginAdditional;
import net.mrpup.industrialforegoingadditional.recipe.survival.SolidifierRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;

public class SolidifierEmiRecipe extends CustomEmiRecipeAdditional {
    private final RecipeHolder<SolidifierRecipe> recipe;

    public SolidifierEmiRecipe(RecipeHolder<SolidifierRecipe> recipe) {
        super(recipe.id(), IFEmiPluginAdditional.SOLIDIFIER_EMI_CATEGORY, combineIng(fromInputRecipe(((SolidifierRecipe)recipe.value()).input), fromInput(((SolidifierRecipe)recipe.value()).inputFluid)), fromOutputRecipe((ItemStack)((SolidifierRecipe)recipe.value()).output.orElse(ItemStack.EMPTY)));
        this.recipe = recipe;
    }

    public static List<EmiIngredient> fromInputRecipe(List<Ingredient> ingredients) {
        return ingredients.stream().map(EmiIngredient::of).toList();
    }

    public static List<EmiStack> fromOutputRecipe(ItemStack output) {
        return fromOutput(EmiStack.of(output));
    }

    public int getDisplayWidth() {
        return 160;
    }

    public int getDisplayHeight() {
        return 82;
    }

    public void addWidgets(WidgetHolder widgets) {
        for (int i = 0; i < recipe.value().input.size(); ++i) {
            widgets.addSlot((EmiIngredient) this.getInputs().get(i), 49, 32);
        }

        widgets.addSlot(EmiIngredient.of(List.of((EmiStack)this.getOutputs().get(0))), 118, 15).recipeContext(this);
        widgets.addTank((EmiIngredient)this.getInputs().get(this.getInputs().size() - 1), 25, 14, 14, 52, 1000).drawBack(false);

        widgets.addDrawable(0, 0, 0, 0, (draw, mouseX, mouseY, delta) -> {
            EnergyBarScreenAddon.drawBackground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 28, 33, 0, 0, 1, UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true, i -> new Color(DyeColor.MAGENTA.getFireworkColor()), i -> true, 1);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);

            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL), 23, 12);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 80, 33);

            int consumed = recipe.value().processingTime * DissolutionChamberConfig.powerPerTick;
            double maxEnergy = Math.max(50000F, consumed);
            EnergyBarScreenAddon.drawForeground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0, consumed, maxEnergy);
        });
        Rectangle rec = DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.ENERGY_BACKGROUND).getArea();
        int consumed = recipe.value().processingTime * SolidifierConfig.powerPerTick;
        widgets.addTooltipText(EnergyBarScreenAddon.getTooltip(consumed, (int)Math.max((double)50000.0F, Math.ceil((double)consumed))), 0, 12, rec.width, rec.height);
    }
}
