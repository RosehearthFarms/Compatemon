package farm.rosehearth.compatemon.modules.apotheosis;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class ApotheosisConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Apotheosis");
	
	public static boolean DoBossPokemon = true;
	public static boolean DoBossSizing = true;
	public static String BossSizingEntities = "all";
	public static float DefaultBossSizeScale = 1.5f;
	public static boolean BossPokemonCatchable = false;
	
	public static void setup(){
	
	}
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		c.setTitle("Compatemon - Apotheosis Configuration");
		DoBossPokemon = c.getBoolean("Allow Pokemon Bosses to Spawn", "bosses", DoBossPokemon, "Allows Pokemon to be Apotheosis Bosses.");
		DoBossSizing = c.getBoolean("Increase Boss Physical Size", "bosses", DoBossSizing, "Increase the size of all Boss Mobs by configurable factor.");
		BossSizingEntities = c.getString("Bosses affected by Size Change","bosses", BossSizingEntities,"Categories: all, Pokemon, non-Pokemon, none");
		DefaultBossSizeScale = c.getFloat("Boss Size Default", "bosses", DefaultBossSizeScale, 0.01f,10.0f, "Default size of Apotheosis Bosses.");
		BossPokemonCatchable = c.getBoolean("Allow Pokemon Bosses to be Caught", "bosses", BossPokemonCatchable, "Sets the uncatchable flag on the pokemon if false. Only battleable or fightable. \nWould like to implement catchable after battle or fight.");
	}
}
