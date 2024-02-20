package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.pokemon.properties.UncatchableProperty;
import dev.shadowsoffire.apotheosis.adventure.boss.BossStats;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.util.SupportingEntity;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.api.entity.PersistantDespawner;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.apotheosis.IApothBossEntity;
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import farm.rosehearth.compatemon.util.CompateUtils;
import farm.rosehearth.compatemon.util.CompatemonDataKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;


import java.util.Map;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

/**
 *
 */
@Mixin(ApothBoss.class)
//@Implements(@Interface(iface=IApothBossEntity.class, prefix="icompat$"))
abstract class MixinApothBoss
implements IApothBossEntity
{
@Unique
public Entity spawnedEntity;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected int weight;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected float quality;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected AABB size;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	@Nullable
//	protected Set<String> stages;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected List<GearSet.SetPredicate> gearSets;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected Set<ResourceLocation> dimensions;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected LootRarity minRarity;

//	@Mutable
//	@Final
//	@Shadow(remap = false)
//	protected LootRarity maxRarity;
	
	@Mutable
	@Final
	@Shadow(remap = false)
	@Nullable
	protected SupportingEntity mount;
	
	@Mutable
	@Final
	@Shadow(remap = false)
	protected EntityType<?> entity;
	
	@Mutable
	@Final
	@Shadow(remap = false)
	protected @Nullable CompoundTag nbt;
	
	@Final
	@Shadow(remap=false)
	protected Map<LootRarity, BossStats> stats;
	
	
	@Shadow(remap=false)
	abstract public void initBoss(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity);
	
	@Inject(at = @At("HEAD")
			, method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;"
			, remap = false)
	public void compatemon$createPokemonBossHead(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			var entityID = EntityType.getKey(this.entity).toString();
			
			//ApotheosisConfig.LOGGER.debug("Let's try to create a boss! What's the type?");
			//ApotheosisConfig.LOGGER.debug("The type is " + entityID + "!");
			if(entityID.equals("cobblemon:pokemon")){
				//TODO: PokemonPropertyGenerator with logic for Player Location and/or Spawning Biome + Player Stats
				//TODO: CustomProperty for Aggressive to work with FightOrFlight? Glowing needs to be longer and stay after reload
				
				var properties = PokemonProperties.Companion.parse("species=random level=50", " ", "=");
				properties.setLevel(random.nextInt(50) + 25);
				if(properties.getSpecies() == null){
					boss = null;
				}
				var pokemonEntity = properties.createEntity(world.getLevel());
				pokemonEntity.setDespawner(new PersistantDespawner<PokemonEntity>()); // never let it despawn
				pokemonEntity.setPersistenceRequired();
				//if(rarity != null){
				//	ApotheosisConfig.LOGGER.debug("Here's the rarity: " + rarity.toString());
				//}
				nbt = pokemonEntity.saveWithoutId(new CompoundTag()); //writeNBT
			
			}
		}
	}
	
	
	@Inject(at = @At("RETURN")
			, remap = false
			, method = "initBoss")
	public void compatemon$initPokemonBossReturn(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity, CallbackInfo cir) {
		
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && entity.getType().toString().equals("entity.cobblemon.pokemon") && ApotheosisConfig.BossPokemonSpawnRate > 0){
			CompoundTag compatemonData = new CompoundTag();
			compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, new CompoundTag());
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putBoolean(APOTH_BOSS, true);
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putInt(APOTH_RARITY, rarity.ordinal());
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putString(APOTH_RARITY_COLOR, rarity.getColor().serialize());
			//TODO: Replace putString APOTH_RARITY_COLOR with parse APOTH_RARIRTY_ORDINAL
			//Compatemon.LOGGER.debug("We've set the color of " + ((PokemonEntity)entity).getPokemon().getSpecies().getName() + " to " + rarity.getColor().serialize());
			((PokemonEntity)entity).getPokemon().getPersistentData().merge(compatemonData);
			((PokemonEntity)entity).getPokemon().setNickname(((PokemonEntity)entity).getPokemon().getNickname().withStyle(Style.EMPTY.withColor(rarity.getColor())));
		}
		
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && Compatemon.ShouldLoadMod(MOD_ID_PEHKUI) && CompateUtils.isApothBoss(entity))// && ApotheosisConfig.DoBossSizing)
		{
			
			var base_scale = PehkuiConfig.size_scale;
			var addToScale = 0.0f;
			if(
					(ApotheosisConfig.BossSizingEntities.equalsIgnoreCase("POKEMON") && entity instanceof PokemonEntity) ||
			    	(ApotheosisConfig.BossSizingEntities.equalsIgnoreCase("NON-POKEMON") && !(entity instanceof PokemonEntity)) ||
			    	(ApotheosisConfig.BossSizingEntities.equalsIgnoreCase("ALL"))
			)
			{
				base_scale *= ApotheosisConfig.DefaultBossSizeScale;
				addToScale = 0.0f;
			}
			CompatemonScaleUtils.Companion.setScale(entity, ScaleTypes.BASE, COMPAT_SCALE_SIZE, base_scale, addToScale);
		}
		
	}
	
//	@Inject(
//			at=@At("HEAD")
//			,remap = false
//			,method="modifyBossItem")
//	private static void compatemon$modifyBossItemHEAD(ItemStack stack, RandomSource rand, String bossName, float luck, LootRarity rarity, BossStats stats, CallbackInfoReturnable<ItemStack> item)
//	{
//	}
	
	@Shadow(remap=false)
	public abstract Mob createBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity);
	
	@Override
	public Mob createPokeBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, Entity entityFromSpawn) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && entityFromSpawn.getType().toString().equals("entity.cobblemon.pokemon" ) && ApotheosisConfig.BossPokemonSpawnRate > 0){
			
			entity = entityFromSpawn.getType();
			int clevel = ((PokemonEntity) entityFromSpawn).getPokemon().getLevel();
			((PokemonEntity) entityFromSpawn).getPokemon().setLevel(clevel + random.nextInt(25) - 5);
			// Kind of expect this to break sometime if the level is out of the range. Hoping Cobblemon accounted for that though.
			
			if(!ApotheosisConfig.BossPokemonCatchable){
				((PokemonEntity) entityFromSpawn).getPokemon().getCustomProperties().add(UncatchableProperty.INSTANCE.uncatchable());
			}
			
			((PokemonEntity) entityFromSpawn).setDespawner(new PersistantDespawner<PokemonEntity>()); // never let it despawn
			nbt = ((PokemonEntity) entityFromSpawn).saveWithoutId(new CompoundTag());
			
			//ApotheosisConfig.LOGGER.debug("Create that PokeBoss! " + ((PokemonEntity) entityFromSpawn).getPokemon().showdownId());
			
			CompoundTag fakeNbt = this.nbt;
			fakeNbt.putString("id", EntityType.getKey(this.entity).toString());
			
			Mob entity = (Mob) entityFromSpawn;
			if(this.nbt != null) entity.load(this.nbt);
			
			
			this.initBoss(random, entity, luck, rarity);
			
			// had to take out the mount info as for SOME reason, it kept trying to create spiders
			// would LOVe to add in bosses mounted as pokemon at some point as compatability with Cobblemounts
			
			entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
			return entity;
		}
		else{
			Compatemon.LOGGER.debug("Using the default createBoss");
			return createBoss(world,pos,random,luck,rarity);
		}
	}
}
