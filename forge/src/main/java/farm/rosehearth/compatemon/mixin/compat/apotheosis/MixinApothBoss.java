package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import dev.shadowsoffire.apotheosis.adventure.boss.ApothBoss;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import com.cobblemon.mod.common.command.argument.PokemonPropertiesArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import virtuoel.pehkui.api.ScaleTypes;

import static com.cobblemon.mod.common.util.LocalizationUtilsKt.commandLang;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.COMPAT_SCALE_SIZE;


@Mixin(ApothBoss.class)
public class MixinApothBoss {
	
	@Final
	@Shadow(remap=false)
	protected EntityType<?> entity;
	
	@Mutable
	@Final
	@Shadow(remap=false)
	protected @Nullable CompoundTag nbt;
	
	@Inject(at = @At("HEAD"), method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;", remap = false)
	public void compatemon$createPokemonBossHead(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss){
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			var entityID = EntityType.getKey(this.entity).toString();
			
			Compatemon.LOGGER.debug("Let's try to create a boss! What's the type?");
			Compatemon.LOGGER.debug("The type is " + entityID + "!");
			if(entityID.equals("cobblemon:pokemon")){
				var properties = PokemonProperties.Companion.parse("species=random uncatchable level=50", " ", "=");
				if(properties.getSpecies() == null){
					boss = null;
				}
				//nbt = properties.saveToNBT();
				var pokemonEntity = properties.createEntity(world.getLevel());
				Compatemon.LOGGER.debug("Wow! It's a pokemon!");
				Compatemon.LOGGER.debug("It's a " + pokemonEntity.getPokemon().showdownId());
				nbt = pokemonEntity.saveWithoutId(nbt);
				
			}
		}
	}
	
	@Inject(at = @At("RETURN"), method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;",  remap = false)
	public void compatemon$createPokemonBossReturn(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss){
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) && Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
			
			if(EntityType.getKey(this.entity).toString().equals("cobblemon:pokemon")){
				//meow
				if(Compatemon.ShouldLoadMod(MOD_ID_PEHKUI)){
					float size_scale = CompatemonScaleUtils.Companion.getScale(((PokemonEntity)(boss.getReturnValue())).getPokemon(), MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE);
					float weight_scale = CompatemonScaleUtils.Companion.getScale(((PokemonEntity)(boss.getReturnValue())).getPokemon(), MOD_ID_COMPATEMON + ":" + COMPAT_SCALE_WEIGHT);
					CompatemonScaleUtils.Companion.setScale((PokemonEntity)(boss.getReturnValue()), ScaleTypes.BASE, MOD_ID_PEHKUI + ":" + COMPAT_SCALE_SIZE, size_scale);
				}
			}
		}
	}
}
