package xd.arkosammy.sensiblesleepiness;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xd.arkosammy.sensiblesleepiness.commands.SensibleSleepinessCommandManager;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;
import xd.arkosammy.sensiblesleepiness.mode.SleepyMode;

public class SensibleSleepiness implements ModInitializer {

	public static final String MOD_ID = "sensible-sleepiness";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final GameRules.Key<EnumRule<SleepyMode>> DEFAULT_SLEEPY_MODE_RULE = GameRuleRegistry.register("defaultSleepyMode", GameRules.Category.MISC, GameRuleFactory.createEnumRule(SleepyMode.Insomnia, SleepyMode.values()));

	@Override
	public void onInitialize() {

		ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> ((ISleepyModeAccess) newPlayer).sensible_sleepiness$setSleepyMode(((ISleepyModeAccess)oldPlayer).sensible_sleepiness$getSleepyMode())));
		CommandRegistrationCallback.EVENT.register(SensibleSleepinessCommandManager::registerCommands);
		LOGGER.info("Thanks to Baconbacon123 for coming up with the name of this mod!");

	}
}