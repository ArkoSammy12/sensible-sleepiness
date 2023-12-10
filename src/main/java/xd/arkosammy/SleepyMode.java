package xd.arkosammy;

public enum SleepyMode {
    PARASOMNIA("sensible-sleepiness:parasomnia", "Parasomnia"),
    HYPERSOMNIA("sensible-sleepiness:hypersomnia", "Hypersomnia"),
    INSOMNIA("sensible-sleepiness:insomnia", "Insomnia");

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

    public static SleepyMode fromString(String s){
        for(SleepyMode sleepyMode : SleepyMode.values()){
            if(sleepyMode.getIdentifier().equals(s)){
                return sleepyMode;
            }
        }
        return SleepyMode.INSOMNIA;
    }

}
