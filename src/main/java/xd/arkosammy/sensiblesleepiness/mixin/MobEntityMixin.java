package xd.arkosammy.sensiblesleepiness.mixin;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xd.arkosammy.sensiblesleepiness.mode.IXpAndDropChanceAccess;

import java.util.Arrays;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin implements IXpAndDropChanceAccess {

    @Shadow protected int experiencePoints;

    @Shadow @Final protected float[] armorDropChances;

    @Shadow @Final protected float[] handDropChances;

    @Override
    public int sensible_sleepiness$getExperiencePoints(){
        return this.experiencePoints;
    }

    @Override
    public void sensible_sleepiness$setExperiencePoints(int experiencePoints){
        this.experiencePoints = experiencePoints;
    }

    @Override
    public float[] sensible_sleepiness$getArmorDropChances(){
        return this.armorDropChances;
    }

    @Override
    public void sensible_sleepiness$setArmorDropChances(float newChance){
        Arrays.fill(this.armorDropChances, newChance);
    }

    @Override
    public float[] sensible_sleepiness$getHandDropChances(){
        return this.handDropChances;
    }

    @Override
    public void sensible_sleepiness$setHandDropChances(float newChance){
        Arrays.fill(this.handDropChances, newChance);
    }

}
