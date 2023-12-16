package xd.arkosammy.sensiblesleepiness.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xd.arkosammy.sensiblesleepiness.mode.IXpAndDropChanceAccess;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {

    @ModifyVariable(method = "spawn", at = @At("STORE"), ordinal = 1)
    private int modifySpawnProbabilityBound(int value, @Local ServerPlayerEntity serverPlayerEntity){
        return switch(((ISleepyModeAccess)serverPlayerEntity).sensible_sleepiness$getSleepyMode()){

            case INSOMNIA -> value;
            case HYPERSOMNIA -> 1;
            case PARASOMNIA -> {

                int daysSinceLastSlept = value / 24000;
                int newProbability = 72000 + (2500 * daysSinceLastSlept - 1);
                yield newProbability;

            }
        };

    }

    @ModifyVariable(method = "spawn", at = @At("STORE"), ordinal = 3)
    private int modifyPhantomSpawnAmount(int value, @Local Random random, @Local ServerPlayerEntity serverPlayerEntity) {
        return switch (((ISleepyModeAccess) serverPlayerEntity).sensible_sleepiness$getSleepyMode()) {

            case INSOMNIA -> value;
            case HYPERSOMNIA -> 0;
            case PARASOMNIA -> {

                int j = MathHelper.clamp(serverPlayerEntity.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                int daysSinceLastSlept = j / 24000;
                int rand = random.nextInt(daysSinceLastSlept);
                if (rand == 1) {
                    int n = random.nextInt(2) + 1;
                    int newValue = Math.max(value - n, 1);
                    yield newValue;

                }
                yield value;

            }

        };

    }


    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;", shift = At.Shift.AFTER))
    private void modifyPhantomOnSpawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir, @Local PhantomEntity phantomEntity, @Local ServerPlayerEntity serverPlayerEntity){
        switch(((ISleepyModeAccess)serverPlayerEntity).sensible_sleepiness$getSleepyMode()){

            case INSOMNIA, HYPERSOMNIA -> {}
            case PARASOMNIA -> {

                int j = MathHelper.clamp(serverPlayerEntity.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                int daysSinceLastSlept = j / 24000;
                int oldPhantomSize = phantomEntity.getPhantomSize();
                int newPhantomSize = MathHelper.clamp(oldPhantomSize * daysSinceLastSlept, 1, 15);
                phantomEntity.setPhantomSize(newPhantomSize);
                Random random = world.getRandom();
                int newXpAmount = random.nextBetween(2, 5) * 10;
                float newDropChance = (float) (random.nextBetween(50, 95)) /100;
                ((IXpAndDropChanceAccess)phantomEntity).sensible_sleepiness$setExperiencePoints(newXpAmount);
                ((IXpAndDropChanceAccess)phantomEntity).sensible_sleepiness$setArmorDropChances(newDropChance);
                ((IXpAndDropChanceAccess)phantomEntity).sensible_sleepiness$setHandDropChances(newDropChance);

            }

        }

    }

}