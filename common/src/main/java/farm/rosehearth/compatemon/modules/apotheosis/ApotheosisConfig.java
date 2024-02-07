package farm.rosehearth.compatemon.modules.apotheosis;

import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 *
 */
public class ApotheosisConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Apotheosis");
	
	public static boolean doBossPokemon = true;
	public static boolean doBossSizing = true;
	public static String bossSizingAffects = "all";
	public static float bossDefaultSizeScale = 1.5f;
	public static boolean bossPokemonCatchable = false;
	
	public static void setup(){
	
	}
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		//Configuration c = new Configuration(new File(Compatemon.configDir, "apotheosis.cfg"));
		c.setTitle("Compatemon Apotheosis Integration Configuration");
		doBossPokemon = c.getBoolean("Allow Pokemon Bosses to Spawn", "bosses", doBossPokemon, "Allows Pokemon to be Apotheosis Bosses.");
		doBossSizing = c.getBoolean("Increase Boss Physical Size", "bosses", doBossSizing, "Increase the size of all Boss Mobs by configurable factor.");
		bossSizingAffects = c.getString("Bosses affected by Size Change","bosses",bossSizingAffects,"Categories: all, Pokemon, non-Pokemon, none");
		bossDefaultSizeScale = c.getFloat("Boss Size Default", "bosses", bossDefaultSizeScale, 0.01f,10.0f, "Default size of Apotheosis Bosses.");
		bossPokemonCatchable = c.getBoolean("Allow Pokemon Bosses to be Caught", "bosses", bossPokemonCatchable, "Sets the uncatchable flag on the pokemon if false. Only battleable or fightable. \nWould like to implement catchable after battle or fight.");
	}
}
