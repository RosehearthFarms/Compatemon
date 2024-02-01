package farm.rosehearth.compatemon.utils.pehkui

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import farm.rosehearth.compatemon.Compatemon
import farm.rosehearth.compatemon.utils.CompatemonDataKeys
import net.minecraft.nbt.CompoundTag
import virtuoel.pehkui.api.ScaleType
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

open class CompatemonScaleUtils {
    companion object {

        var rand: Random = Random()
        fun setScale(entity: PokemonEntity, scaleType: ScaleType, scaleName: String, scale: Float){
            if(scale != 1.0f){
                // Call Pehkui's setScale function
                scaleType.getScaleData(entity).setScale(scale)

                // Add the new scale to the pokemonEntity's PersistentData
                val compatemonData = CompoundTag()
                compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, CompoundTag())
                compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putFloat(scaleName, scale)
                entity.pokemon.persistentData.merge(compatemonData)
            }
        }

        fun getScale(pokemon: Pokemon, scaleName: String):Float{
            if(!pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).contains(scaleName)) {
                return getNewScale(scaleName)
            }
            return pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).getFloat(scaleName)
        }

        /*
         * function getNewScale(String scaleName)
         * @return Float: randomized value of the scaleName based on config. If the scale isn't in the config, will return 1.0f
         *
         */
        fun getNewScale(scaleName: String):Float{
            if(scaleName == "${CompatemonDataKeys.MOD_ID_PEHKUI}:${CompatemonDataKeys.COMPAT_SCALE_SIZE}") {
                if (!Compatemon.config().size_do_unprovided) return 1.0f
                var new_size = Compatemon.config().size_scale +
                        (rand.nextGaussian() * Compatemon.config().size_dev)

                new_size = if (new_size > Compatemon.config().size_max_percentage) Compatemon.config().size_max_percentage.toDouble() else new_size
                new_size = if (new_size < Compatemon.config().size_min_percentage) Compatemon.config().size_min_percentage.toDouble() else new_size
                new_size = if (new_size <= 0.00) 0.25 else new_size

                return BigDecimal.valueOf(new_size).setScale(2, RoundingMode.UP).toFloat()
            }
            else if (scaleName == "${CompatemonDataKeys.MOD_ID_COMPATEMON}:${CompatemonDataKeys.COMPAT_SCALE_WEIGHT}") {
                if (!Compatemon.config().weight_do_unprovided) return 1.0f
                var new_weight = Compatemon.config().weight_scale +
                        (rand.nextGaussian() * Compatemon.config().weight_dev)

                new_weight = if (new_weight > Compatemon.config().weight_max_percentage) Compatemon.config().weight_max_percentage.toDouble() else new_weight
                new_weight = if (new_weight < Compatemon.config().weight_min_percentage) Compatemon.config().weight_min_percentage.toDouble() else new_weight
                new_weight = if (new_weight <= 0.00) 0.25 else new_weight
                return BigDecimal.valueOf(new_weight).setScale(2, RoundingMode.UP).toFloat()
            }
            return 1.0f
        }
    }
}