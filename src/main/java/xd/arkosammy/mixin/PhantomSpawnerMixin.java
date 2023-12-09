package xd.arkosammy.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xd.arkosammy.SleepyMode;
import xd.arkosammy.SleepyModeInterface;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {

    @Unique
    private static ThreadLocal<ServerPlayerEntity> currentServerPlayerEntity = new ThreadLocal<>();

    @ModifyReceiver(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"))
    private ServerPlayerEntity captureCurrentServerPlayerEntity(ServerPlayerEntity instance){
        currentServerPlayerEntity.set(instance);
        return instance;
    }

    @ModifyExpressionValue(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 1))
    private int onPhantomAttemptSpawn(int original, @Local int j, @Local Random random){

        ServerPlayerEntity serverPlayerEntity = currentServerPlayerEntity.get();

        if(serverPlayerEntity == null){
            return original;
        }

        if(((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() == SleepyMode.HYPERSOMNIA){
            return 0;
        } else if (((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() == SleepyMode.PARASOMNIA){
            int daysSinceLastSlept = j / 24000;
            int newProbability = 72000 + (2500 * daysSinceLastSlept - 1);
            return newProbability;

        }
        return original;

    }

    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;refreshPositionAndAngles(Lnet/minecraft/util/math/BlockPos;FF)V"))
    private void onPhantomSpawned(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) int j, @Local PhantomEntity phantomEntity){

        ServerPlayerEntity serverPlayerEntity = currentServerPlayerEntity.get();

        if(serverPlayerEntity == null || ((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() != SleepyMode.PARASOMNIA) {
            return;
        }

        int daysSinceLastSlept = j / 24000;
        int oldPhantomSize = phantomEntity.getPhantomSize();
        int newPhantomSize = oldPhantomSize * daysSinceLastSlept;
        phantomEntity.setPhantomSize(newPhantomSize);

    }

}