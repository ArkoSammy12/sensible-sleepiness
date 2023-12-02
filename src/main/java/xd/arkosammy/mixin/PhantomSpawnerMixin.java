package xd.arkosammy.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xd.arkosammy.SleepyMode;
import xd.arkosammy.SleepyModeInterface;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {
    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"), cancellable = true)
    public void onPhantomAttemptSpawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir, @Local int i, @Local ServerPlayerEntity serverPlayerEntity) {
        if(!((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode().equals(SleepyMode.HYPERSOMNIA)) {
            cir.setReturnValue(i);
        }
    }

}