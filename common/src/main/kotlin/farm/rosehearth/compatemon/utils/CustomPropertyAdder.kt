package farm.rosehearth.compatemon.utils

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.util.splitMap
import farm.rosehearth.compatemon.api.cobblemon.properties.SizeScaleProperty
import org.apache.logging.log4j.LogManager

open class CustomPropertyAdder {

    companion object {

        val LOGGER = LogManager.getLogger()
        fun addPropertyToPokemon(property:String, pokemon: PokemonEntity)
        {

           // var s = "\"$value\""

           // var x = s.splitMap(" ", "=")
           // x.forEach { (it.first + " ::: " + it.second) }

            //CustomPokemonProperty.properties.forEach { (it.fromString(s)?.asString()) }
            val keyPairs = property.splitMap(" ", "=")
            pokemon.pokemon.customProperties.addAll( CustomPokemonProperty.properties.mapNotNull { property ->
                val matchedKeyPair = keyPairs.find { it.first.lowercase() in property.keys }
                if (matchedKeyPair == null) {
                    if (!property.needsKey) {
                        var savedProperty: CustomPokemonProperty? = null
                        val keyPair = keyPairs.find { keyPair ->
                            savedProperty = property.fromString(keyPair.second)
                            return@find savedProperty != null
                        }
                        if (keyPair != null) {
                            keyPairs.remove(keyPair)
                        }
                        return@mapNotNull savedProperty
                    } else {
                        return@mapNotNull null
                    }
                } else {
                    keyPairs.remove(matchedKeyPair)
                    return@mapNotNull property.fromString(matchedKeyPair.second)
                }
            }.toMutableList()
            )
            LOGGER.info("I AM HERE!")
            pokemon.pokemon.customProperties.forEach {LOGGER.info(it.asString()) }
        }
    }
}