package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import com.cobblemon.mod.common.net.serverhandling.pokemon.update.SetNicknameHandler;
import com.cobblemon.mod.common.net.messages.server.pokemon.update.SetNicknamePacket;

import com.cobblemon.mod.common.api.storage.PokemonStore;
import com.cobblemon.mod.common.api.storage.pc.link.PCLinkManager;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SetNicknameHandler.class)
public class MixinSetNicknameHandler {
//	private Pokemon pkmn;
//	@Inject(
//			at=@At("TAIL"),
//			remap=false
//			,method="handle(Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/SetNicknamePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V"
//	)
//	public void compatemon$handleNickname(SetNicknamePacket packet, MinecraftServer server, ServerPlayer player, CallbackInfo cir )
//	{
//
//	}
	
//	@Redirect(
//		method="handle(Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/SetNicknamePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V"
//		,at = @At("FIELD"
//				,target = ""
//				,opcode= Opcodes.PUTFIELD)
//	)
//	public Component compatemon$setNicknameColor(Component nickname){
//		return
//	}
}
