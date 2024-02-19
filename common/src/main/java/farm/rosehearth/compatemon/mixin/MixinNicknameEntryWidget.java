package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.client.gui.summary.widgets.NicknameEntryWidget;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NicknameEntryWidget.class)
public class MixinNicknameEntryWidget extends EditBox {

	@Shadow(remap=false)
	private Pokemon pokemon;
	
	public MixinNicknameEntryWidget(Font font, int x, int y, int width, int height, Component message) {
		super(font, x, y, width, height, message);
	}
	
//	@Inject(at=@At("RETURN")
//	,remap=false
//	,method="<init>")
//		public void compatemon$onInitNicknameEntryWidget(Pokemon pokemon, int x, int y, int width, int height, boolean isParty, Component text, CallbackInfo cir){
//
//	}
//
	@ModifyConstant(method="<init>"
			,constant = @Constant(intValue=12)
			,remap=false)
	public int compatemon$fixMaxNameLength(int value){
		return 32;
	}
	
	
//	@Inject(at=@At("RETURN")
//			,remap=false
//			,method="setSelectedPokemon")
//	public void compatemon$setSelectedPokemon(Pokemon pokemon, CallbackInfo cir){
//		super.setMessage(pokemon.getDisplayName());
//	}
	
//	@Inject(at=@At("RETURN")
//			,remap=false
//			,method="updateNickname")
//	public void compatemon$updateNickname(String nickname, CallbackInfo cir){
//		super.setMessage(pokemon.getDisplayName());
//	}
//
//	@ModifyArgs(at=@At(value="INVOKE", target="Lcom/cobblemon/mod/common/client/render/RenderHelperKt;drawScaledText$default(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/Number;Ljava/lang/Number;FLjava/lang/Number;IIZZILjava/lang/Object;)V")
//	,remap=false
//	,method="render")
//	private void injected(Args args) {
//
//	}
}
