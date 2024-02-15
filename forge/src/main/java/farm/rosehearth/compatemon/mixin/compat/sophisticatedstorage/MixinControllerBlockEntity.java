package farm.rosehearth.compatemon.mixin.compat.sophisticatedstorage;

import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.sophisticatedcore.SophisticatedConfig;
import net.p3pp3rf1y.sophisticatedstorage.block.ControllerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

@Mixin(ControllerBlockEntity.class)
public class MixinControllerBlockEntity {
	
	
	@ModifyConstant(
			method="getRenderBoundingBox"
			,constant = @Constant(doubleValue=15.0)
	)
	private double compatemon$replaceSearchRangeInRenderBoundingBox(double value){
		double searchRange = value;
		if(Compatemon.ShouldLoadMod(MOD_ID_SOPHISTICATEDSTORAGE)){
			SophisticatedConfig.LOGGER.debug("Modifying the CONSTANT 15.0 to " + SophisticatedConfig.StorageSearchRange);
			searchRange = SophisticatedConfig.StorageSearchRange;
			if(searchRange < SOPHISTICATED_SEARCH_MIN) searchRange = 15;
			else if(searchRange > SOPHISTICATED_SEARCH_MAX) searchRange = SOPHISTICATED_SEARCH_MAX;
		}
		return searchRange;
	}
}
