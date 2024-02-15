package farm.rosehearth.compatemon.modules.sophisticatedcore;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.SOPHISTICATED_SEARCH_MAX;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.SOPHISTICATED_SEARCH_MIN;

public class SophisticatedConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Sophisticated Storage");
	
	public static int StorageSearchRange = 32;
	
	/**
	 *
	 */
	public static void setup(){
	
	}
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		c.setTitle("Compatemon - Sophisticated Storage Configuration");
		StorageSearchRange = c.getInt("Storage Search Range", "core", StorageSearchRange, SOPHISTICATED_SEARCH_MIN, SOPHISTICATED_SEARCH_MAX, "Range of the Storage Controller. Changing this is currently untested, and the potential max is unknown..");
	}
}
