package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import dev.shadowsoffire.apotheosis.util.NameHelper;
import farm.rosehearth.compatemon.Compatemon;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.MOD_ID_APOTHEOSIS;

@Mixin(NameHelper.class)
public class MixinNameHelper {
	
	@Shadow(remap=false)
	private static String[] prefixes;
	
	@Shadow(remap=false)
	private static String[] suffixes;
	
	@Shadow(remap=false)
	public static String suffixFormat;
	
	@Inject(at = @At("RETURN"), method = "setEntityName", remap = false)
	private static void compatemon$setPokemonNicknameReturn(RandomSource rand, Mob entity, CallbackInfoReturnable<String> root) {
		if(Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS)){
			if(entity.getType().toString().equals("entity.cobblemon.pokemon")){
				var pokemonEntity = (PokemonEntity) entity;
				var name = root.getReturnValue();
				if(rand.nextFloat() < 0.3F && prefixes.length > 0){
					name = prefixes[rand.nextInt(prefixes.length)] + " " + name;
				}
				if(rand.nextFloat() < 0.8F && suffixes.length > 0){
					name = String.format(suffixFormat, name, suffixes[rand.nextInt(suffixes.length)]) + " " + ((PokemonEntity) entity).getPokemon().showdownId();
				} else if(suffixes.length == 0){
				
				} else{
					// should hit if the rand.nextFloat() was > 0.8F
					name = name + " the " + ((PokemonEntity) entity).getPokemon().showdownId();
				}
				
				Compatemon.LOGGER.debug("Let's name that " + pokemonEntity.getPokemon().showdownId() + "!");
				Compatemon.LOGGER.debug("Its new name is... " + name);
				((PokemonEntity) entity).setCustomName(Component.literal(name));
				((PokemonEntity) entity).setCustomNameVisible(true);
				Compatemon.LOGGER.debug("Its display name is... " + entity.getDisplayName().getString());
				//nbt = pokemonEntity.saveWithoutId(nbt);
				
			}
		}
	}
}