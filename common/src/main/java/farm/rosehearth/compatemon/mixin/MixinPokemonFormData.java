package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.pokemon.FormData;
import farm.rosehearth.compatemon.Compatemon;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_PEHKUI;

@Mixin(FormData.class)
abstract class MixinPokemonFormData {
//
//	@Mutable
//	@Shadow(remap=false)
//	private float _height;
//	@Mutable
//	@Shadow(remap=false)
//	private float _weight;
//	@Mutable
//	@Shadow(remap=false)
//	private EntityDimensions _hitbox;
//
//	//@Inject(at=@At("HEAD"),remap=false,method="")
//
//	@Inject(at=@At("RETURN"),remap=false,method="getHitbox")
//	public void compatemon$getHitbox(CallbackInfoReturnable<EntityDimensions> cir)
//	{
//		if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
//			//cir.getReturnValue().scale();
//		}
//	}
//
//
//	@Inject(at=@At("RETURN"),remap=false,method="getHeight")
//	public void compatemon$getHeight(CallbackInfoReturnable<EntityDimensions> cir)
//	{
//		if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
//			//cir.getReturnValue().scale();
//		}
//	}
//
//	@Inject(at=@At("RETURN"),remap=false,method="getWeight")
//	public void compatemon$getWeight(CallbackInfoReturnable<EntityDimensions> cir)
//	{
//		if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
//			//cir.getReturnValue().scale();
//		}
//	}
}
