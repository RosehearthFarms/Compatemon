package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_RARITY_COLOR;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_COMPATEMON;

/**
 * Mixin to the Pokemon class file in order to actually allow a nickname to be colored? Why was this not a thing? One of the network packet handlers casts to string instead of just using the MutableText
 */
@Mixin(Pokemon.class)
public class MixinPokemonClass {
	@Shadow(remap=false)
	private @Nullable MutableComponent nickname;
	@Shadow(remap=false)
	private CompoundTag persistentData;
	
	@Inject(at=@At("RETURN")
		,method="getDisplayName"
		,remap=false
		,cancellable = true)
	public void compatemon$getDisplayNameWithColor(CallbackInfoReturnable<Component> cir){
		if(persistentData.getCompound(MOD_ID_COMPATEMON).contains(APOTH_RARITY_COLOR) ){//cir.setReturnValue(nickname.copy().withStyle(Style.EMPTY.withColor(TextColor.parseColor(persistentData.getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR)))));
			
			var x = cir.getReturnValue().copy().setStyle(Style.EMPTY.withColor(TextColor.parseColor(persistentData.getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR))));
			cir.setReturnValue(x);
		}
	}
	
	@Inject(at=@At("HEAD")
		,method="setNickname"
		,remap=false)
	public void compatemon$setNicknameWithColor(@Nullable MutableComponent value, CallbackInfo cir){
		
		if(value != null && persistentData.getCompound(MOD_ID_COMPATEMON).contains(APOTH_RARITY_COLOR)){
			nickname = value.withStyle(Style.EMPTY.withColor(TextColor.parseColor(persistentData.getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR))));
			value=nickname;
		}
				
		
		
	}
}
