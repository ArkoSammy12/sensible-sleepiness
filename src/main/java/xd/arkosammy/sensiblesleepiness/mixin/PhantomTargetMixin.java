package xd.arkosammy.sensiblesleepiness.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xd.arkosammy.sensiblesleepiness.mode.SleepyMode;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;

import java.util.List;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public abstract class PhantomTargetMixin {
    @ModifyVariable(method = "canStart()Z", at = @At("STORE"), ordinal = 0)
    private List<PlayerEntity> modifyPhantomTargets(List<PlayerEntity> targets){
        targets.removeIf(player -> ((ISleepyModeAccess) player).sensible_sleepiness$getSleepyMode() == SleepyMode.HYPERSOMNIA);
        return targets;
    }

}