package farm.rosehearth.compatemon.modules.quark;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class QuarkConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Quark");
	
	public static boolean LoadAmbientMusicDiscs = true;
	public static boolean PokemonDropDiscOnPlayerKill = true;
	
	
	
	
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
		c.setTitle("Compatemon - Quark Configuration");
		LoadAmbientMusicDiscs = c.getBoolean("Add extra ambient discs?", "discs", true, "Should Ambient Music discs from Pokemon be loaded?");
		PokemonDropDiscOnPlayerKill = c.getBoolean("Pokemon drop music discs", "discs", true, "Should Pokemon drop music discs when killed by a player?");
	}
}
