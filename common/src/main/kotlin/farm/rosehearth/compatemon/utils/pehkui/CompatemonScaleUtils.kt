package farm.rosehearth.compatemon.utils.pehkui

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import virtuoel.pehkui.api.ScaleType

open class CompatemonScaleUtils {
    companion object {

        fun setScale(entity: PokemonEntity, scaleType: ScaleType, scale: Float){
            if(scale != 1.0f){
                scaleType.getScaleData(entity).setScale(scale)
                //entity.pokemon.saveToNBT()
            }
        }
    }
}