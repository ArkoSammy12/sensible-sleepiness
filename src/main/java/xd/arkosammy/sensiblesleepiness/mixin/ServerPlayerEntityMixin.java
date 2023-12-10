package xd.arkosammy.sensiblesleepiness.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.sensiblesleepiness.SleepyModeInterface;
import xd.arkosammy.sensiblesleepiness.SleepyMode;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements SleepyModeInterface {

    @Unique
    private SleepyMode sleepyMode = SleepyMode.INSOMNIA;

    @Unique
    public SleepyMode sensible_sleepiness$getSleepyMode(){
        return this.sleepyMode;
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
        this.sleepyMode = SleepyMode.fromString(sleepyModeString);
    }

}
