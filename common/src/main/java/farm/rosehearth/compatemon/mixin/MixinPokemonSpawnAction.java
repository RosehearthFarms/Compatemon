package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin into PokemonSpawnAction of Cobblemon. Should set the scales for the entity of the configured scale types at spawn time
 * This is the common specific Mixin - Was previously using but now is just here in case a new mod compat needs to use it too
 */
@Mixin(value = PokemonSpawnAction.class, remap = false)
abstract class MixinPokemonSpawnAction extends SpawnAction<PokemonEntity> {


    public MixinPokemonSpawnAction(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {
        super(ctx, detail);
    }

//    @Inject(method = "createEntity()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", at = @At("RETURN"))
//    private void compatemon$createPokemonEntityReturn(CallbackInfoReturnable<PokemonEntity> cir) {
//
//    }

}
