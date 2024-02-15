package farm.rosehearth.compatemon.mixin.compat.apotheosis;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.APOTH_BOSS;
import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.APOTH_RARITY;

@Mixin(AdventureEvents.class)
abstract class MixinAdventureEvents {
	
	@Inject(
			at=@At("HEAD"),
			remap=false,
			method="special"
	)
	public void compatemon$specialAddAffixItemsToPokemon(MobSpawnEvent.FinalizeSpawn e, CallbackInfo cir){
		if (    e.getSpawnType() == MobSpawnType.NATURAL &&
				e.getLevel().getRandom().nextFloat() <= AdventureConfig.randomAffixItem &&
				e.getEntity().getType().toString().equals("entity.cobblemon.pokemon") &&
				ApotheosisConfig.PokemonDropAffixItems)
		{
			Player player = e.getLevel().getNearestPlayer(e.getX(), e.getY(), e.getZ(), -1, false);
			if (player == null) return;
			ItemStack affixItem = LootController.createRandomLootItem(e.getLevel().getRandom(), null, player, (ServerLevel) e.getEntity().level());
			ApotheosisConfig.LOGGER.debug("We've made us a new Loot item for a Pokemon! " + affixItem.toString());
			if (affixItem.isEmpty()) return;
			affixItem.getOrCreateTag().putBoolean("apoth_rspawn", true);
			LootCategory cat = LootCategory.forItem(affixItem);
			EquipmentSlot slot = cat.getSlots()[0];
			e.getEntity().setItemSlot(slot, affixItem);
			e.getEntity().setGuaranteedDrop(slot);
		}
	}
	
	@Inject(
			at=@At("HEAD"),
			remap=false,
			method="dropsHigh"
			
	)
	public void compatemon$dropsHigh(LivingDropsEvent e, CallbackInfo cir) {
		if (e.getSource().getEntity() instanceof ServerPlayer p && e.getEntity().getType().toString().equals("entity.cobblemon.pokemon")) {
			
			float chance = AdventureConfig.gemDropChance;
			float boss_chance = ApotheosisConfig.PokemonDropGems ? chance + (((PokemonEntity)(e.getEntity())).getPokemon().getPersistentData().contains(APOTH_BOSS) ? AdventureConfig.gemBossBonus : 0) : 0;
			float normal_chance = ApotheosisConfig.PokemonDropGems ? chance : 0;
			if (p.getRandom().nextFloat() <= chance) {
				Entity ent = e.getEntity();
				e.getDrops()
						.add(new ItemEntity(ent.level(), ent.getX(), ent.getY(), ent.getZ(), GemRegistry.createRandomGemStack(p.getRandom(), (ServerLevel) p.level(), p.getLuck(), WeightedDynamicRegistry.IDimensional.matches(p.level()), GameStagesCompat.IStaged.matches(p)), 0, 0, 0));
				
				ApotheosisConfig.LOGGER.debug("Pokemon Gem Dropped! ");
			}
		}
	}
}
