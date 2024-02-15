package farm.rosehearth.compatemon.mixin.compat.sophisticatedcore;

import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.sophisticatedcore.SophisticatedConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import net.p3pp3rf1y.sophisticatedcore.controller.ControllerBlockEntityBase;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_SOPHISTICATEDSTORAGE;

@Mixin(ControllerBlockEntityBase.class)
public class MixinControllerBlockEntityBase {
	@Final
	@Mutable
	@Shadow(remap=false)
	public static int SEARCH_RANGE;
	
	@Inject(at=@At("TAIL")
		,remap=false
		,method="onLoad")
	public void compatemon$onOnLoad(CallbackInfo cir){
		if(Compatemon.ShouldLoadMod(MOD_ID_SOPHISTICATEDSTORAGE)){
			SophisticatedConfig.LOGGER.debug("Modifying SEARCH_RANGE to " + SophisticatedConfig.StorageSearchRange);
			SEARCH_RANGE = SophisticatedConfig.StorageSearchRange;
			if(SEARCH_RANGE <= 0) SEARCH_RANGE = 15;
			else if(SEARCH_RANGE > 64) SEARCH_RANGE = 64;
		}
	}
	
	@ModifyConstant(
			method="isWithinRange"
			,constant = @Constant(intValue=15)
	)
	private int compatemon$replaceSearchRange(int value){
		int searchRange = value;
		if(Compatemon.ShouldLoadMod(MOD_ID_SOPHISTICATEDSTORAGE)){
			SophisticatedConfig.LOGGER.debug("Modifying the CONSTANT 15 to " + SophisticatedConfig.StorageSearchRange);
			searchRange = SophisticatedConfig.StorageSearchRange;
			if(searchRange <= 0) searchRange = 15;
			else if(searchRange > 64) searchRange = 64;
		}
		return searchRange;
	}
}
