package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import farm.rosehearth.compatemon.utils.CustomPropertyAdder;


import com.cobblemon.mod.common.util.*;
import farm.rosehearth.compatemon.Compatemon;

import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = PokemonSpawnAction.class, remap = false)
abstract class MixinPokemonSpawnAction extends SpawnAction<PokemonEntity> {

    @Shadow public abstract void setProps(@NotNull PokemonProperties pokemonProperties);

    public MixinPokemonSpawnAction(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {

        super(ctx, detail);
    }

    @Inject(method = "createEntity()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", at = @At("RETURN"))
    private void adjustEntitySizing(CallbackInfoReturnable<PokemonEntity> cir) {
       // CustomPokemonProperty.Companion.getProperties();
        float size_scale = Compatemon.getSizeModifier();
        float weight_scale = Compatemon.getWeightModifier();
        Compatemon.LOGGER.debug("Adjusting size of " + cir.getReturnValue().getPokemon().getDisplayName() + " to " + size_scale);
        ScaleUtils.setScaleOnSpawn(cir.getReturnValue(),size_scale);
       // props.customProperties.add(PokemonProperties.Companion.parse("CS_SizeScale=" + size_scale," ","="))
       // var p = props.getCustomProperties();
        String s = "\"" + size_scale + "\"";
        Compatemon.LOGGER.debug(s);
        CustomPropertyAdder.Companion.addPropertyToPokemon(s,cir.getReturnValue());
       // var p = PokemonProperties.Companion.parse(s," ","=").getCustomProperties();
     //  Compatemon.LOGGER.debug("Got here at least - tried to add custom property : " + p.size());
      // cir.getReturnValue().getPokemon().getCustomProperties().addAll(p);

        //props.setCustomProperties(p);
       // props.setCustomProperties(PokemonProperties.Companion.parse("CS_SizeScale=" + size_scale," ","="));

    }

    @Shadow
    private PokemonProperties props;

}
