package farm.rosehearth.compatemon.mixin.client;

import farm.rosehearth.compatemon.Compatemon;
import com.cobblemon.mod.common.client.render.pokemon.PokemonRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EntityDimensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.COMPAT_SCALE_SIZE;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_COMPATEMON;

@Mixin(PokemonRenderer.class)
abstract class MixinPokemonRenderer {
//	@Mutable
//	@Shadow(remap=false)
//	private EntityDimensions _hitbox;
	@Unique
	private float compatemon$newScale = 1.0f;
	
	@Inject(at=@At("RETURN"),remap=false,method="scale(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V")
	public void compatemon$onScale(PokemonEntity pEntity, PoseStack pMatrixStack, float pPartialTickTime, CallbackInfo cir){
	
	}
	@Inject(at=@At(value="INVOKE", target="Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V")
	,remap=false, locals = LocalCapture.CAPTURE_FAILSOFT
	,method="scale(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V")
	private void compatemon$onScale(PokemonEntity pEntity, PoseStack pMatrixStack, float pPartialTickTime, CallbackInfo cir, float scale) {
		if(pEntity.getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(COMPAT_SCALE_SIZE))
			compatemon$newScale = pEntity.getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).getFloat(COMPAT_SCALE_SIZE);
		
	}
	
	@ModifyVariable(at=@At("STORE"), ordinal = 0
			,remap=false//, locals = LocalCapture.CAPTURE_FAILSOFT
			,method="scale(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V")
	private float compatemon$modifyScale(float scale) {
		return scale * compatemon$newScale;
	}
}
