package farm.rosehearth.compatemon.mixin;

import farm.rosehearth.compatemon.utils.CompateUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraftforge.fml.ModList;

@Mixin(CompateUtils.class)
public class MixinModLoaded {
	
	//@Inject(at=@At("RETURN"), method="IsModLoaded", remap=false, cancellable = true)
	//private static void compatemon$forgeLogic(String mod_id, CallbackInfoReturnable<Boolean> ret){
	//	ret.setReturnValue(ModList.get().isLoaded(mod_id));
	//}
}
