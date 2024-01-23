package farm.rosehearth.compatemon;

import farm.rosehearth.compatemon.event.EntityLoadHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;



public class CompatemonFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		Compatemon.LOGGER.info("Hello Fabric world from Fight or Flight!");

		//CobblemonSizes.init((pokemonEntity, priority, goal) -> ((MobEntityAccessor) (Object) pokemonEntity).goalSelector().addGoal(priority, goal));

		ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadHandler());
	}
}