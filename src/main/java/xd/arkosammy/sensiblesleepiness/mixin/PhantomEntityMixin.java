package xd.arkosammy.sensiblesleepiness.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;
@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends LivingEntityMixin {

    @Override
    protected boolean canPhantomTargetPlayer(boolean original, LivingEntity entity){
        if(!(entity instanceof ServerPlayerEntity serverPlayerEntity)){
            return original;
        }
        return switch(((ISleepyModeAccess)serverPlayerEntity).sensible_sleepiness$getSleepyMode()){
            case Insomnia, Parasomnia -> original;
            case Hypersomnia -> false;
        };

    }

}
