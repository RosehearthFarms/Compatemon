package farm.rosehearth.compatemon

import com.cobblemon.mod.common.api.events.CobblemonEvents
import farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_APOTHEOSIS
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn

object CompatemonForgeKotlin {
	fun postCommonInit(){

//		Compatemon.LOGGER.debug("----------------------------------------------------------------------------------")
//		Compatemon.LOGGER.debug("Forge Specific Events")
//		Compatemon.LOGGER.debug("----------------------------------------------------------------------------------")



		//CobblemonEvents.LOOT_DROPPED.subscribe{ event ->
		//	if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)) {
				//val newEvent = LivingDropsEvent( event.entity, event.entity.lastDamageSource, null, event.player., boolean recentlyHit)
				//MinecraftForge.EVENT_BUS.post(newEvent)
		//	}
		//}

	}
}