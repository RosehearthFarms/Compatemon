package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import farm.rosehearth.compatemon.Compatemon;
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

import static com.cobblemon.mod.common.util.LocalizationUtilsKt.commandLang;


@Mixin(ApothBoss.class)
public class MixinApothBoss {
	
	@Final
	@Shadow(remap=false)
	protected EntityType<?> entity;
	
	@Mutable
	@Final
	@Shadow(remap=false)
	protected @Nullable CompoundTag nbt;
	
	@Inject(method = "createBoss(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;FLdev/shadowsoffire/apotheosis/adventure/loot/LootRarity;)Lnet/minecraft/world/entity/Mob;", at = @At("HEAD"), remap = false)
	public void compatemon$dodifferentLogic(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity, CallbackInfoReturnable<Mob> boss){
		var entityID = EntityType.getKey(this.entity).toString();
		Compatemon.LOGGER.debug("Let's try to create a boss! What's the type?");
		Compatemon.LOGGER.debug("The type is " + entityID);
		if(entityID.equals("cobblemon:pokemon")){
			Compatemon.LOGGER.debug("Wow! It's a pokemon!");
			var properties = PokemonProperties.Companion.parse("species=random uncatchable"," ","=");
			if (properties.getSpecies() == null) {
				boss = null;
			}
			//nbt = properties.saveToNBT();
			var pokemonEntity = properties.createEntity(world.getLevel());
			Compatemon.LOGGER.debug("It's a " + pokemonEntity.getPokemon().getDisplayName());
			nbt = pokemonEntity.saveWithoutId(nbt);  //writeNBT(nbt);
			
		}
		
		
	}
}
