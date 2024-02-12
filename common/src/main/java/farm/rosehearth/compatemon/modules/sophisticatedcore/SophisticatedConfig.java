package farm.rosehearth.compatemon.modules.sophisticatedcore;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SophisticatedConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Sophisticated Core");
	
	public static int StorageSearchRange = 15;
	
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
		c.setTitle("Compatemon - Sophisticated Core Configuration");
		StorageSearchRange = c.getInt("Storage Search Range", "storage", 15, 1, 64, "Range of the Storage Controller. Changing this is currently untested.");
	}
}
