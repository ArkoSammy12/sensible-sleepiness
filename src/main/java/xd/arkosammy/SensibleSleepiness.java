package xd.arkosammy;

import net.fabricmc.api.DedicatedServerModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xd.arkosammy.commands.SensibleSleepinessCommandManager;

public class SensibleSleepiness implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("sensible-sleepiness");

	@Override
	public void onInitializeServer() {

		ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> ((SleepyModeInterface) newPlayer).sensible_sleepiness$setSleepyMode(((SleepyModeInterface)oldPlayer).sensible_sleepiness$getSleepyMode())));
		CommandRegistrationCallback.EVENT.register(SensibleSleepinessCommandManager::registerCommands);

	}
}