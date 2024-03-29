package farm.rosehearth.compatemon


import com.cobblemon.mod.common.api.events.CobblemonEvents.LOOT_DROPPED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_CAPTURED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_LOAD
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_SAVE
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_NICKNAMED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_SENT_PRE
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import farm.rosehearth.compatemon.Compatemon.LOGGER
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_JSON_LOADED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_JSON_SAVED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_LOADED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_SAVED
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils.Companion.setScale
import farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_BOSS
import farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_RARITY
import farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_RARITY_COLOR
import farm.rosehearth.compatemon.util.CompatemonDataKeys.COMPAT_SCALE_SIZE
import farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_APOTHEOSIS
import farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_COMPATEMON
import farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_PEHKUI
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor

object CompatemonKotlin {

    fun initialize() {

        // Occurs when the Pokemon is saved to the world or party
        POKEMON_ENTITY_SAVE.subscribe{ event ->
            if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
                var isBoss = event.pokemonEntity.pokemon.persistentData.getCompound(MOD_ID_COMPATEMON).contains(APOTH_BOSS);
                if(isBoss){
                    var rarityKey = event.pokemonEntity.pokemon.persistentData.getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR)
                    event.pokemonEntity.pokemon.nickname = event.pokemonEntity.pokemon.nickname?.withStyle(Style.EMPTY.withColor(TextColor.parseColor(rarityKey)))
                }
            }
        }

        // Occurs when the Pokemon's NBT data is loaded from the world. We need to make sure that the Entity's scale
        // is set to what it was when the world is loaded
        POKEMON_ENTITY_LOAD.subscribe{ event ->
            if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)) {
                setScale(event.pokemonEntity, COMPAT_SCALE_SIZE, PehkuiConfig.size_scale, 0.0f)
            }
        }

//        POKEMON_NBT_SAVED.subscribe{event ->
//        }
//        // Occurs when the Pokemon's NBT data is loaded from the world. Injected into PokemonEntity's method.
//        POKEMON_NBT_LOADED.subscribe{event ->
//        }
//        // Occurs when the Pokemon is Sent Out of its pokeball
//        POKEMON_SENT_PRE.subscribe{ event ->
//        }
//
//        // Occurs when the Pokemon is Captured
//        POKEMON_CAPTURED.subscribe {event ->
//        }
//
//        POKEMON_JSON_SAVED.subscribe{event ->
//            //LOGGER.debug("Pokemon JSON has been saved.")
//        }
//
//        POKEMON_JSON_LOADED.subscribe{event ->
//            LOGGER.debug("Pokemon JSON has been loaded.")
//        }
//
//        LOOT_DROPPED.subscribe{event ->
//        }
//
//        // To update the nickname color whenever the name changes
//        POKEMON_NICKNAMED.subscribe{event ->
//            LOGGER.debug(event.pokemon.nickname.toString())
//        }



    }


    }