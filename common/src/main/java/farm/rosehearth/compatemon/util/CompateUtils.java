package farm.rosehearth.compatemon.util;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

/**
 *
 */
public class CompateUtils {
	public static Random Rand = new Random();
	
	/**
	 * Used to generate a PokemonProperties string that can be passed to PokemonProperties.Companion.parse to create a pokemon
	 * @param world server level
	 * @param pos Position where the pokemon will attempt to generate
	 * @param minLevel Minimum level for the pokemon to generate
	 * @param levelRange Range of levels for the pokemon to generate in
	 * @return String for PokemonProperties to parse
	 */
	public static String GeneratePokemonProperties(ServerLevelAccessor world, BlockPos pos, int minLevel, int levelRange){
		String pokemon = "species=";
		String uncatch = "uncatchable";
		String lev = "l=" + (Rand.nextInt(levelRange) + minLevel);
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			uncatch = ApotheosisConfig.BossPokemonCatchable ? "" : "uncatchable";
		}
		var b = world.getBiome(pos);
		
		return "";
	}
	
	public static boolean isApothBoss(Mob entity){
		boolean isBoss = false;
		if (entity.getType().toString().equals("entity.cobblemon.pokemon")){
			isBoss = ((PokemonEntity)entity).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_BOSS);
		}else{
			isBoss = entity.saveWithoutId(new CompoundTag()).getCompound(Compatemon.implementation.persistentDataKey()).contains(APOTH_BOSS);
		}
		return isBoss;
	}
	
}

