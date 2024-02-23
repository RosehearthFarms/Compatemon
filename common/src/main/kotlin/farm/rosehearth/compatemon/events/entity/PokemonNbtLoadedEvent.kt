package farm.rosehearth.compatemon.events.entity

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.nbt.CompoundTag

/**
 * Fired when a PokemonEntity's NBT data is loaded from the world or storage
 */
data class PokemonNbtLoadedEvent(val pokemon: Pokemon, val nbt: CompoundTag): Cancelable()
