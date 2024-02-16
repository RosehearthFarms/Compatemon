package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.client.gui.summary.widgets.NicknameEntryWidget;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_RARITY_COLOR;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_COMPATEMON;

@Mixin(NicknameEntryWidget.class)
public class MixinNicknameEntryWidget extends EditBox {

	
	public MixinNicknameEntryWidget(Font font, int x, int y, int width, int height, Component message) {
		super(font
				, x, y, width, height, message);
	}
	
	@Inject(at=@At("RETURN")
	,remap=false
	,method="<init>")
		public void compatemon$onInitNicknameEntryWidget(Pokemon pokemon, int x, int y, int width, int height, boolean isParty, Component text, CallbackInfo cir){
		
	}
	
	@ModifyConstant(method="<init>"
			,constant = @Constant(intValue=12)
			,remap=false)
	public int compatemon$fixMaxNameLength(int value){
		return 24;
	}
	
	
	@Inject(at=@At("RETURN")
			,remap=false
			,method="setSelectedPokemon")
	public void compatemon$setSelectedPokemon(Pokemon pokemon, CallbackInfo cir){
		super.setMessage(pokemon.getDisplayName());
	}
}
