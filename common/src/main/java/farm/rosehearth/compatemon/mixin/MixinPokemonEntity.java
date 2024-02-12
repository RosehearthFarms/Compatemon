package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

/**
 *
 */
@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends ShoulderRidingEntity {

    @Shadow(remap=false)
    private Pokemon pokemon;
    
    /**
     *
     * @param entityType
     * @param level
     */
    MixinPokemonEntity(EntityType<?> entityType, Level level){
        super((EntityType<? extends ShoulderRidingEntity>) entityType, level);
    }
    
    /**
     *
     * @param world
     * @param pokemon
     * @param entityType
     * @param cir
     */
    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void onInit(Level world, Pokemon pokemon, EntityType entityType, CallbackInfo cir){
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            float size_scale = CompatemonScaleUtils.Companion.getScale(pokemon, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
            float weight_scale = CompatemonScaleUtils.Companion.getScale(pokemon, MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
            Compatemon.LOGGER.debug("Woo we've created a pokemon and set its scale to " + size_scale);
            CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
        }
    }
    
    /**
     *
     * @param player
     * @param hand
     * @param cir
     */
    @Inject(at = @At("RETURN"), method="mobInteract", remap=false)
    public void compatemon$injectInteractMobReturn(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        // Placeholder bc I'm SURE we'll have something todo here
    }
    
    /**
     *
     * @param t
     * @param cir
     */
    @Inject(at = @At("TAIL"), method="setCustomName", remap=false)
    public void compatemon$injectSetCustomNameReturn(Component t, CallbackInfo cir){
        Compatemon.LOGGER.debug("I just really needed to say...");
        
        pokemon.setNickname(t.copy().withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
        Compatemon.LOGGER.debug("I Love You!");
    }
}
