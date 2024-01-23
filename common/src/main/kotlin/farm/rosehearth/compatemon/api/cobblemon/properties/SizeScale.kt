package farm.rosehearth.compatemon.api.cobblemon.properties


import com.cobblemon.mod.common.pokemon.properties.StringProperty
import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType

object SizeScaleProperty : CustomPokemonPropertyType<StringProperty> {
    override val keys = setOf("CS_SizeScale")
    override val needsKey = true

    override fun fromString(value: String?) = if (value == null) null else StringProperty(keys.first(), value, { _, _ -> }, { pokemon, underlyingValue -> pokemon.hasLabels(underlyingValue) })




    override fun examples() = setOf("1.0", "0.75", "2.0")

}
