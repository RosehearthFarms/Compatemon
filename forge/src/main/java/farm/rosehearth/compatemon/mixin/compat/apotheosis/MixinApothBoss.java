package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.boss.BossStats;
import dev.shadowsoffire.apotheosis.adventure.loot.LootController;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import dev.shadowsoffire.apotheosis.adventure.loot.LootCategory;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.util.NameHelper;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.api.entity.PersistantDespawner;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import farm.rosehearth.compatemon.utils.CompatemonDataKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import com.cobblemon.mod.common.command.argument.PokemonPropertiesArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Map;

import static com.cobblemon.mod.common.util.LocalizationUtilsKt.commandLang;
import static dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss.enchantBossItem;
import static dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss.modifyBossItem;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.COMPAT_SCALE_SIZE;

/**
 *
 */
@Mixin(ApothBoss.class)
public class MixinApothBoss {
	
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
	
	/**
	 * @param world
	 * @param pos
	 * @param random
	 * @param luck
	 * @param rarity
	 * @param boss
	 */
	@Inject(at = @At("HEAD"), method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;", remap = false)
	public void compatemon$createPokemonBossHead(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			var entityID = EntityType.getKey(this.entity).toString();
			
			Compatemon.LOGGER.debug("Let's try to create a boss! What's the type?");
			Compatemon.LOGGER.debug("The type is " + entityID + "!");
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
				Compatemon.LOGGER.debug("Wow! It's a pokemon!");
				Compatemon.LOGGER.debug("It's a " + pokemonEntity.getPokemon().showdownId());
				if(rarity != null){
					Compatemon.LOGGER.debug("Here's the rarity: " + rarity.toString());
				}
				nbt = pokemonEntity.saveWithoutId(new CompoundTag()); //writeNBT
				
			}
		}
	}
	
	/**
	 * @param world
	 * @param pos
	 * @param random
	 * @param luck
	 * @param rarity
	 * @param boss
	 */
	@Inject(at = @At("RETURN"), method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;", remap = false)
	public void compatemon$createPokemonBossReturn(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
			
			if(EntityType.getKey(this.entity).toString().equals("cobblemon:pokemon")){
				//meow
//				if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
//					float size_scale = CompatemonScaleUtils.Companion.getScale(((PokemonEntity) (boss.getReturnValue())).getPokemon(), MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE) + 1.0f;
//					float weight_scale = CompatemonScaleUtils.Companion.getScale(((PokemonEntity) (boss.getReturnValue())).getPokemon(), MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
//					CompatemonScaleUtils.Companion.setScale((PokemonEntity) (boss.getReturnValue()), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
//				}
			}
		}
	}
	
	/**
	 *
	 * @param rand
	 * @param entity
	 * @param luck
	 * @param rarity
	 * @param cir
	 */
	@Inject(at = @At("HEAD"), method = "initBoss", remap = false)
	public void compatemon$initPokemonBossHead(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity, CallbackInfo cir) {
		
	}
	
	/**
	 *
	 * @param rand
	 * @param entity
	 * @param luck
	 * @param rarity
	 * @param cir
	 */
	@Inject(at = @At("RETURN"), method = "initBoss", remap = false)
	public void compatemon$initPokemonBossReturn(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity, CallbackInfo cir) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			if(entity.getType().toString().equals("entity.cobblemon.pokemon")){
				//
				String s_rarity = RarityRegistry.INSTANCE.getKey(rarity).toString() + "~" + rarity.getColor().serialize() + "~" + rarity.ordinal();
				CompoundTag compatemonData = new CompoundTag();
				compatemonData.put(CompatemonDataKeys.MOD_ID_COMPATEMON, new CompoundTag());
				compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putBoolean("apoth.boss", true);
				compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putString("apoth.rarity", s_rarity);
				compatemonData.getCompound(CompatemonDataKeys.MOD_ID_COMPATEMON).putString("apoth.rarity.color", rarity.getColor().serialize());
				
				((PokemonEntity)entity).getPokemon().getPersistentData().merge(compatemonData);
			}
		}
		
	}
	
	@Inject(
			at=@At("HEAD")
			,remap = false
			,method="modifyBossItem"
	)
	private static void compatemon$modifyBossItemHEAD(ItemStack stack, RandomSource rand, String bossName, float luck, LootRarity rarity, BossStats stats, CallbackInfoReturnable<ItemStack> item)
	{
		enchantBossItem(rand, stack, Apotheosis.enableEnch ? stats.enchLevels()[2] : stats.enchLevels()[3], true);
		NameHelper.setItemName(rand, stack);
		stack = LootController.createLootItem(stack, LootCategory.forItem(stack), rarity, rand);
		
		String bossOwnerName = String.format(NameHelper.ownershipFormat, bossName);
		Component name = AffixHelper.getName(stack);
		//Compatemon.LOGGER.debug(name.getStyle().toString());
		Compatemon.LOGGER.debug(name.toString());
	}
}
