package xd.arkosammy.sensiblesleepiness.mode;

public interface IXpAndDropChanceAccess {


    int sensible_sleepiness$getExperiencePoints();

    void sensible_sleepiness$setExperiencePoints(int experiencePoints);

    float[] sensible_sleepiness$getArmorDropChances();

    void sensible_sleepiness$setArmorDropChances(float newChance);

    float[] sensible_sleepiness$getHandDropChances();

    void sensible_sleepiness$setHandDropChances(float newChance);

}
