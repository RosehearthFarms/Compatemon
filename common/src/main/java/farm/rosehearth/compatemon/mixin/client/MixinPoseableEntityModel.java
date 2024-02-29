package farm.rosehearth.compatemon.mixin.client;

import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityModel;
import com.cobblemon.mod.common.client.render.models.blockbench.PoseableEntityState;
import com.mojang.blaze3d.vertex.PoseStack;
import farm.rosehearth.compatemon.Compatemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PoseableEntityModel.class)
abstract class MixinPoseableEntityModel {
	
	@Inject(at=@At(value="INVOKE", target="Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V",shift=At.Shift.BEFORE)
			, remap=false, locals = LocalCapture.CAPTURE_FAILHARD,
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift=At.Shift.AFTER),
					to = @At(value = "INVOKE", target = "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;isForLivingEntityRenderer()Z")
			)
			, method="updateLocators")
	public void compatemon$inUpdateLocatorsOnInvokeScaleModifiers(PoseableEntityState<?> state, CallbackInfo ci, Entity entity, PoseStack matrixStack, float scale){
		Compatemon.LOGGER.info("In the UpdateLocator function of PoseableEntityModel: {}", scale);
	}
	

//	@ModifyVariable(at = @At("LOAD"), name="scale"
//			, remap = false//, locals = LocalCapture.CAPTURE_FAILSOFT
//			, method = "updateLocators")
//	private float compatemon$modifyScale(float scale) {
//		Compatemon.LOGGER.debug("In the UpdateLocator, setting the scale: " + scale);
//		return scale ;//* compatemon$newScale;
//	}
}
