package farm.rosehearth.compatemon.mixin;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
//import farm.rosehearth.compatemon.Compatemon;
import farm.rosehearth.compatemon.events.CompatemonEvents;
import farm.rosehearth.compatemon.events.entity.*;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pokemon.class)
abstract class MixinPokemonSaveLoadEvents {

    @Inject(method = "saveToNBT", at = @At("RETURN"), remap = false)
    private void addNBTSavingEvent(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> pokemon) {
        CompatemonEvents.POKEMON_NBT_SAVED.postThen(new PokemonNbtSavedEvent((Pokemon)((Object)this), nbt),savedEvent -> null,savedEvent -> {
            //Compatemon.LOGGER.debug("Injected properly into saveToNBT!");
            return null;
        });
    }
    @Inject(method = "loadFromNBT", at = @At("RETURN"), remap = false)
    private void addNBTLoadingEvent(CompoundTag nbt, CallbackInfoReturnable<Pokemon> pokemon) {
        CompatemonEvents.POKEMON_NBT_LOADED.post(new PokemonNbtLoadedEvent[]{new PokemonNbtLoadedEvent(pokemon.getReturnValue(), nbt)}, loadedEvent -> {
            //Compatemon.LOGGER.info("NBT Loaded!");
            return null;
        });
    }

    @Inject(method = "saveToJSON", at = @At("RETURN"), remap = false)
    private void addJSONSavingEvent(JsonObject json, CallbackInfoReturnable<JsonObject> pokemon) {
        CompatemonEvents.POKEMON_JSON_SAVED.postThen(new PokemonJsonSavedEvent((Pokemon)((Object)this), json),savedEvent -> null,savedEvent -> {
            //Compatemon.LOGGER.info("JSON Saved!");
            return null;
        });
    }
    @Inject(method = "loadFromJSON", at = @At("RETURN"), remap = false)
    private void addJSONLoadingEvent(JsonObject json, CallbackInfoReturnable<Pokemon> pokemon) {
        CompatemonEvents.POKEMON_JSON_LOADED.post(new PokemonJsonLoadedEvent[]{new PokemonJsonLoadedEvent(pokemon.getReturnValue(), json)}, loadedEvent -> {
            //Compatemon.LOGGER.info("JSON Loaded!");
            return null;
        });
    }

}
