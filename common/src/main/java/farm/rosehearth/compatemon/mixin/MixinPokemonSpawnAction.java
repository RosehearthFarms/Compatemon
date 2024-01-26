package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import farm.rosehearth.compatemon.utils.pehkui.CompatemonScaleUtils;

import farm.rosehearth.compatemon.Compatemon;

import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

@Mixin(value = PokemonSpawnAction.class, remap = false)
abstract class MixinPokemonSpawnAction extends SpawnAction<PokemonEntity> {


    public MixinPokemonSpawnAction(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {

        super(ctx, detail);
    }

    @Inject(method = "createEntity()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", at = @At("RETURN"))
    private void adjustEntitySizing(CallbackInfoReturnable<PokemonEntity> cir) {
        // Get the modifiers based on config
        float size_scale = CompatemonScaleUtils.Companion.getNewScale(MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
        float weight_scale = CompatemonScaleUtils.Companion.getNewScale(MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);

        Compatemon.LOGGER.debug("In the injection for createEntity()");
        // Actually set the scale of the spawning pokemonEntity. Will eventually add a new scale type for weight.
        CompatemonScaleUtils.Companion.setScale(cir.getReturnValue(), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);


    }

}
