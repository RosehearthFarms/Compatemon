package farm.rosehearth.compatemon.api.cobblemon.properties


import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.pokemon.Pokemon

open class FloatProperty (val key: String,
                          val value: Float,
                          private val pokemonApplicator: (pokemon: Pokemon, value: Float) -> Unit,
                          private val pokemonMatcher: (pokemon: Pokemon, value: Float) -> Boolean,
) : CustomPokemonProperty {

    override fun asString() = "${this.key}=${this.value}"

    override fun apply(pokemon: Pokemon) {
        this.pokemonApplicator.invoke(pokemon, this.value)
    }

    override fun matches(pokemon: Pokemon) = this.pokemonMatcher.invoke(pokemon, this.value)

}
