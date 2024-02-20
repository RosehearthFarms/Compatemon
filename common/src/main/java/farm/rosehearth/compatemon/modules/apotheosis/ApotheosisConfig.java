package farm.rosehearth.compatemon.modules.apotheosis;

import farm.rosehearth.compatemon.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_BOSS_SCALE_MAX;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_BOSS_SCALE_MIN;

/**
 * Apotheosis Config File. For settings related to Apotheosis and Cobblemon
 */
public class ApotheosisConfig {
	public static final Logger LOGGER = LogManager.getLogger("Compatemon : Apotheosis");
	
	public static float BossPokemonSpawnRate = 25.0f;
	//public static int BossPokemonSpawnTiming = 3600;
	public static String BossSizingEntities = "all";
	public static float DefaultBossSizeScale = 1.0f;
	public static boolean BossPokemonCatchable = true;
	public static float PokemonGemDropChance = 0.015f;
	public static float PokemonAffixItemRate = 0.015f;
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		c.setTitle("Compatemon - Apotheosis Configuration");
		c.setComment("Contains all settings related to Apotheosis.");
		//Bosses
		BossPokemonSpawnRate = c.getFloat("Spawn Rate of Boss Pokemon", "bosses", BossPokemonSpawnRate,0.0f,100.0f, "Any POSITIVE value is a percentage for the spawn rate. 0 to disable Pokemon Bosses.");
		BossSizingEntities = c.getString("Bosses affected by Size Change","bosses", BossSizingEntities,"Categories: all, Pokemon, non-Pokemon, none");
		DefaultBossSizeScale = c.getFloat("Boss Size Default", "bosses", DefaultBossSizeScale, APOTH_BOSS_SCALE_MIN, APOTH_BOSS_SCALE_MAX, "Default size of Apotheosis Bosses.");
		BossPokemonCatchable = c.getBoolean("Allow Pokemon Bosses to be Caught", "bosses", BossPokemonCatchable, "Should boss pokemon be catchable?");
		
		//Drops
		PokemonGemDropChance = c.getFloat("Wild Pokemon Gem Drop Rate", "drops", PokemonGemDropChance, 0.0f, 100.00f, "The base gem drop rate for wild pokemon. Bosses receive the bonus from the apotheosis adventure config file.\n0 = 0%, 100 = 100%");
		PokemonAffixItemRate = c.getFloat("Allow Wild Pokemon to drop affix items?", "drops", PokemonAffixItemRate, 0.0f,100.0f, "The chance that a wild pokemon will be given a random affix item. \n0 = 0%, 100 = 100%");
	}
}
