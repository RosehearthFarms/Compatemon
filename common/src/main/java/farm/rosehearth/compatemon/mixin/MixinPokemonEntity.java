package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.utils.pehkui.CompatemonScaleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends Entity {



    MixinPokemonEntity(EntityType<?> entityType, Level level){
        super(entityType, level);
    }


    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void onInit(Level world, Pokemon pokemon, EntityType entityType, CallbackInfo cir){
       // ((PokemonEntity)((Object)this))
        float size_scale = CompatemonScaleUtils.Companion.getScale(MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
        float weight_scale = CompatemonScaleUtils.Companion.getScale(MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
        Compatemon.LOGGER.debug("In the constructor injection for pokemonEntity");
        CompatemonScaleUtils.Companion.setScale(((PokemonEntity)((Object)this)), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
    }
}
