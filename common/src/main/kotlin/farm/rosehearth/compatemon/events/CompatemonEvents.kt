package farm.rosehearth.compatemon.events

import com.cobblemon.mod.common.api.reactive.CancelableObservable
import com.cobblemon.mod.common.api.reactive.EventObservable
import farm.rosehearth.compatemon.events.entity.PokemonJsonLoadedEvent
import farm.rosehearth.compatemon.events.entity.PokemonJsonSavedEvent
import farm.rosehearth.compatemon.events.entity.PokemonNbtLoadedEvent
import farm.rosehearth.compatemon.events.entity.PokemonNbtSavedEvent

object CompatemonEvents {

    @JvmField
    val POKEMON_NBT_LOADED = EventObservable<PokemonNbtLoadedEvent>()
    @JvmField
    val POKEMON_NBT_SAVED = CancelableObservable<PokemonNbtSavedEvent>()

    @JvmField
    val POKEMON_JSON_LOADED = EventObservable<PokemonJsonLoadedEvent>()
    @JvmField
    val POKEMON_JSON_SAVED = CancelableObservable<PokemonJsonSavedEvent>()

}