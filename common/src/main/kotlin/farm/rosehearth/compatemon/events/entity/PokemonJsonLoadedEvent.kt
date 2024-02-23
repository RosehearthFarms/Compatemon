package farm.rosehearth.compatemon.events.entity

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import com.google.gson.JsonObject

/**
 * Fired when a PokemonEntity's JSON is Loaded
 */
data class PokemonJsonLoadedEvent(val pokemon: Pokemon, val json: JsonObject): Cancelable()
