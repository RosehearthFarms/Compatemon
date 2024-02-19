package farm.rosehearth.compatemon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;


public class CompatemonFabric implements ModInitializer, CompatemonImplementation {
	@Override
	public void onInitialize() {
		Compatemon.LOGGER.info("Hello Fabric world from Compatemon!");
		Compatemon.preInitialize(this);
		Compatemon.init();
	}
	
	@NotNull
	@Override
	public ModAPI getModAPI() {
		return ModAPI.FABRIC;
	}
	
	
	@Override
	public boolean isModInstalled(@NotNull String modID) {
		return FabricLoader.getInstance().isModLoaded(modID);
	}
	
	@Override
	public void postCommonInitialization() {
	}
	
	@Override
	public void registerEvents() {
	
	}
	
	@NotNull
	@Override
	public String persistentDataKey() {
		return "FabricData";
	}
}