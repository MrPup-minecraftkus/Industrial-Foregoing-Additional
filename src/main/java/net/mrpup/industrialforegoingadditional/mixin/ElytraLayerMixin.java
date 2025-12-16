package net.mrpup.industrialforegoingadditional.mixin;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin {

    private static final ResourceLocation CUSTOM_ELYTRA_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(IndustrialForegoingAdditional.MOD_ID, "textures/entity/plastic_elytra.png");

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModItems.PLASTIC_ELYTRA.get())) {
            cir.setReturnValue(true);
        }
    }
    
    @Inject(method = "getElytraTexture", at = @At("HEAD"), cancellable = true)
    private void onGetElytraTexture(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (stack.is(ModItems.PLASTIC_ELYTRA.get())) {
            cir.setReturnValue(CUSTOM_ELYTRA_TEXTURE);
        }
    }
}
