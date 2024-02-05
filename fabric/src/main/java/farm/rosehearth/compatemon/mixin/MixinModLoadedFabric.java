package farm.rosehearth.compatemon.mixin;

import farm.rosehearth.compatemon.utils.CompateUtils;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompateUtils.class)
public class MixinModLoadedFabric {
	
	//@Inject(at=@At("RETURN"), method="IsModLoaded", remap=false, cancellable = true)
	//private static void compatemon$fabricLogic(String mod_id, CallbackInfoReturnable<Boolean> ret){
	//	ret.setReturnValue(FabricLoader.getInstance().isModLoaded(mod_id));
	//}
}
