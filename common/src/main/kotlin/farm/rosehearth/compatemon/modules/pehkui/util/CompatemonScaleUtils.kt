package farm.rosehearth.compatemon.modules.pehkui.util

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig
import farm.rosehearth.compatemon.util.CompateUtils
import farm.rosehearth.compatemon.util.CompatemonDataKeys
import farm.rosehearth.compatemon.util.CompatemonDataKeys.COMPAT_SCALE_SIZE
import farm.rosehearth.compatemon.util.CompatemonDataKeys.COMPAT_SCALE_WEIGHT
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.Nullable
import virtuoel.pehkui.api.ScaleType
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Class to store logic for handling scale data for pokemon by reading and writing from the entity's persistantdatat
 */
open class CompatemonScaleUtils {
    companion object {

        /**
         *
         */
        fun setScale(entity: Entity, scaleType: ScaleType, scaleName: String, @Nullable defaultBaseScale:Float = 1.0f, @Nullable addToScale:Float = 0.0f):Float{

            // get current scale
            // pokemon -> set persistant data
            // add modifier to scale
            // set the scale
            // if it's a pokemon, recalc health and hitbox and stuff

            var scale: Float = getScale(entity,scaleType,scaleName, defaultBaseScale) + addToScale

            if(entity.type.toString() == "entity.cobblemon.pokemon"){

                val compatemonData = CompoundTag()

                compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, CompoundTag())
                compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putFloat(scaleName, scale)

                (entity as PokemonEntity).pokemon.persistentData.merge(compatemonData)
            }

            scaleType.getScaleData(entity).setScale(scale)
            // Add the new scale to the pokemonEntity's PersistentData

            // if it's a pokemon, recalc health and hitbox and stuff

            return scale
        }


        /**
         *
         */
        private fun getScale(entity:Entity, scaleType: ScaleType, scaleName: String, defaultBaseScale:Float):Float {
            var scaleVal = defaultBaseScale;
            if(entity.type.toString() == "entity.cobblemon.pokemon"){
                if((entity as PokemonEntity).pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).contains(scaleName))
                    scaleVal = (entity as PokemonEntity).pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).getFloat(scaleName)

            }
            else{
                scaleVal = scaleType.getScaleData(entity).scale
            }

            if(scaleVal == defaultBaseScale) scaleVal = getNewScale(scaleName, defaultBaseScale)
            return scaleVal
        }

//
//        fun getScale(pokemon: Pokemon, scaleName: String):Float{
//            if(!pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).contains(scaleName)) {
//                return getNewScale(scaleName)
//            }
//            return pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).getFloat(scaleName)
//        }

        /**
         * TODO: Make scaleName a Class instead of hardcoding logic and iterate through a registry of Scales
         * @return Float: randomized value of the scaleName based on config. If the scale isn't in the config, will return 1.0f
         *
         */
        private fun getNewScale(scaleName: String, defaultBaseScale:Float):Float{
            if(scaleName == COMPAT_SCALE_SIZE) {
                if (!PehkuiConfig.size_do_unprovided) return 1.0f
                var new_size = (PehkuiConfig.size_scale * defaultBaseScale) +
                        (CompateUtils.Rand.nextGaussian() * PehkuiConfig.size_dev)

                new_size = if (new_size > PehkuiConfig.size_max_percentage) PehkuiConfig.size_max_percentage.toDouble() else new_size
                new_size = if (new_size < PehkuiConfig.size_min_percentage) PehkuiConfig.size_min_percentage.toDouble() else new_size
                new_size = if (new_size <= 0.00) 0.25 else new_size

                return BigDecimal.valueOf(new_size).setScale(2, RoundingMode.UP).toFloat()
            }
            else if (scaleName == COMPAT_SCALE_WEIGHT) {
                if (!PehkuiConfig.weight_do_unprovided) return 1.0f
                var new_weight = (PehkuiConfig.weight_scale * defaultBaseScale) +
                        (CompateUtils.Rand.nextGaussian() * PehkuiConfig.weight_dev)

                new_weight = if (new_weight > PehkuiConfig.weight_max_percentage) PehkuiConfig.weight_max_percentage.toDouble() else new_weight
                new_weight = if (new_weight < PehkuiConfig.weight_min_percentage) PehkuiConfig.weight_min_percentage.toDouble() else new_weight
                new_weight = if (new_weight <= 0.00) 0.25 else new_weight
                return BigDecimal.valueOf(new_weight).setScale(2, RoundingMode.UP).toFloat()
            }
            return 1.0f
        }
    }
}