package farm.rosehearth.compatemon



import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_SAVE
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import farm.rosehearth.compatemon.api.cobblemon.properties.SizeScaleProperty
import net.minecraft.nbt.CompoundTag
import org.apache.logging.log4j.LogManager
import virtuoel.pehkui.api.ScaleTypes
import virtuoel.pehkui.util.ScaleUtils

object CompatemonKotlin {

    fun initialize() {
        val LOGGER = LogManager.getLogger()
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("Initializing Compatemon")
        LOGGER.info("----------------------------------------------------------------------------------")

        CustomPokemonProperty.register(SizeScaleProperty)
        POKEMON_ENTITY_SAVE.subscribe{ event ->
            var baseScale = ScaleUtils.getTypedScale(event.pokemonEntity,ScaleTypes.BASE,1.0f)
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)

            event.nbt.put("Pokemon",compatNBT)

        }
       // POKEMON_CAPTURED.subscribe {event ->
       //
       // }
    }


    }