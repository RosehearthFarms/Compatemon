package farm.rosehearth.compatemon.mixin;
import com.cobblemon.mod.common.api.data.ShowdownIdentifiable;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.events.CompatemonEvents;
import farm.rosehearth.compatemon.events.entity.*;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pokemon.class)
abstract class MixinPokemonNBTEvents {


    @Inject(method = "saveToNBT", at = @At("RETURN"))
    private void addSavingEvent(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> pokemon) {

        CompatemonEvents.POKEMON_NBT_SAVED.postThen(new PokemonNbtSavedEvent((Pokemon)((Object)this), nbt),savedEvent -> null,savedEvent -> {
            Compatemon.LOGGER.info("NBT Saved!");
            return null;
        });
    }
    @Inject(method = "loadFromNBT", at = @At("RETURN"))
    private void addLoadingEvent(CompoundTag nbt, CallbackInfoReturnable<Pokemon> pokemon) {

        CompatemonEvents.POKEMON_NBT_LOADED.post(new PokemonNbtLoadedEvent[]{new PokemonNbtLoadedEvent(pokemon.getReturnValue(), nbt)}, loadedEvent -> {

            Compatemon.LOGGER.info("NBT Loaded!");
            return null;

        });
    }
}
