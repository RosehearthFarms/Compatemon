package farm.rosehearth.compatemon.utils.pehkui

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import farm.rosehearth.compatemon.Compatemon.LOGGER
import farm.rosehearth.compatemon.utils.CompatemonDataKeys
import net.minecraft.nbt.CompoundTag
import virtuoel.pehkui.api.ScaleType

open class CompatemonScaleUtils {
    companion object {

        fun setScale(entity: PokemonEntity, scaleType: ScaleType, scaleName: String, scale: Float){
            if(scale != 1.0f){
                scaleType.getScaleData(entity).setScale(scale)
                LOGGER.debug("Setting the scale of " + entity.pokemon.showdownId() + " to $scale")


                // Add the new scale to the pokemonEntity's PersistentData
                val compatemonData = CompoundTag()
                compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, CompoundTag())
                compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putFloat("$scaleName", scale)
                entity.pokemon.persistentData.merge(compatemonData)
                //entity.pokemon.saveToNBT()
            }
        }

    }
}