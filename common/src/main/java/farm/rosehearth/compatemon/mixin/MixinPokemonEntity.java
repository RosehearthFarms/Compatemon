package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends Entity {

    @Shadow(remap=false)
    private Pokemon pokemon;

    MixinPokemonEntity(EntityType<?> entityType, Level level){
        super(entityType, level);
    }


    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void onInit(Level world, Pokemon pokemon, EntityType entityType, CallbackInfo cir){
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            float size_scale = CompatemonScaleUtils.Companion.getScale(pokemon, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
            float weight_scale = CompatemonScaleUtils.Companion.getScale(pokemon, MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
            CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
        }
    }
    
}
