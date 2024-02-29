package farm.rosehearth.compatemon.modules.compatemon

import com.cobblemon.mod.common.entity.EntityProperty
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData

interface IPokemonEntityExtensions {

	abstract fun getPersistentData(): EntityProperty<CompoundTag>;
	abstract fun setPersistentData(p:EntityProperty<CompoundTag>);

	companion object{
		@JvmStatic val PERSISTENT_DATA = SynchedEntityData.defineId(PokemonEntity::class.java, EntityDataSerializers.COMPOUND_TAG)
	}
}