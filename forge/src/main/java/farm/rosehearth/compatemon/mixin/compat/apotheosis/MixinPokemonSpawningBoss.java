package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.AdventureConfig;
import dev.shadowsoffire.apotheosis.adventure.client.BossSpawnMessage;
import dev.shadowsoffire.placebo.network.PacketDistro;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_COMPATEMON;

@Mixin(value = PokemonSpawnAction.class, remap = false)
abstract class MixinPokemonSpawningBoss extends SpawnAction<PokemonEntity> {
	
	
	public MixinPokemonSpawningBoss(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {
		
		super(ctx, detail);
	}
	
	@Inject(method = "createEntity()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", at = @At("RETURN"))
	private void compatemon$createPokemonEntityReturn(CallbackInfoReturnable<PokemonEntity> cir) {
		var world = this.getCtx().getWorld();
		var pos = this.getCtx().getPosition();
		RandomSource rand = world.getRandom();
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			var isBoss = cir.getReturnValue().getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).contains("apoth.boss");
			if(isBoss){
				Player player = world.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(), -1, false);
				var rarityKey = cir.getReturnValue().getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).getString("apoth.rarity.color");
				var name = cir.getReturnValue().getPokemon().getNickname().withStyle(Style.EMPTY.withColor(TextColor.parseColor(rarityKey)));
				cir.getReturnValue().getPokemon().setNickname(name);
				world.players().forEach(p -> {
					Vec3 tPos = new Vec3(pos.getX(), AdventureConfig.bossAnnounceIgnoreY ? p.getY() : pos.getY(), pos.getZ());
					if (p.distanceToSqr(tPos) <= AdventureConfig.bossAnnounceRange * AdventureConfig.bossAnnounceRange) {
						((ServerPlayer) p).connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("info.apotheosis.boss_spawn", name, (int) pos.getX(), (int) pos.getY())));
						TextColor color = name.getStyle().getColor();
						PacketDistro.sendTo(Apotheosis.CHANNEL, new BossSpawnMessage(pos, color == null ? 0xFFFFFF : color.getValue()), player);
					}
				});
			}
		}
	}
	
}
