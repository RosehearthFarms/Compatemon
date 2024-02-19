package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
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

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

/**
 *
 */
@Mixin(PokemonEntity.class)
abstract class MixinPokemonEntity extends Entity {

    @Shadow(remap=false)
    private Pokemon pokemon;
    
    /**
     *
     * @param entityType
     * @param level
     */
    MixinPokemonEntity(EntityType<?> entityType, Level level){
        super(entityType, level);
    }
    
    /**
     *
     * @param world
     * @param pokemon
     * @param entityType
     * @param cir
     */
    @Inject(method = "<init>"
            ,at = @At("RETURN")
            ,remap = false)
    public void compatemon$onInit(Level world, Pokemon pokemon, EntityType entityType, CallbackInfo cir){
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            CompatemonScaleUtils.Companion.setScale(((PokemonEntity) ((Object) this)), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, PehkuiConfig.size_scale, 0.0f);//, size_scale, 0.0f);
        }
    }
    
    /**
     *
     * @param player
     * @param hand
     * @param cir
     */
    @Inject(at = @At("RETURN")
            ,method="mobInteract"
            ,remap=false)
    public void compatemon$injectInteractMobReturn(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        // Placeholder bc I'm SURE we'll have something todo here
    }
    
    /**
     *
     * @param t
     * @param cir
     */
    @Inject(at = @At("TAIL")
            ,method="setCustomName"
            ,remap=false)
    public void compatemon$injectSetCustomNameReturn(Component t, CallbackInfo cir){
        if(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_RARITY_COLOR)){
            pokemon.setNickname(t.copy().withStyle(Style.EMPTY.withColor(TextColor.parseColor(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR)))));
        }
    }
    
    @Inject(at = @At("TAIL")
            ,remap=false
            ,method="getDimensions")
    public void compatemon$overrideDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir){
        if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
            if(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE))
                cir.getReturnValue().scale(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).getFloat(MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE));
        }
    }
}
