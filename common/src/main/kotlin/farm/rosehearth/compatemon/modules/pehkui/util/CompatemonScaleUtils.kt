package farm.rosehearth.compatemon.modules.pehkui.util

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import farm.rosehearth.compatemon.Compatemon
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig
import farm.rosehearth.compatemon.modules.pehkui.IScalableFormData
import farm.rosehearth.compatemon.modules.pehkui.IScalablePokemonEntity
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig
import farm.rosehearth.compatemon.util.CompateUtils
import farm.rosehearth.compatemon.util.CompatemonDataKeys
import farm.rosehearth.compatemon.util.CompatemonDataKeys.COMPAT_SCALE_SIZE
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Mob
import virtuoel.pehkui.api.ScaleTypes
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Class to store logic for handling scale data for pokemon by reading and writing from the entity's persistantdata
 */
open class CompatemonScaleUtils {
    companion object {

        fun setScale(entity:Entity, scaleName:String):Float{
            return setScale(entity,scaleName,CompateUtils.isApothBoss(entity as Mob))
        }
        fun setScale(entity:Entity, scaleName:String, isBoss:Boolean):Float{
            return setScale(entity,scaleName,1.0f,0.0f, isBoss)
        }
        fun setScale(entity:Entity, scaleName: String, defaultBaseScale:Float = 1.0f, addToScale:Float = 0.0f, isBoss:Boolean = false):Float{
            if(scaleName != COMPAT_SCALE_SIZE)
                return 1.0f;

            var scale: Float = getScale(entity, scaleName, defaultBaseScale, isBoss) + addToScale

            return applyScale(entity, scale)
        }



        fun getScale(entity:Entity,scaleName:String):Float{
            return getScale(entity,scaleName,1.0f,CompateUtils.isApothBoss(entity as Mob))
        }
        fun getScale(entity:Entity, scaleName: String, defaultBaseScale:Float, isBoss: Boolean):Float {
            var scaleVal = defaultBaseScale;

            if(entity.type.toString() == "entity.cobblemon.pokemon"){
                if((entity as PokemonEntity).pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).contains(scaleName)) {
                    scaleVal = entity.pokemon.persistentData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).getFloat(scaleName)
                }
            }
            else{
                scaleVal = ScaleTypes.HEALTH.getScaleData(entity).scale
            }




            if(scaleVal == defaultBaseScale) scaleVal = getNewScale(scaleName, defaultBaseScale, isBoss)





            if(entity.type.toString() == "entity.cobblemon.pokemon"){

                val compatemonData = CompoundTag()

                compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, CompoundTag())
                compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putFloat(scaleName, scaleVal)

                (entity as PokemonEntity).pokemon.persistentData.merge(compatemonData)

                Compatemon.LOGGER.debug("Size Scale of {} is being set: {}", entity.pokemon.species.name, scaleVal);
            }
            return scaleVal
        }


        /**
         * TODO: Make scaleName a Class instead of hardcoding logic and iterate through a registry of Scales
         * @return Float: randomized value of the scaleName based on config. If the scale isn't in the config, will return 1.0f
         *
         */
        private fun getNewScale(scaleName: String, defaultBaseScale:Float, isBoss: Boolean):Float{
            if(scaleName == COMPAT_SCALE_SIZE) {
                if (!PehkuiConfig.size_do_unprovided) return 1.0f
                val bossSizeMultiplier = if(isBoss) ApotheosisConfig.DefaultBossSizeScale else 1.0f ;
                var new_size = (PehkuiConfig.size_scale * defaultBaseScale * bossSizeMultiplier) +
                        (CompateUtils.Rand.nextGaussian() * PehkuiConfig.size_dev)

                new_size = if (new_size > PehkuiConfig.size_max_percentage) PehkuiConfig.size_max_percentage.toDouble() else new_size
                new_size = if (new_size < PehkuiConfig.size_min_percentage) PehkuiConfig.size_min_percentage.toDouble() else new_size
                new_size = if (new_size <= 0.00) 0.25 else new_size

                return BigDecimal.valueOf(new_size).setScale(2, RoundingMode.UP).toFloat()
            }
//            else if (scaleName == COMPAT_SCALE_WEIGHT) {
//                if (!PehkuiConfig.weight_do_unprovided) return 1.0f
//                var new_weight = (PehkuiConfig.weight_scale * defaultBaseScale) +
//                        (CompateUtils.Rand.nextGaussian() * PehkuiConfig.weight_dev)
//
//                new_weight = if (new_weight > PehkuiConfig.weight_max_percentage) PehkuiConfig.weight_max_percentage.toDouble() else new_weight
//                new_weight = if (new_weight < PehkuiConfig.weight_min_percentage) PehkuiConfig.weight_min_percentage.toDouble() else new_weight
//                new_weight = if (new_weight <= 0.00) 0.25 else new_weight
//                return BigDecimal.valueOf(new_weight).setScale(2, RoundingMode.UP).toFloat()
//            }
            return 1.0f
        }

        private fun applyScale(entity:Entity, scale:Float):Float{

            ScaleTypes.ATTACK.getScaleData(entity).scale = scale
            ScaleTypes.ATTACK_SPEED.getScaleData(entity).scale = scale
            ScaleTypes.DEFENSE.getScaleData(entity).scale = scale
            ScaleTypes.HEALTH.getScaleData(entity).scale = scale
            ScaleTypes.PROJECTILES.getScaleData(entity).scale = scale

            ScaleTypes.MODEL_HEIGHT.getScaleData(entity).scale = scale
            ScaleTypes.MODEL_WIDTH.getScaleData(entity).scale = scale


            return scale
        }
    }
}