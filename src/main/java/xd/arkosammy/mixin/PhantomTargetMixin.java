package xd.arkosammy.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public abstract class PhantomTargetMixin {
    @ModifyVariable(method = "canStart()Z", at = @At("STORE"), index = 1)
    private List<PlayerEntity> modifyPhantomTargets(List<PlayerEntity> targets){
        targets.removeIf(player -> !((InsomniaFieldMixinInterface) player).hypersomnia$isInsomniaEnabled());
        return targets;
    }

}