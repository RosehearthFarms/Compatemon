package farm.rosehearth.compatemon.mixin.compat.sophisticatedcore;

import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.sophisticatedcore.SophisticatedConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import net.p3pp3rf1y.sophisticatedcore.controller.ControllerBlockEntityBase;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

@Mixin(ControllerBlockEntityBase.class)
public class MixinControllerBlockEntityBase {
	@Final
	@Mutable
	@Shadow(remap=false)
	public static int SEARCH_RANGE;
	
	@Unique
	private static boolean compatemon$modifiedRange = false;
	
	@Inject(at=@At("TAIL")
		,remap=false
		,method="onLoad")
	public void compatemon$onOnLoad(CallbackInfo cir){
		if(Compatemon.ShouldLoadMod(MOD_ID_SOPHISTICATEDSTORAGE) && !compatemon$modifiedRange){
			SophisticatedConfig.LOGGER.debug("Modifying SEARCH_RANGE to " + SophisticatedConfig.StorageSearchRange);
			SEARCH_RANGE = SophisticatedConfig.StorageSearchRange;
			if(SEARCH_RANGE < SOPHISTICATED_SEARCH_MIN) SEARCH_RANGE = SOPHISTICATED_SEARCH_MIN;
			else if(SEARCH_RANGE > SOPHISTICATED_SEARCH_MAX) SEARCH_RANGE = SOPHISTICATED_SEARCH_MAX;
			compatemon$modifiedRange = true;
		}
	}
	
	@ModifyConstant(
			method="isWithinRange"
			,constant = @Constant(intValue=15)
			,remap=false
	)
	private int compatemon$replaceSearchRange(int value){
		int searchRange = value;
		if(Compatemon.ShouldLoadMod(MOD_ID_SOPHISTICATEDSTORAGE)){
			searchRange = SophisticatedConfig.StorageSearchRange;
			if(searchRange < SOPHISTICATED_SEARCH_MIN) searchRange = SOPHISTICATED_SEARCH_MIN;
			else if(searchRange > SOPHISTICATED_SEARCH_MAX) searchRange = SOPHISTICATED_SEARCH_MAX;
		}
		return searchRange;
	}
}
