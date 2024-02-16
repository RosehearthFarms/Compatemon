package farm.rosehearth.compatemon.modules.apotheosis;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_BOSS_SCALE_MAX;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_BOSS_SCALE_MIN;

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
	public static boolean PokemonDropGems = true;
	public static boolean PokemonDropAffixItems = true;
	
	public static void setup(){
	
	}
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		c.setTitle("Compatemon - Apotheosis Configuration");
		//Bosses
		DoBossPokemon = c.getBoolean("Allow Pokemon Bosses to Spawn", "bosses", DoBossPokemon, "Allows Pokemon to be Apotheosis Bosses.");
		DoBossSizing = c.getBoolean("Increase Boss Physical Size", "bosses", DoBossSizing, "Increase the size of all Boss Mobs by configurable factor.");
		BossSizingEntities = c.getString("Bosses affected by Size Change","bosses", BossSizingEntities,"Categories: all, Pokemon, non-Pokemon, none");
		DefaultBossSizeScale = c.getFloat("Boss Size Default", "bosses", DefaultBossSizeScale, APOTH_BOSS_SCALE_MIN,APOTH_BOSS_SCALE_MAX, "Default size of Apotheosis Bosses.");
		BossPokemonCatchable = c.getBoolean("Allow Pokemon Bosses to be Caught", "bosses", BossPokemonCatchable, "Sets the uncatchable flag on the pokemon if false. Only battleable or fightable. \nWould like to implement catchable after battle or fight.");
		
		//Drops
		PokemonDropGems = c.getBoolean("Allow Wild Pokemon to drop apotheosis gems?", "drops", PokemonDropGems, "Allows wild pokemon to drop apotheosis gems on death (possible defeat?) Will change to string with all/boss only/none at some point.");
		PokemonDropAffixItems = c.getBoolean("Allow Wild Pokemon to drop affix items?", "drops", PokemonDropAffixItems, "Allows wild pokemon to drop affix items on death (possible defeat?) Will change to string with all/boss only/none at some point.");
	}
}
