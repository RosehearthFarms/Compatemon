package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.IScalableFormData;
import farm.rosehearth.compatemon.modules.pehkui.IScalablePokemonEntity;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

/**
 * Mixin to the Pokemon Entity Class. Using for Pehkui sizing.
 */
@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends Entity
implements IScalablePokemonEntity
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
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            compatemon$sizeScale = CompatemonScaleUtils.Companion.getScale(((PokemonEntity) ((Object) this)), COMPAT_SCALE_SIZE);;
            Compatemon.LOGGER.debug("Size Scale is being generated in the init: {}", compatemon$sizeScale);
        }
    }
    
    
//    NEED TO INJECT INTO THIS
//    @Suppress("SENSELESS_COMPARISON")
//    override fun getActiveEyeHeight(pose: EntityPose, dimensions: EntityDimensions): Float {
//    // DO NOT REMOVE
//    // LivingEntity#getActiveEyeHeight is called in the constructor of Entity
//    // Pokémon param is not available yet
//    if (this.pokemon == null) {
//        return super.getActiveEyeHeight(pose, dimensions)
//    }
//    return this.pokemon.form.eyeHeight(this)
//}

//
//    override fun playAmbientSound() {
//    if (!this.isSilent || this.busyLocks.filterIsInstance<EmptyPokeBallEntity>().isEmpty()) {
//        val sound = Identifier(this.pokemon.species.resourceIdentifier.namespace, "pokemon.${this.pokemon.showdownId()}.ambient")
//        // ToDo distance to travel is currently hardcoded to default we can maybe find a way to work around this down the line
//        UnvalidatedPlaySoundS2CPacket(sound, this.soundCategory, this.x, this.y, this.z, this.soundVolume, this.soundPitch)
//                .sendToPlayersAround(this.x, this.y, this.z, 16.0, this.world.registryKey)
//    }
//}




//
//    @Inject(at = @At("RETURN")
//            ,remap=false
//            ,method="getDimensions", cancellable = true)
//    public void compatemon$overrideDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir){
//        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
//            compatemon$sizeScale = CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), COMPAT_SCALE_SIZE, PehkuiConfig.size_scale, 0.0f);
//            if(compatemon$sizeScale != 1.0f){
//                cir.setReturnValue(cir.getReturnValue().scale(compatemon$sizeScale));
//
//                Compatemon.LOGGER.debug("NewScale: " + (compatemon$sizeScale));
//                CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), COMPAT_SCALE_SIZE, PehkuiConfig.size_scale, 0.0f);
//            }
//        }
//    }

//	@Inject(at=@At(value="INVOKE", shift=At.Shift.AFTER,target="Lcom/cobblemon/mod/common/pokemon/Pokemon;getScaleModifier()F")
//	,remap=false, locals = LocalCapture.CAPTURE_FAILSOFT
//	,method="getDimensions")
//	private void compatemon$invokeGetDimensionsScale(@NotNull Pose pose, CallbackInfoReturnable<EntityDimensions> cir, float scale) {
//        scale = scale * 5.0f;
//	}

//
//	@ModifyVariable(at = @At("LOAD"), ordinal = 0
//			, remap = false//, locals = LocalCapture.CAPTURE_FAILSOFT
//			, method = "getDimensions(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;")
//	private float compatemon$onStoreScaleModifyScale(float scale) {
//		Compatemon.LOGGER.debug("Scale: " + scale);
//		Compatemon.LOGGER.debug("NewScale: " + (scale * compatemon$sizeScale));
//		return scale * compatemon$sizeScale;
//	}



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
    
    @Override
    public float compatemon$getSizeScale(){
        return compatemon$sizeScale;
    }
    
    @Override
    public void compatemon$setSizeScale(float sizeScale){
        compatemon$sizeScale = sizeScale;
    }
}