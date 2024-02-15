package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.AdventureConfig;
import dev.shadowsoffire.apotheosis.adventure.AdventureModule;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import dev.shadowsoffire.apotheosis.adventure.boss.BossEvents;
import dev.shadowsoffire.apotheosis.adventure.boss.BossRegistry;
import dev.shadowsoffire.apotheosis.adventure.client.BossSpawnMessage;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.placebo.network.PacketDistro;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import farm.rosehearth.compatemon.utils.CompateUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
	
	@Inject(method = "createEntity()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;"
			,at = @At("RETURN"))
	private void compatemon$onSpawnCreatePokemonEntityAsABoss(CallbackInfoReturnable<PokemonEntity> cir) {
		
		if(cir.getReturnValue().getType().toString().equals("entity.cobblemon.pokemon")){
			var world = this.getCtx().getWorld();
			var pos = this.getCtx().getPosition();
			MobSpawnEvent.FinalizeSpawn newEvent = new MobSpawnEvent.FinalizeSpawn(cir.getReturnValue(), world, pos.getX(), pos.getY(), pos.getZ(), world.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null, null);
			MinecraftForge.EVENT_BUS.post(newEvent);
		}
		else{
			Compatemon.LOGGER.debug("The entity created by the POKEMON Spawn Action Class wasn't actually a pokemon?");
		}
		
//		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
//			var isBoss = cir.getReturnValue().getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).contains("apoth.boss");
//			if(isBoss){
//				var rarityKey = cir.getReturnValue().getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).getString("apoth.rarity.color");
//				cir.getReturnValue().getPokemon().setNickname(cir.getReturnValue().getPokemon().getNickname().withStyle(Style.EMPTY.withColor(TextColor.parseColor(rarityKey))));
//			}
//		}
		
		if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
			float size_scale = CompatemonScaleUtils.Companion.getNewScale(MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
			//float weight_scale = CompatemonScaleUtils.Companion.getNewScale(MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
			if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && ApotheosisConfig.DoBossSizing)
			{
				if(ApotheosisConfig.BossSizingEntities.toUpperCase().equals("POKEMON"))
				{
					if(cir.getReturnValue() instanceof PokemonEntity && CompateUtils.PokemonIsBoss(cir.getReturnValue())) //
						size_scale *= ApotheosisConfig.DefaultBossSizeScale;
				}
				else if(ApotheosisConfig.BossSizingEntities.toUpperCase().equals("NON-POKEMON"))
				{
					if(!(cir.getReturnValue() instanceof PokemonEntity))
						size_scale *= ApotheosisConfig.DefaultBossSizeScale;
				}
				else if(ApotheosisConfig.BossSizingEntities.toUpperCase().equals("ALL") && CompateUtils.PokemonIsBoss(cir.getReturnValue()))
				{
					size_scale *= ApotheosisConfig.DefaultBossSizeScale;
				}
				
				
			}
			// Actually set the scale of the spawning pokemonEntity. Will eventually add a new scale type for weight.
			CompatemonScaleUtils.Companion.setScale(cir.getReturnValue(), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
			
		}
		
		
	}
	
}
