package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;

import farm.rosehearth.compatemon.Compatemon;

import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

/**
 * Mixin into PokemonSpawnAction of Cobblemon. Should set the scales for the entity of the configured scale types at spawn time
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
