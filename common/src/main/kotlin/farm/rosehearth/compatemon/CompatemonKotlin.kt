package farm.rosehearth.compatemon

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.util.splitMap
import farm.rosehearth.compatemon.api.cobblemon.properties.SizeScaleProperty
import org.apache.logging.log4j.LogManager

object CompatemonKotlin {

    fun initialize() {
        val LOGGER = LogManager.getLogger()


        var s = "\"1.25\""

        var x = s.splitMap(" ", "=")
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")
        x.forEach { LOGGER.info(it.first + " ::: " + it.second) }
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("----------------------------------------------------------------------------------")

        CustomPokemonProperty.register(SizeScaleProperty)

        CustomPokemonProperty.properties.forEach { LOGGER.info(it.fromString(s)?.asString()) }
    }


    }