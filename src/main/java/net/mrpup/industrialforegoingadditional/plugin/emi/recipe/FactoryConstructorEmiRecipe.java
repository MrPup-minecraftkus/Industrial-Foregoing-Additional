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
import net.mrpup.industrialforegoingadditional.block.core.tile.FactoryConstructorTile;
import net.mrpup.industrialforegoingadditional.config.machine.core.FactoryConstructorConfig;
import net.mrpup.industrialforegoingadditional.plugin.emi.IFEmiPluginAdditional;
import net.mrpup.industrialforegoingadditional.recipe.core.FactoryConstructorRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;

public class FactoryConstructorEmiRecipe extends CustomEmiRecipeAdditional {
    private final RecipeHolder<FactoryConstructorRecipe> recipe;

    public FactoryConstructorEmiRecipe(RecipeHolder<FactoryConstructorRecipe> recipe) {
        super(recipe.id(), IFEmiPluginAdditional.FACTORY_CONSTRUCTOR_EMI_CATEGORY, combineIng(fromInputRecipe(((FactoryConstructorRecipe)recipe.value()).input), fromInput(((FactoryConstructorRecipe)recipe.value()).inputFluid1), fromInput(((FactoryConstructorRecipe)recipe.value()).inputFluid2)), fromOutputRecipe((ItemStack)((FactoryConstructorRecipe)recipe.value()).output.orElse(ItemStack.EMPTY), (FluidStack)((FactoryConstructorRecipe)recipe.value()).outputFluid.orElse(FluidStack.EMPTY)));
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
        for(int i = 0; i < this.getInputs().size() - 2; ++i) {
            widgets.addSlot((EmiIngredient)this.getInputs().get(i), 23 + (Integer) FactoryConstructorTile.getSlotPos(i).getLeft(), 10 + (Integer)FactoryConstructorTile.getSlotPos(i).getRight());
        }

        widgets.addSlot(EmiIngredient.of(List.of((EmiStack)this.getOutputs().get(0))), 118, 15).recipeContext(this);
        widgets.addTank((EmiIngredient)this.getInputs().get(this.getInputs().size() - 2), 72, 34, 14, 15, 1000).drawBack(false);
        widgets.addTank((EmiIngredient)this.getInputs().get(this.getInputs().size() - 1), 22, 34, 14, 15, 1000).drawBack(false);
        widgets.addTank((EmiIngredient)this.getOutputs().get(1), 141, 16, 14, 52, 1000).backgroundTexture(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4).drawBack(false).recipeContext(this);

        widgets.addDrawable(0, 0, 0, 0, (draw, mouseX, mouseY, delta) -> {
            EnergyBarScreenAddon.drawBackground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 24, 11, 0, 0, 8, FactoryConstructorTile::getSlotPos, (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), (integer) -> true, 1);
            SlotsScreenAddon.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 16, 0, 0, 3, (integer) -> Pair.of(18 * (integer % 1), 18 * (integer / 1)), (integer) -> ItemStack.EMPTY, true, (integer) -> new Color(DyeColor.ORANGE.getFireworkColor()), (integer) -> true, 1);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 20, 32);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 70, 32);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL), 139, 14);
            AssetUtil.drawAsset(draw, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 33);
            int consumed = recipe.value().processingTime * FactoryConstructorConfig.powerPerTick;
            EnergyBarScreenAddon.drawForeground(draw, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 12, 0, 0, (double)consumed, (double)((int)Math.max((double)50000.0F, Math.ceil((double)consumed))));
        });
        Rectangle rec = DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.ENERGY_BACKGROUND).getArea();
        int consumed = ((FactoryConstructorRecipe)this.recipe.value()).processingTime * FactoryConstructorConfig.powerPerTick;
        widgets.addTooltipText(EnergyBarScreenAddon.getTooltip(consumed, (int)Math.max((double)50000.0F, Math.ceil((double)consumed))), 0, 12, rec.width, rec.height);
    }
}
