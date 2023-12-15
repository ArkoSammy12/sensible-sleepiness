package xd.arkosammy.sensiblesleepiness.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyReturnValue(method = "isTarget", at = @At("RETURN"))
    protected boolean canPhantomTargetPlayer(boolean original, @Local LivingEntity entity){
        return original;
    }

}