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
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils
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
        // Occurs when the Pokemon is saved to the world or party
        POKEMON_ENTITY_SAVE.subscribe{ event ->
        }
        // Occurs when the Pokemon's NBT data is loaded from the world. Injected into PokemonEntity's method.
        POKEMON_NBT_LOADED.subscribe{event ->
        }
        // Occurs when the Pokemon's NBT data is loaded from the world.
        POKEMON_ENTITY_LOAD.subscribe{ event ->
            if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)) {
                var sizeScale: Float = CompatemonScaleUtils.getScale(event.pokemonEntity.pokemon, "$MOD_ID_PEHKUI:$COMPAT_SCALE_SIZE")
                var weightScale: Float = CompatemonScaleUtils.getScale(event.pokemonEntity.pokemon, "$MOD_ID_COMPATEMON:$COMPAT_SCALE_WEIGHT")
                CompatemonScaleUtils.setScale(event.pokemonEntity, ScaleTypes.BASE, "$MOD_ID_PEHKUI:$COMPAT_SCALE_SIZE", sizeScale)
            }
        }
        // Occurs when the Pokemon is Sent Out of its pokeball
        POKEMON_SENT_PRE.subscribe{ event ->
        }
        // Occurs when the Pokemon is Captured
        POKEMON_CAPTURED.subscribe {event ->
        }
        POKEMON_JSON_SAVED.subscribe{event ->
            LOGGER.debug("Pokemon JSON has been saved.")
        }
        POKEMON_JSON_LOADED.subscribe{event ->
            LOGGER.debug("Pokemon JSON has been loaded.")
        }
    }


    }