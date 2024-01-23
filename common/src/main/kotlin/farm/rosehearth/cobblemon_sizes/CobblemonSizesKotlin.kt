package farm.rosehearth.cobblemon_sizes

import com.cobblemon.mod.common.advancement.CobblemonCriteria
import com.cobblemon.mod.common.advancement.criterion.EvolvePokemonContext
import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.SeasonResolver
import com.cobblemon.mod.common.api.data.DataProvider
import com.cobblemon.mod.common.api.drop.CommandDropEntry
import com.cobblemon.mod.common.api.drop.DropEntry
import com.cobblemon.mod.common.api.drop.ItemDropEntry
import com.cobblemon.mod.common.api.events.CobblemonEvents.BATTLE_VICTORY
import com.cobblemon.mod.common.api.events.CobblemonEvents.DATA_SYNCHRONIZED
import com.cobblemon.mod.common.api.events.CobblemonEvents.EVOLUTION_COMPLETE
import com.cobblemon.mod.common.api.events.CobblemonEvents.LEVEL_UP_EVENT
import com.cobblemon.mod.common.api.events.CobblemonEvents.POKEMON_CAPTURED
import com.cobblemon.mod.common.api.events.CobblemonEvents.TRADE_COMPLETED
import com.cobblemon.mod.common.api.net.serializers.PoseTypeDataSerializer
import com.cobblemon.mod.common.api.net.serializers.StringSetDataSerializer
import com.cobblemon.mod.common.api.net.serializers.Vec3DataSerializer
import com.cobblemon.mod.common.api.permission.PermissionValidator
import com.cobblemon.mod.common.api.pokeball.catching.calculators.CaptureCalculator
import com.cobblemon.mod.common.api.pokeball.catching.calculators.CaptureCalculators
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.pokemon.effect.ShoulderEffectRegistry
import com.cobblemon.mod.common.api.pokemon.experience.ExperienceCalculator
import com.cobblemon.mod.common.api.pokemon.experience.ExperienceGroups
import com.cobblemon.mod.common.api.pokemon.experience.StandardExperienceCalculator
import com.cobblemon.mod.common.api.pokemon.feature.ChoiceSpeciesFeatureProvider
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeatures
import com.cobblemon.mod.common.api.pokemon.helditem.HeldItemProvider
import com.cobblemon.mod.common.api.pokemon.stats.EvCalculator
import com.cobblemon.mod.common.api.pokemon.stats.Generation8EvCalculator
import com.cobblemon.mod.common.api.pokemon.stats.StatProvider
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.api.spawning.BestSpawner
import com.cobblemon.mod.common.api.spawning.CobblemonSpawningProspector
import com.cobblemon.mod.common.api.spawning.context.AreaContextResolver
import com.cobblemon.mod.common.api.spawning.prospecting.SpawningProspector
import com.cobblemon.mod.common.api.starter.StarterHandler
import com.cobblemon.mod.common.api.storage.PokemonStoreManager
import com.cobblemon.mod.common.api.storage.adapter.conversions.ReforgedConversion
import com.cobblemon.mod.common.api.storage.adapter.database.MongoDBStoreAdapter
import com.cobblemon.mod.common.api.storage.adapter.flatfile.FileStoreAdapter
import com.cobblemon.mod.common.api.storage.adapter.flatfile.JSONStoreAdapter
import com.cobblemon.mod.common.api.storage.adapter.flatfile.NBTStoreAdapter
import com.cobblemon.mod.common.api.tags.CobblemonEntityTypeTags
import com.cobblemon.mod.common.battles.BagItems
import com.cobblemon.mod.common.battles.BattleFormat
import com.cobblemon.mod.common.battles.BattleRegistry
import com.cobblemon.mod.common.battles.BattleSide
import com.cobblemon.mod.common.battles.ShowdownThread
import com.cobblemon.mod.common.battles.actor.PokemonBattleActor
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.config.LastChangedVersion
import com.cobblemon.mod.common.config.constraint.IntConstraint
import com.cobblemon.mod.common.config.starter.StarterConfig
import com.cobblemon.mod.common.data.CobblemonDataProvider
import com.cobblemon.mod.common.events.AdvancementHandler
import com.cobblemon.mod.common.events.ServerTickHandler
import com.cobblemon.mod.common.item.PokeBallItem
import com.cobblemon.mod.common.net.messages.client.settings.ServerSettingsPacket
import com.cobblemon.mod.common.permission.LaxPermissionValidator
import com.cobblemon.mod.common.platform.events.PlatformEvents
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.aspects.GENDER_ASPECT
import com.cobblemon.mod.common.pokemon.aspects.SHINY_ASPECT
import com.cobblemon.mod.common.pokemon.evolution.variants.BlockClickEvolution
import com.cobblemon.mod.common.pokemon.feature.TagSeasonResolver
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager
import com.cobblemon.mod.common.pokemon.properties.HiddenAbilityPropertyType
import com.cobblemon.mod.common.pokemon.properties.UncatchableProperty
import com.cobblemon.mod.common.pokemon.properties.tags.PokemonFlagProperty
import com.cobblemon.mod.common.pokemon.stat.CobblemonStatProvider
import com.cobblemon.mod.common.starter.CobblemonStarterHandler
import com.cobblemon.mod.common.trade.TradeManager
import com.cobblemon.mod.common.util.DataKeys
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.util.UUID
import kotlin.properties.Delegates
import org.apache.logging.log4j.LogManager
import farm.rosehearth.cobblemon_sizes.api.cobblemon.properties.*
object CobblemonSizesKotlin {

    fun initialize() {
        CustomPokemonProperty.register(SizeScaleProperty)


    }


    }