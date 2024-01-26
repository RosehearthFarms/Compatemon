package farm.rosehearth.compatemon


import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_CAPTURED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_LOAD
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_SAVE
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_SENT_PRE
import farm.rosehearth.compatemon.Compatemon.LOGGER
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_JSON_LOADED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_JSON_SAVED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_LOADED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_SAVED
import farm.rosehearth.compatemon.utils.CompatemonDataKeys.COMPAT_SCALE_SIZE
import farm.rosehearth.compatemon.utils.CompatemonDataKeys.COMPAT_SCALE_WEIGHT
import farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_COMPATEMON
import farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_PEHKUI
import farm.rosehearth.compatemon.utils.pehkui.CompatemonScaleUtils
import virtuoel.pehkui.api.ScaleTypes

object CompatemonKotlin {

    fun initialize() {

        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("Initializing Compatemon")
        LOGGER.info("----------------------------------------------------------------------------------")


        // ---------------------------------------------------------
        // Event Subscriptions
        // ---------------------------------------------------------

        POKEMON_NBT_SAVED.subscribe{event ->

        }
        POKEMON_ENTITY_SAVE.subscribe{ event ->

        }
        POKEMON_NBT_LOADED.subscribe{event ->

        }
        POKEMON_ENTITY_LOAD.subscribe{ event ->
            LOGGER.debug("--------------------------------------------------------------")
            LOGGER.debug("In POKEMON_ENTITY_LOAD")
           // LOGGER.debug("--------------------------------------------------------------")
           // LOGGER.debug(event.pokemonEntity.pokemon.showdownId())
           // LOGGER.debug("")
           // LOGGER.debug("Keys inside the event")
            //event.nbt.allKeys.forEach{
            //    LOGGER.debug("    $it")
           //     LOGGER.debug("        " + event.nbt.get(it).toString())
           // }

            var sizeScale:Float = CompatemonScaleUtils.getScale(event.pokemonEntity.pokemon,"$MOD_ID_PEHKUI:$COMPAT_SCALE_SIZE")
            var weightScale:Float = CompatemonScaleUtils.getScale(event.pokemonEntity.pokemon,"$MOD_ID_COMPATEMON:$COMPAT_SCALE_WEIGHT")

            CompatemonScaleUtils.setScale(event.pokemonEntity, ScaleTypes.BASE,"$MOD_ID_PEHKUI:$COMPAT_SCALE_SIZE", sizeScale)
            LOGGER.debug("Scaled the size of " + event.pokemonEntity.pokemon.showdownId() + " to $sizeScale")
        }

        POKEMON_SENT_PRE.subscribe{ event ->
           // var baseScale = ScaleUtils.getTypedScale(event.pokemon.persistentData,ScaleTypes.BASE,1.0f)
            //LOGGER.info("Pokemon has been sent out. Scale is possibly $baseScale")
        }
        POKEMON_CAPTURED.subscribe {event ->
            //event.pokemon.saveToNBT()
        }
        POKEMON_JSON_SAVED.subscribe{event ->
            LOGGER.debug("Pokemon JSON has been saved.")
        }
        POKEMON_JSON_LOADED.subscribe{event ->
            LOGGER.debug("Pokemon JSON has been loaded.")
        }
    }


    }