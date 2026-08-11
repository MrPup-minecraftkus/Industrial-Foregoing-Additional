package net.mrpup.industrialforegoingadditional.recipe.output;

/* Update
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public record Chance(ItemStack stack, float chance) {
    public static final Codec<Chance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(Chance::stack),
            Codec.FLOAT.optionalFieldOf("chance", 1.0f).forGetter(Chance::chance)
    ).apply(instance, Chance::new));
}

 */
