package farm.rosehearth.cobblemon_sizes.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "cobblemon_sizes")
public class CobblemonSizesCommonConfigModel implements ConfigData {

    @ConfigEntry.Category("Wild Pokemon Aggression")
    @Comment("Do more aggressive Pokemon fight back when provoked?")
    public boolean do_pokemon_attack = true;

    @Comment("The minimum level a Pokemon needs to be to fight back when provoked.")
    public int minimum_attack_level = 5;

    @Comment("Pokemon that will always be aggressive")
    public String[] always_aggro = {
            "mankey",
            "primeape"
    };

    @Comment("Pokemon that will never be aggressive")
    public String[] never_aggro = {};

    @ConfigEntry.Category("Player Pokemon Defence")
    @Comment("Do player Pokemon defend their owners when they attack or are attacked by other mobs?")
    public boolean do_pokemon_defend_owner = true;


    @ConfigEntry.Category("Pokemon Damage and Effects")
    @Comment("The amount of damage a pokemon would do on contact if it had 0 ATK and Sp.ATK.")
    public float minimum_attack_damage = 1.0f;


}