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
import farm.rosehearth.compatemon.utils.CompatemonDataKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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


import java.util.Map;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;

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
			
			ApotheosisConfig.LOGGER.debug("Let's try to create a boss! What's the type?");
			ApotheosisConfig.LOGGER.debug("The type is " + entityID + "!");
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
				//ApotheosisConfig.LOGGER.debug("Wow! It's a pokemon!");
				//ApotheosisConfig.LOGGER.debug("It's a " + pokemonEntity.getPokemon().showdownId());
				if(rarity != null){
					ApotheosisConfig.LOGGER.debug("Here's the rarity: " + rarity.toString());
				}
				nbt = pokemonEntity.saveWithoutId(new CompoundTag()); //writeNBT
			
			}
		}
	}
	
	
	@Inject(at = @At("RETURN")
			, remap = false
			, method = "initBoss")
	public void compatemon$initPokemonBossReturn(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity, CallbackInfo cir) {
		
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && entity.getType().toString().equals("entity.cobblemon.pokemon")){
			String s_rarity = RarityRegistry.INSTANCE.getKey(rarity).toString() + "~" + rarity.getColor().serialize() + "~" + rarity.ordinal();
			CompoundTag compatemonData = new CompoundTag();
			compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, new CompoundTag());
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putBoolean("apoth.boss", true);
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putString("apoth.rarity", s_rarity);
			compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putString("apoth.rarity.color", rarity.getColor().serialize());
			Compatemon.LOGGER.debug("We've set the color of " + ((PokemonEntity)entity).getPokemon().getSpecies().getName() + " to " + rarity.getColor().serialize());
			((PokemonEntity)entity).getPokemon().getPersistentData().merge(compatemonData);
		}
		
		
	}
	
	@Inject(
			at=@At("HEAD")
			,remap = false
			,method="modifyBossItem")
	private static void compatemon$modifyBossItemHEAD(ItemStack stack, RandomSource rand, String bossName, float luck, LootRarity rarity, BossStats stats, CallbackInfoReturnable<ItemStack> item)
	{
//		enchantBossItem(rand, stack, Apotheosis.enableEnch ? stats.enchLevels()[2] : stats.enchLevels()[3], true);
//		NameHelper.setItemName(rand, stack);
//		stack = LootController.createLootItem(stack, LootCategory.forItem(stack), rarity, rand);
//
//		String bossOwnerName = String.format(NameHelper.ownershipFormat, bossName);
//		Component name = AffixHelper.getName(stack);
//		//Compatemon.LOGGER.debug(name.getStyle().toString());
//		ApotheosisConfig.LOGGER.debug(name.toString());
	}
	
	@Shadow(remap=false)
	public abstract Mob createBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity);
	@Override
	public Mob createPokeBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, Entity entityFromSpawn) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && entityFromSpawn.getType().toString().equals("entity.cobblemon.pokemon")){
			//TODO: PokemonPropertyGenerator with logic for Player Location and/or Spawning Biome + Player Stats
			//TODO: CustomProperty for Aggressive to work with FightOrFlight? Glowing needs to be longer and stay after reload
			
			if(rarity != null){
				ApotheosisConfig.LOGGER.debug("Here's the rarity: " + rarity.toString());
			}
			entity = entityFromSpawn.getType();
			//PokemonEntity pEntity = ((PokemonEntity) entityFromSpawn);
			((PokemonEntity) entityFromSpawn).getPokemon().setLevel(random.nextInt(50) + 25);
			
			if(!ApotheosisConfig.BossPokemonCatchable){
				((PokemonEntity) entityFromSpawn).getPokemon().getCustomProperties().add(UncatchableProperty.INSTANCE.uncatchable());
			}
			
			((PokemonEntity) entityFromSpawn).setPersistenceRequired();
			((PokemonEntity) entityFromSpawn).setDespawner(new PersistantDespawner<PokemonEntity>()); // never let it despawn
			nbt = ((PokemonEntity) entityFromSpawn).saveWithoutId(new CompoundTag());
			
			ApotheosisConfig.LOGGER.debug("Create that PokeBoss! " + ((PokemonEntity) entityFromSpawn).getPokemon().showdownId());
			//ApotheosisConfig.LOGGER.debug("It's a " + pEntity.getPokemon().showdownId());
			
			CompoundTag fakeNbt = this.nbt;
			fakeNbt.putString("id", EntityType.getKey(this.entity).toString());
			
			Mob entity = (Mob) entityFromSpawn;
			if(this.nbt != null) entity.load(this.nbt);
			
			
			this.initBoss(random, entity, luck, rarity);
			
			if(this.nbt != null) entity.readAdditionalSaveData(this.nbt);
			
			if(this.mount != null){
				Mob mountedEntity = this.mount.create(world.getLevel(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				entity.startRiding(mountedEntity, true);
				entity = mountedEntity;
			}
			
			entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
			return entity;
		}
		else{
			return createBoss(world,pos,random,luck,rarity);
		}
	}
}
