package xd.arkosammy.sensiblesleepiness.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.sensiblesleepiness.SensibleSleepiness;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;
import xd.arkosammy.sensiblesleepiness.mode.SleepyMode;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ISleepyModeAccess {

    @Shadow public abstract ServerWorld getServerWorld();

    @Unique
    private SleepyMode sleepyMode = SleepyMode.Insomnia;

    @Unique
    public SleepyMode sensible_sleepiness$getSleepyMode(){
        return this.sleepyMode;
    }

    @Inject(method = "<init>*", at = @At("RETURN"), remap = false)
    private void onServerPlayerEntity(CallbackInfo ci) {
        this.sleepyMode = this.getServerWorld().getGameRules().get(SensibleSleepiness.DEFAULT_SLEEPY_MODE_RULE).get();
    }

    @Unique
    public void sensible_sleepiness$setSleepyMode(SleepyMode sleepyMode){
        this.sleepyMode = sleepyMode;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCustomDataToNbt (NbtCompound tag, CallbackInfo info) {
        tag.putString("sleepyMode", sleepyMode.getIdentifier());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound tag, CallbackInfo info) {
        String sleepyModeString = tag.getString("sleepyMode");
        this.sleepyMode = SleepyMode.fromStringIdentifier(sleepyModeString).orElse(this.getServerWorld().getGameRules().get(SensibleSleepiness.DEFAULT_SLEEPY_MODE_RULE).get());
    }

}
