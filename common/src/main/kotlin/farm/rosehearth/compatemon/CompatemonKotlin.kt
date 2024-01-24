package farm.rosehearth.compatemon


import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_CAPTURED
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_LOAD
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_ENTITY_SAVE
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_SENT_PRE
import farm.rosehearth.compatemon.Compatemon.LOGGER
import farm.rosehearth.compatemon.events.CompatemonEvents.POKEMON_NBT_SAVED
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
            var baseScale = ScaleUtils.getTypedScale(event.pokemonEntity,ScaleTypes.BASE,1.0f)
            LOGGER.info("Yay modify the scaling to $baseScale")
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)
            var toMergeNBT = CompoundTag()
            toMergeNBT.put("Pokemon",compatNBT)
            event.nbt.merge(toMergeNBT)
            //event.nbt.put("Pokemon",compatNBT)

        }
        POKEMON_ENTITY_LOAD.subscribe{ event ->
            //var c :CompoundTag = event.nbt.get("Pokemon")
            var x = CompoundTag()
            var baseScale = 1.0f
            LOGGER.info("Yay modify the scaling to $baseScale")
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)
            var toMergeNBT = CompoundTag()
            toMergeNBT.put("Pokemon",compatNBT)
            event.nbt.merge(toMergeNBT)
            //event.nbt.put("Pokemon",compatNBT)

        }
        POKEMON_SENT_PRE.subscribe{ event ->
            //var baseScale = ScaleUtils.getTypedScale(event.pokemon.entity,ScaleTypes.BASE,1.0f)
            //event.pokemon.entity.nb
            //CompatemonScaleUtils.setScale(event.pokemon.entity, ScaleTypes.BASE, baseScale)
        }

        POKEMON_CAPTURED.subscribe {event ->
            var baseScale = ScaleUtils.getTypedScale(event.pokemon.entity,ScaleTypes.BASE,1.0f)
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)
            event.pokemon.saveToNBT(compatNBT)
        }
        POKEMON_NBT_SAVED.subscribe{event ->
            var baseScale = ScaleUtils.getTypedScale(event.pokemon,ScaleTypes.BASE,1.0f)
            LOGGER.info("Yay modify the scaling to $baseScale")
            var compatScaleNBT = CompoundTag()
            compatScaleNBT.putFloat("peh_sizeScale", baseScale)
            compatScaleNBT.putFloat("peh_weightScale", baseScale)
            var compatNBT = CompoundTag()
            compatNBT.put("Compatemon",compatScaleNBT)
            var toMergeNBT = CompoundTag()
            toMergeNBT.put("Pokemon",compatNBT)
            event.nbt.merge(toMergeNBT)

        }
    }


    }