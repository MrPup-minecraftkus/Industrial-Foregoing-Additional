package net.mrpup.industrialforegoingadditional.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.mrpup.industrialforegoingadditional.config.item.PlasticElytraConfig;
import net.mrpup.industrialforegoingadditional.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ElytraSpeedMixin {

    @Inject(method = "travel", at = @At("TAIL"))
    private void applyElytraSpeedBoost(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isFallFlying()) {
            return;
        }

        ItemStack elytra = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (elytra.is(ModItems.PLASTIC_ELYTRA.get())) {
            Vec3 delta = entity.getDeltaMovement();
            double targetMaxSpeed = PlasticElytraConfig.MaxSpeed;

            if (delta.length() < targetMaxSpeed) {
                entity.setDeltaMovement(delta.scale(1.05));
            }
        }
    }
}