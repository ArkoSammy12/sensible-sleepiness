package xd.arkosammy.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xd.arkosammy.SleepyMode;
import xd.arkosammy.SleepyModeInterface;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {

    @Unique
    private static final ThreadLocal<ServerPlayerEntity> currentServerPlayerEntity = new ThreadLocal<>();

    @ModifyReceiver(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"))
    private ServerPlayerEntity captureCurrentServerPlayerEntity(ServerPlayerEntity instance){
        currentServerPlayerEntity.set(instance);
        return instance;
    }

    @ModifyVariable(method = "spawn", at = @At("STORE"), ordinal = 1)
    private int modifyProbabilityBound(int value){

        ServerPlayerEntity serverPlayerEntity = currentServerPlayerEntity.get();
        if(serverPlayerEntity == null){
            return value;
        }
        if(((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() == SleepyMode.HYPERSOMNIA){
            return 1;
        } else if (((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() == SleepyMode.PARASOMNIA){
            int daysSinceLastSlept = value / 24000;
            int newProbability = 72000 + (2500 * daysSinceLastSlept - 1);
            return newProbability;
        }
        return value;
    }

    @ModifyVariable(method = "spawn", at = @At("STORE"), ordinal = 3)
    private int modifyPhantomSpawnAmount(int value, @Local int j, @Local Random random){

        ServerPlayerEntity serverPlayerEntity = currentServerPlayerEntity.get();
        if(((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() != SleepyMode.PARASOMNIA){
            return value;
        }
        int daysSinceLastSlept = j / 24000;
        int rand = random.nextInt(daysSinceLastSlept);
        if(rand == 1){
            int n = random.nextInt(1) + 1;
            value = Math.max(value - n, 1);
        }
        return value;
    }

    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;refreshPositionAndAngles(Lnet/minecraft/util/math/BlockPos;FF)V"))
    private void onPhantomSpawned(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) int j, @Local PhantomEntity phantomEntity){

        ServerPlayerEntity serverPlayerEntity = currentServerPlayerEntity.get();
        if(serverPlayerEntity == null || ((SleepyModeInterface)serverPlayerEntity).sensible_sleepiness$getSleepyMode() != SleepyMode.PARASOMNIA) {
            return;
        }
        int daysSinceLastSlept = j / 24000;
        int oldPhantomSize = phantomEntity.getPhantomSize();
        int newPhantomSize = MathHelper.clamp(oldPhantomSize * daysSinceLastSlept, 1, 5);
        phantomEntity.setPhantomSize(newPhantomSize);

    }

}