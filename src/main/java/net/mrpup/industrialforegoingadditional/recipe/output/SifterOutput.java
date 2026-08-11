package net.mrpup.industrialforegoingadditional.recipe.output;

/* Update

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.mrpup.industrialforegoingadditional.item.sifter.SifterItem;

import java.util.function.Supplier;

public record SifterOutput(ItemStack stack, float baseChance, Supplier<? extends Item> minSifter) {

    public static SifterOutput of(ItemStack stack, float baseChance, Supplier<? extends Item> minSifter) {
        return new SifterOutput(stack, baseChance, minSifter);
    }

    public static SifterOutput of(ItemStack stack, float baseChance) {
        return new SifterOutput(stack, baseChance, null);
    }

    private SifterItem.SifterTier minTier() {
        if (minSifter == null) return SifterItem.SifterTier.WOOD;
        if (!(minSifter.get() instanceof SifterItem si)) {
            throw new IllegalStateException("minSifter must be a SifterItem");
        }
        return si.getTier();
    }

    public boolean appliesTo(SifterItem.SifterTier tier) {
        return tier.ordinal() >= minTier().ordinal();
    }

    public Chance toChance(SifterItem.SifterTier tier) {
        float scaled = Math.min(1.0f, baseChance * tier.getLootModifier());
        return new Chance(stack, scaled);
    }
}

 */
