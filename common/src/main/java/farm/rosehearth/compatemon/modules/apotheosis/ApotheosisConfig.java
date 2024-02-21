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
	public static String BossSizingEntities = "all";
	public static float DefaultBossSizeScale = 1.0f;
	public static boolean BossPokemonCatchable = true;
	public static float PokemonGemDropChance = 0.15f;
	public static float PokemonAffixItemRate = 0.15f;
	
	public static boolean AffixItemRarityBasedOnLevel = false;
	public static int LevelVariancePerRarity = 2;
	public static int PokemonBossLevelRange = 30;
	
	/**
	 *
	 * @param c Configuration File to load these settings into
	 */
	public static void load(Configuration c){
		c.setTitle("Compatemon - Apotheosis Configuration");
		c.setComment("Contains all settings related to Apotheosis.");
		
		//Bosses
		c.setCategoryComment("bosses", "Settings pertaining to Apotheosis Bosses");
		BossPokemonSpawnRate = c.getFloat("Spawn Rate of Boss Pokemon", "bosses", BossPokemonSpawnRate,0.0f,100.0f, "Any POSITIVE value is a percentage for the spawn rate. 0 to disable Pokemon Bosses.");
		BossPokemonCatchable = c.getBoolean("Allow Pokemon Bosses to be Caught", "bosses", BossPokemonCatchable, "Should boss pokemon be catchable?");
		DefaultBossSizeScale = c.getFloat("Boss Size Default", "bosses", DefaultBossSizeScale, APOTH_BOSS_SCALE_MIN, APOTH_BOSS_SCALE_MAX, "Default size of Apotheosis Bosses.\n!!!WARNING!!!   Large bosses have wonky physics and ai   !!!WARNING!!!");
		BossSizingEntities = c.getString("Bosses affected by Size Change","bosses", BossSizingEntities,"Which bosses are affected by the SizeScale value?\nCategories: all, Pokemon, non-Pokemon, none");
		LevelVariancePerRarity = c.getInt("Pokemon Boss Level Variance", "bosses", LevelVariancePerRarity, 0, 20, "The levels added to pokemon bosses per level of rarity.\n" +
							"Common:0, Uncommon:1n, Rare:2n, Epic:3n, Mythic:4n, Ancient:5n\n" +
							"Set to 0 to disable adding extra levels.");
		PokemonBossLevelRange = c.getInt("Level Range of Pokemon Bosses", "bosses", PokemonBossLevelRange, 0, 100,
				"The total number of levels available for a pokemon boss. \n" +
						"MaxLevel = OriginalSpawnLevel + (thisValue/2) + CalculatedLevelVariance\n" +
						"MinLevel = OriginalSpawnLevel - (thisValue/2) + CalculatedLevelVariance\n" +
						"Set to 0 to disable range variance and only use LevelVariance's config.");
		
		//Drops
		c.setCategoryComment("drops", "Settings specifically pertaining to Drops and Drop Rates");
		PokemonGemDropChance = c.getFloat("Wild Pokemon Gem Drop Rate", "drops", PokemonGemDropChance, 0.0f, 100.00f, "The base gem drop rate for wild pokemon. Bosses ALSO receive the bonus from the apotheosis adventure config file.\n0 = 0%, 100 = 100%");
		PokemonAffixItemRate = c.getFloat("Allow Wild Pokemon to drop affix items?", "drops", PokemonAffixItemRate, 0.0f,100.0f, "The chance that a wild pokemon will be given a random affix item. \n0 = 0%, 100 = 100%");
	}
}
