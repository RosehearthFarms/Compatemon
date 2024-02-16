package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.pehkui.util.CompatemonScaleUtils;
import farm.rosehearth.compatemon.util.CompateUtils;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

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
