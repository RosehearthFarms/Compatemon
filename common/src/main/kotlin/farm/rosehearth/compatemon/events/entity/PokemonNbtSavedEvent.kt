package farm.rosehearth.compatemon.events.entity

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.nbt.CompoundTag

/**
 * Fired when a Pokemon Entity's NBT data is saved to the world or storage
 */
data class PokemonNbtSavedEvent(val pokemon: Pokemon, val nbt: CompoundTag): Cancelable()
