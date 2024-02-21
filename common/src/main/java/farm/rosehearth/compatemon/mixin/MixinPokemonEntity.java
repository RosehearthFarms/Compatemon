package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

/**
 * Mixin to the Pokemon Entity Class. Using for Pehkui sizing.
 */
@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends Entity
//implements IScalablePokemonEntity
{

    @Shadow(remap=false)
    private Pokemon pokemon;
    
    @Unique
    private float compatemon$sizeScale = 1.0f;
    
    MixinPokemonEntity(EntityType<?> entityType, Level level){
        super(entityType, level);
    }
    
    @Inject(method = "<init>"
            ,at = @At("RETURN")
            ,remap = false)
    public void compatemon$onInit(Level world, Pokemon pokemon, EntityType entityType, CallbackInfo cir){
//        ((IScalableForm)(Object)(pokemon.getForm())).compatemon$setPokemonEntity((PokemonEntity)(Object)this);
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            compatemon$sizeScale = CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), ScaleTypes.BASE, COMPAT_SCALE_SIZE, PehkuiConfig.size_scale, 0.0f);
            pokemon.getForm().getHitbox().scale(compatemon$sizeScale);
            //pokemon.getForm().getHitbox().height = 1;
        }
    }
    
    
//    @Inject(at = @At("RETURN")
//            ,method="mobInteract"
//            ,remap=false)
//    public void compatemon$injectInteractMobReturn(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
//        // Placeholder bc I'm SURE we'll have something to do here
//    }
//
    
    @Inject(at = @At("TAIL")
            ,method="setCustomName"
            ,remap=false)
    public void compatemon$injectSetCustomNameReturn(Component t, CallbackInfo cir){
        if(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_RARITY_COLOR)){
            pokemon.setNickname(t.copy().withStyle(Style.EMPTY.withColor(TextColor.parseColor(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR)))));
        }
    }
    
    
    @Inject(at = @At("RETURN")
            ,remap=false
            ,method="getDimensions", cancellable = true)
    public void compatemon$overrideDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir){
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            if(compatemon$sizeScale != 1.0f)
                cir.setReturnValue(cir.getReturnValue().scale(compatemon$sizeScale));
        }
    }
    
    /**
     * These were implemented by the interface we're no longer using. Here as reference.
     */
//    @Override
//    public float compatemon$getSizeScale(){
//        return compatemon$sizeScale;
//    }
//
//    @Override
//    public void compatemon$setSizeScale(float sizeScale){
//        compatemon$sizeScale = sizeScale;
//    }
}
