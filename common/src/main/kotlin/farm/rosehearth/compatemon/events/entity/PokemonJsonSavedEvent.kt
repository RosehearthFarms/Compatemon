package farm.rosehearth.compatemon.events.entity

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import com.google.gson.JsonObject

/**
 * Fired when a PokemonEntity's JSON data is Saved
 */
data class PokemonJsonSavedEvent(val pokemon: Pokemon, val json: JsonObject): Cancelable()

