package farm.rosehearth.cobblemon_sizes;

import farm.rosehearth.cobblemon_sizes.event.EntityLoadHandler;
import farm.rosehearth.cobblemon_sizes.mixin.MobEntityAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;



public class CobblemonSizesFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		CobblemonSizes.LOGGER.info("Hello Fabric world from Fight or Flight!");

		//CobblemonSizes.init((pokemonEntity, priority, goal) -> ((MobEntityAccessor) (Object) pokemonEntity).goalSelector().addGoal(priority, goal));

		ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadHandler());
	}
}