package net.mrpup.industrialforegoingadditional.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.mrpup.industrialforegoingadditional.config.item.PlasticElytraConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ElytraSpeedMixin {

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isFallFlying()Z", shift = At.Shift.AFTER))
    private void applyElytraSpeedBoost(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Vec3 delta = entity.getDeltaMovement();

        double targetMaxSpeed = PlasticElytraConfig.MaxSpeed;

        if (delta.length() < targetMaxSpeed) {
            entity.setDeltaMovement(delta.scale(1.05));
        }
    }
}