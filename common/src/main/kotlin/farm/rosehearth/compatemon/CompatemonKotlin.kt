package farm.rosehearth.compatemon


import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_CAPTURED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_LOAD
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_SAVE
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_SENT_PRE
import farm.rosehearth.compatemon.Compatemon.LOGGER
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_LOADED
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_SAVED
import farm.rosehearth.compatemon.utils.pehkui.CompatemonScaleUtils
import net.minecraft.nbt.CompoundTag
import virtuoel.pehkui.api.ScaleTypes
import virtuoel.pehkui.util.ScaleUtils

object CompatemonKotlin {

    fun initialize() {

        LOGGER.info("----------------------------------------------------------------------------------")
        LOGGER.info("Initializing Compatemon")
        LOGGER.info("----------------------------------------------------------------------------------")


        // ---------------------------------------------------------
        // Event Subscriptions
        // ---------------------------------------------------------
        POKEMON_ENTITY_SAVE.subscribe{ event ->
            //var baseScale = ScaleUtils.getTypedScale(event.pokemonEntity,ScaleTypes.BASE,1.0f)
            //LOGGER.info("Yay modify the scaling to $baseScale")
            //var compatScaleNBT = CompoundTag()
            //compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            //compatScaleNBT.putFloat("peh_weightScale", baseScale)
            //var compatNBT = CompoundTag()
           // compatNBT.put("Compatemon",compatScaleNBT)
           // var toMergeNBT = CompoundTag()
           // toMergeNBT.put("Pokemon",compatNBT)
            //event.nbt.merge(toMergeNBT)
            //event.nbt.put("Pokemon",compatNBT)

        }
        POKEMON_ENTITY_LOAD.subscribe{ event ->

            var n = event.nbt
            var baseScale = n.getFloat("peh_sizeScale");
            CompatemonScaleUtils.setScale(event.pokemonEntity,ScaleTypes.BASE,baseScale)
            LOGGER.info("POKEMON_ENTITY_LOAD let out the pokemon and scale to $baseScale")
        }
        POKEMON_SENT_PRE.subscribe{ event ->
        }

        POKEMON_CAPTURED.subscribe {event ->
            //event.pokemon.saveToNBT()
        }
        POKEMON_NBT_SAVED.subscribe{event ->
            var baseScale = ScaleUtils.getTypedScale(event.pokemon.entity,ScaleTypes.BASE,1.0f)
            LOGGER.info("POKEMON_NBT_SAVED modify the scaling to $baseScale")
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)
           // var toMergeNBT = CompoundTag()
           // toMergeNBT.put("Pokemon",compatNBT)
            event.nbt.merge(compatNBT)

        }
        POKEMON_NBT_LOADED.subscribe{event ->
            var n = event.nbt
            var baseScale = n.getFloat("peh_sizeScale");
            event.pokemon.entity?.let { CompatemonScaleUtils.setScale(it,ScaleTypes.BASE,baseScale) }
            LOGGER.info("POKEMON_NBT_LOADED let out the pokemon and scale to $baseScale")

        }
    }


    }