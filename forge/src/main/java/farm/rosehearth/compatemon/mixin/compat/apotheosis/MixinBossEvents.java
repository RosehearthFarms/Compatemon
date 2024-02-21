package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.api.entity.Despawner;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.AdventureConfig;
import dev.shadowsoffire.apotheosis.adventure.AdventureModule;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import dev.shadowsoffire.apotheosis.adventure.boss.BossEvents;
import dev.shadowsoffire.apotheosis.adventure.boss.BossRegistry;
import dev.shadowsoffire.apotheosis.adventure.client.BossSpawnMessage;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.placebo.network.PacketDistro;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.events.CompatemonEvents;
import farm.rosehearth.compatemon.events.apotheosis.ApothBossSpawnedEvent;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.apotheosis.IApothBossEntity;
import farm.rosehearth.compatemon.util.CompateUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

@Mixin(BossEvents.class)
abstract class MixinBossEvents {
	
	@Shadow(remap=false)
	public Object2IntMap<ResourceLocation> bossCooldowns;
	
	@Shadow(remap = false)
	@Nullable
	private Component getName(Mob boss) {
		return boss.getSelfAndPassengers().filter(e -> ((PokemonEntity)e).getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_BOSS)).findFirst().map(Entity::getCustomName).orElse(null);
	}
	
	@Invoker(value="canSpawn",remap = false)
	private static boolean invokeCanSpawn(LevelAccessor world, Mob entity, double playerDist){
		throw new AssertionError();
	};
	
	@Inject(at=@At("HEAD"),
	method="naturalBosses",
	remap=false)
	private void compatemon$naturalBossesForPokemon(MobSpawnEvent.FinalizeSpawn e, CallbackInfo cir)
	{
		
//		CompatemonEvents.APOTH_BOSS_SPAWNED.postThen(new ApothBossSpawnedEvent(e.getEntity()), savedEvent -> null, savedEvent -> {
//			return null;
//		});
		if (e.getEntity().getType().toString().equals("entity.cobblemon.pokemon") &&
				ApotheosisConfig.BossPokemonSpawnRate > 0 &&
				Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) ) {
			LivingEntity entity = e.getEntity();
			CompoundTag originalEntity = entity.saveWithoutId(new CompoundTag());
			Pokemon originalPokemon = ((PokemonEntity)entity).getPokemon().clone(false,false);
			RandomSource rand = e.getLevel().getRandom();
			
			if (this.bossCooldowns.getInt(entity.level().dimension().location()) <= 0 &&  !e.getLevel().isClientSide()  && e.getResult() != Event.Result.DENY) {
				ServerLevelAccessor sLevel = (ServerLevelAccessor) e.getLevel();
				ResourceLocation dimId = sLevel.getLevel().dimension().location();
				Pair<Float, BossEvents.BossSpawnRules> rules = AdventureConfig.BOSS_SPAWN_RULES.get(dimId);
				
				if (rules == null) return;
				if (rand.nextFloat() <= (ApotheosisConfig.BossPokemonSpawnRate/100.0f)) {
				
					Player player = sLevel.getNearestPlayer(e.getX(), e.getY(), e.getZ(), -1, false);
					if (player == null) return; // Spawns require player context
					ApothBoss item = BossRegistry.INSTANCE.getRandomItem(rand, player.getLuck(), WeightedDynamicRegistry.IDimensional.matches(sLevel.getLevel()), GameStagesCompat.IStaged.matches(player));
					
					if (item == null) {
						AdventureModule.LOGGER.error("Attempted to spawn a boss in dimension {} using configured boss spawn rule {}/{} but no bosses were made available.", dimId, rules.getRight(), rules.getLeft());
						return;
					}
					var despawner = ((PokemonEntity)entity).getDespawner();
					
					Mob boss = ((IApothBossEntity) ((Object)item)).createPokeBoss(sLevel, BlockPos.containing(e.getX() - 0.5, e.getY(), e.getZ() - 0.5), rand, player.getLuck(), null, entity);
					
					if (invokeCanSpawn(sLevel, boss, player.distanceToSqr(boss))) {
						
						((PokemonEntity) entity).setPersistenceRequired();
						var n = ((PokemonEntity)boss).getCustomName();
						ApotheosisConfig.LOGGER.debug("It's {} - the {}" , n.getString(), ((PokemonEntity)boss).getPokemon().getSpecies().getName());
						if (AdventureConfig.bossAutoAggro) {
							boss.setTarget(player);
						}
						
						e.setResult(Event.Result.DENY);
						
						Component name = this.getName(boss);
						
						
						LootRarity rarity = RarityRegistry.byOrdinal(((PokemonEntity)boss).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).getInt(APOTH_RARITY)).get();
						if(rarity != null) ApotheosisConfig.LOGGER.debug("Here's the rarity in BossEvents Mixin: {}", rarity.toString());
						
						
						
						if (name == null || name.getStyle().getColor() == null) AdventureModule.LOGGER.warn("A Boss {} ({}) has spawned without a custom name!", boss.getName().getString(), EntityType.getKey(boss.getType()));
						else {
							sLevel.players().forEach(p -> {
								Vec3 tPos = new Vec3(boss.getX(), AdventureConfig.bossAnnounceIgnoreY ? p.getY() : boss.getY(), boss.getZ());
								if (p.distanceToSqr(tPos) <= AdventureConfig.bossAnnounceRange * AdventureConfig.bossAnnounceRange) {
									
									String r = RarityRegistry.INSTANCE.getKey(rarity).toString();
									String translatableKey = "info.compatemon.boss_spawn."  + r.substring(r.indexOf(":")+1);
									
									var translated = Component.translatable(translatableKey, name, (int) boss.getX(), (int) boss.getZ());
									// Send Custom Message or Fail and send default apotheosis message
									((ServerPlayer) p).connection.send(new ClientboundSetActionBarTextPacket(translated));
									
									TextColor color = name.getStyle().getColor();
									PacketDistro.sendTo(Apotheosis.CHANNEL, new BossSpawnMessage(boss.blockPosition(), color == null ? 0xFFFFFF : color.getValue()), player);
								}
							});
						}
						this.bossCooldowns.put(entity.level().dimension().location(), AdventureConfig.bossSpawnCooldown);
						
					}
					else{
						// Need to set the pokemon and the entity back to normal bc Pokemon get initialized differently.
						// This means the Pokemon Object, the pokemon's persistent data,
						// the entity's effects and attributes, and the entity's item slots
						entity.load(originalEntity);
						entity.removeAllEffects();
						((PokemonEntity)entity).setPokemon(originalPokemon);
						((PokemonEntity)entity).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).remove(APOTH_BOSS);
						((PokemonEntity)entity).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).remove(APOTH_RARITY);
						((PokemonEntity)entity).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).remove(APOTH_RARITY_COLOR);
						((PokemonEntity)entity).setDespawner(despawner);
					}
				}
			}
		}
	}
}
