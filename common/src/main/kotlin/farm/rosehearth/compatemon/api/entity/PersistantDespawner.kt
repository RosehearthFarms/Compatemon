package farm.rosehearth.compatemon.api.entity

import com.cobblemon.mod.common.api.entity.Despawner
import net.minecraft.world.entity.Entity

/**
 * PersistantDespawner extends the Despawner class of Cobblemon.
 * The Only purpose is to prevent apotheosis pokemon bosses from despawning
 */
class PersistantDespawner<T:Entity>
	:Despawner<T>{
	override fun beginTracking(entity: T) {

	}

	override fun shouldDespawn(entity: T): Boolean {
		return false
	}
}