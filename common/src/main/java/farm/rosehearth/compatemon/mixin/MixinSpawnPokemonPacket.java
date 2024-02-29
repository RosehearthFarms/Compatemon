package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.net.messages.client.spawn.SpawnPokemonPacket;
import farm.rosehearth.compatemon.Compatemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import farm.rosehearth.compatemon.modules.pehkui.IScalablePokemonEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnPokemonPacket.class)
abstract class MixinSpawnPokemonPacket {

	@Inject(at=@At("RETURN"), remap=false, method="applyData(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V")
	public void compatemon$onApplyDataReturn(PokemonEntity entity, CallbackInfo ci){
		Compatemon.LOGGER.debug(entity.getPokemon().getPersistentData().toString());
		Compatemon.LOGGER.debug("Scale in Entity: {}", ((IScalablePokemonEntity)(Object)entity).compatemon$getSizeScale());
	}
	
}
