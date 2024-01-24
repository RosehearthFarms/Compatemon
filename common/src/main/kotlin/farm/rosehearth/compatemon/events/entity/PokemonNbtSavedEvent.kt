package farm.rosehearth.compatemon.events.entity

import com.cobblemon.mod.common.api.events.Cancelable
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.nbt.CompoundTag

data class PokemonNbtSavedEvent(val pokemon: PokemonEntity, val nbt: CompoundTag): Cancelable()
