# Sensible Sleepiness
A small, server-side Fabric mod to allow each player to select how they want to interact with Phantoms. Players can select between one of three "sleepy" modes via commands. However, this mod does not override the `doInsomnia` gamerule. That is, if it set to false, then no Phantoms will spawn for everyone, regardless of their sleepy mode.

## Insomnia mode

`/sensible-sleepiness mode insomnia`

This is the default sleepy mode for those who want to keep the vanilla behaviour of Phantoms for themselves.

## Hypersomnia mode

`/sensible-sleepiness mode hypersomnia`

This mode prevents Phantoms from spawning around the player with the mode enabled. They will also become invisible to already spawned Phantoms.

## Parasomnia mode

`/sensible-sleepiness mode parasomnia`

This mode increases the difficulty of Phantoms by making them bigger and deal more damage the more nights the player has gone without sleeping. The period of grace becomes shorter. Though there is a chance that Phantoms will appear in smaller groups, this chance will only decrease the more nights go by without sleeping. 

To compensate for the added difficulty, Phantoms spawned for players with this mode enabled will have higher chances to drop more experience, and better chances to drop loot upon death.

You can view your current sleepy mode by running
`/sensible-sleepiness mode`.


## Support

If you would like to report a bug, or make a suggestion, you can do so via the mod's [issue tracker](https://github.com/ArkoSammy12/sensible-sleepiness/issues) or join my [Discord server](https://discord.gg/UKr8n3b3ze). 


## Credits

- Thanks to @Baconbacon123 for giving me the idea and name for this mod.

## Building

Clone this repository on your PC, then open your command line prompt on the main directory of the mod, and run the command: `gradlew build`. Once the build is successful, you can find the mod under `/sensible-sleepiness/build/libs`. Use the .jar file without the `"sources"`.
