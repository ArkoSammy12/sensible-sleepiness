package xd.arkosammy.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xd.arkosammy.InsomniaFieldMixinInterface;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements InsomniaFieldMixinInterface {

    @Unique
    private boolean isInsomniaEnabled;

    public boolean sensible_sleepiness$isInsomniaEnabled(){
        return this.isInsomniaEnabled;
    }

    public void sensible_sleepiness$setInsomnia(boolean insomnia){
        this.isInsomniaEnabled = insomnia;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCustomDataToNbt (NbtCompound tag, CallbackInfo info) {
        tag.putBoolean("isInsomniaEnabled", isInsomniaEnabled);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound tag, CallbackInfo info) {
        isInsomniaEnabled = tag.getBoolean("isInsomniaEnabled");
    }

}
