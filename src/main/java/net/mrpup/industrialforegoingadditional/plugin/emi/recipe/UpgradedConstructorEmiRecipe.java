package net.mrpup.industrialforegoingadditional.plugin.emi.recipe;

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
import net.mrpup.industrialforegoingadditional.config.machine.core.UpgradedConstructorConfig;
import net.mrpup.industrialforegoingadditional.plugin.emi.IFEmiPluginAdditional;
import net.mrpup.industrialforegoingadditional.recipe.core.UpgradedConstructorRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;

public class UpgradedConstructorEmiRecipe extends CustomEmiRecipeAdditional {
    private final RecipeHolder<UpgradedConstructorRecipe> recipe;

    public UpgradedConstructorEmiRecipe(RecipeHolder<UpgradedConstructorRecipe> recipe) {
        super(recipe.id(), IFEmiPluginAdditional.UPGRADED_CONSTRUCTOR_EMI_CATEGORY, combineIng(fromInputRecipe(((UpgradedConstructorRecipe)recipe.value()).input), fromInput(((UpgradedConstructorRecipe)recipe.value()).inputFluid1)), fromOutputRecipe((ItemStack)((UpgradedConstructorRecipe)recipe.value()).output.orElse(ItemStack.EMPTY), (FluidStack)((UpgradedConstructorRecipe)recipe.value()).outputFluid.orElse(FluidStack.EMPTY)));
        this.recipe = recipe;
    }

    public static List<EmiIngredient> fromInputRecipe(List<Ingredient> ingredients) {
        return ingredients.stream().map(EmiIngredient::of).toList();
    }

    public static List<EmiStack> fromOutputRecipe(ItemStack output, FluidStack fluidStack) {
        return fromOutput(output, fluidStack);
    }

    public int getDisplayWidth() {
        return 160;
    }

    public int getDisplayHeight() {
        return 82;
    }

    public void addWidgets(WidgetHolder widgets) {
        for(int i = 0; i < this.getInputs().size() - 1; ++i) {
            widgets.addSlot((EmiIngredient)this.getInputs().get(i), 23 + (Integer) UpgradedConstructorTile.getSlotPos(i).getLeft(), 10 + (Integer)UpgradedConstructorTile.getSlotPos(i).getRight());
        }

        widgets.addSlot(EmiIngredient.of(List.of((EmiStack)this.getOutputs().get(0))), 118, 15).recipeContext(this);
        widgets.addTank((EmiIngredient)this.getInputs().get(this.getInputs().size() - 1), 47, 34, 14, 15, 1000).drawBack(false);
        widgets.addTank((EmiIngredient)this.getOutputs().get(1), 141, 16, 14, 52, 1000).backgroundTexture(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4).drawBack(false).recipeContext(this);

        widgets.addDrawable(0, 0, 0, 0, (draw, mouseX, mouseY, delta) -> {
            EnergyBarScreenAddon.drawBackground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 24, 11, 0, 0, 4, UpgradedConstructorTile::getSlotPos, i -> ItemStack.EMPTY, true, i -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), i -> true, 1);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);

            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 45, 32);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL), 139, 14);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 33);

            int consumed = recipe.value().processingTime * UpgradedConstructorConfig.powerPerTick;
            EnergyBarScreenAddon.drawForeground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0,
                    consumed, Math.max(50000, consumed));
        });
        Rectangle rec = DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.ENERGY_BACKGROUND).getArea();
        int consumed = ((UpgradedConstructorRecipe)this.recipe.value()).processingTime * UpgradedConstructorConfig.powerPerTick;
        widgets.addTooltipText(EnergyBarScreenAddon.getTooltip(consumed, (int)Math.max((double)50000.0F, Math.ceil((double)consumed))), 0, 12, rec.width, rec.height);
    }
}
