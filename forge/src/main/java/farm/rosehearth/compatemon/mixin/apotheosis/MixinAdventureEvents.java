package farm.rosehearth.compatemon.mixin.apotheosis;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.AdventureConfig;
import dev.shadowsoffire.apotheosis.adventure.AdventureEvents;
import dev.shadowsoffire.apotheosis.adventure.compat.GameStagesCompat;
import dev.shadowsoffire.apotheosis.adventure.loot.LootCategory;
import dev.shadowsoffire.apotheosis.adventure.loot.LootController;
import dev.shadowsoffire.apotheosis.adventure.affix.socket.gem.GemRegistry;
import dev.shadowsoffire.placebo.reload.WeightedDynamicRegistry;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.util.CompatemonDataKeys.*;

@Mixin(AdventureEvents.class)
abstract class MixinAdventureEvents {
	
	@Inject(
			at=@At("HEAD"),
			remap=false,
			method="special"
	)
	public void compatemon$specialAddAffixItemsToPokemon(MobSpawnEvent.FinalizeSpawn e, CallbackInfo cir){
		if (    e.getSpawnType() == MobSpawnType.NATURAL &&
				Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) &&
				e.getEntity().getType().toString().equals("entity.cobblemon.pokemon") &&
				ApotheosisConfig.PokemonAffixItemRate > 0)
		{
			if(e.getEntity().getRandom().nextFloat()*100 <= ApotheosisConfig.PokemonAffixItemRate && Apotheosis.enableAdventure){
				Player player = e.getLevel().getNearestPlayer(e.getX(), e.getY(), e.getZ(), -1, false);
				if(player == null) return;
				ItemStack affixItem = LootController.createRandomLootItem(e.getLevel().getRandom(), null, player, (ServerLevel) e.getEntity().level());
				
				if(affixItem.isEmpty()) return;
				
				affixItem.getOrCreateTag().putBoolean("apoth_rspawn", true);
				LootCategory cat = LootCategory.forItem(affixItem);
				EquipmentSlot slot = cat.getSlots()[0];
				e.getEntity().setItemSlot(slot, affixItem);
				e.getEntity().setGuaranteedDrop(slot);
			}
		}
	}
	
	@Inject(
			at=@At("HEAD"),
			remap=false,
			method="dropsHigh"
			
	)
	public void compatemon$dropsHigh(LivingDropsEvent e, CallbackInfo cir) {
		if (e.getSource().getEntity() instanceof ServerPlayer p &&
				Compatemon.ShouldLoadMod(MOD_ID_APOTHEOSIS) &&
				e.getEntity().getType().toString().equals("entity.cobblemon.pokemon")) {
			
			float chance = ApotheosisConfig.PokemonGemDropChance;
			if(((PokemonEntity)(e.getEntity())).getPokemon().getPersistentData().getCompound(MOD_ID_COMPATEMON).contains(APOTH_BOSS)){
				chance += AdventureConfig.gemBossBonus;
			}
			if (p.getRandom().nextFloat() <= chance && Apotheosis.enableAdventure) {
				Entity ent = e.getEntity();
				e.getDrops()
						.add(new ItemEntity(ent.level(), ent.getX(), ent.getY(), ent.getZ(), GemRegistry.createRandomGemStack(p.getRandom(), (ServerLevel) p.level(), p.getLuck(), WeightedDynamicRegistry.IDimensional.matches(p.level()), GameStagesCompat.IStaged.matches(p)), 0, 0, 0));
				
				ApotheosisConfig.LOGGER.debug("Pokemon Gem Dropped! ");
			}
		}
	}
}
