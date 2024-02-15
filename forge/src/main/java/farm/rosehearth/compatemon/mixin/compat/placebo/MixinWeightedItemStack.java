package farm.rosehearth.compatemon.mixin.compat.placebo;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import dev.shadowsoffire.placebo.json.WeightedItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * IDK if this is actually needed? May try and disable and see what happens.
 */
@Mixin(WeightedItemStack.class)
public class MixinWeightedItemStack {
	@Final
	@Shadow(remap=false)
	float dropChance;
	
	@Final
	@Shadow(remap=false)
	ItemStack stack;
	
	/**
	 *
	 * @param entity
	 * @param slot
	 * @param cir
	 */
	@Inject(at = @At("RETURN"), method="apply", remap = false)
	public void compatemon$allowApothItemToDrop(LivingEntity entity, EquipmentSlot slot, CallbackInfo cir)
	{
		if (this.dropChance >= 0 && entity instanceof PokemonEntity mob) {
			mob.setDropChance(slot, this.dropChance);
		}
	}
}
