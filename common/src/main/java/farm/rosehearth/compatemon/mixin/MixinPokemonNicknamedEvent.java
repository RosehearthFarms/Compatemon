package farm.rosehearth.compatemon.mixin;

import com.cobblemon.mod.common.api.events.pokemon.PokemonNicknamedEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.MOD_ID_COMPATEMON;
import static farm.rosehearth.compatemon.util.CompatemonDataKeys.APOTH_RARITY_COLOR;

@Mixin(PokemonNicknamedEvent.class)
abstract class MixinPokemonNicknamedEvent {
	@Final
	@Shadow(remap=false)
	private Pokemon pokemon;
	@Mutable
	@Shadow(remap=false)
	@Nullable
	private MutableComponent nickname;
	
	@Inject(at=@At("RETURN")
	,remap=false,
	method="<init>")
	public void compatemon$onPokemonNicknamedEvent(ServerPlayer pl, Pokemon p, @Nullable MutableComponent name, CallbackInfo cir){
		if(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_RARITY_COLOR)){
			if(name != null)
				nickname = name.copy().withStyle(Style.EMPTY.withColor(TextColor.parseColor(pokemon.getPersistentData().getCompound(MOD_ID_COMPATEMON).getString(APOTH_RARITY_COLOR))));
			
		}
	}
}
