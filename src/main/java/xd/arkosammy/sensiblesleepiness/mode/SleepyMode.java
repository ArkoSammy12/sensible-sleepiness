package xd.arkosammy.sensiblesleepiness.mode;

import java.util.Optional;

public enum SleepyMode {
    // In PascalCase so they look nice in the gamerule
    Parasomnia("sensible-sleepiness:parasomnia", "Parasomnia"),
    Hypersomnia("sensible-sleepiness:hypersomnia", "Hypersomnia"),
    Insomnia("sensible-sleepiness:insomnia", "Insomnia");

    private final String identifier;
    private final String displayName;

    SleepyMode(String identifier, String displayName){
        this.identifier = identifier;
        this.displayName = displayName;
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public static Optional<SleepyMode> fromStringIdentifier(String s){
        for(SleepyMode sleepyMode : SleepyMode.values()){
            if(sleepyMode.getIdentifier().equals(s)){
                return Optional.of(sleepyMode);
            }
        }
        return Optional.empty();
    }

}
