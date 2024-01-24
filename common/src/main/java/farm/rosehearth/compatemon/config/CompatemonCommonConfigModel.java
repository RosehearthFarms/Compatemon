package farm.rosehearth.compatemon.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Config(name = "compatemon")
public class CompatemonCommonConfigModel implements ConfigData {

    @ConfigEntry.Category("Debug")
    @Comment("\nShould jsons be generated for all loaded species? Default values will be loaded when not provided.")
    public boolean do_generate_pokemon_json = false;

@ConfigEntry.Category("Default Size Variation")
    @Comment("\nIf not provided in the species jsons, should Pokemon sizes be randomized?")
    public boolean size_do_unprovided = true;
    @Comment("The standard deviation of unprovided pokemon sizes")
    public float size_dev = .20f;
    @Comment("The average size of most pokemon. Change to modify the scaling across the board!")
    public float size_scale = 1.0f;
    @Comment("The max size percentage for unprovided pokemon sizes, where 1.0 is standard size.")
    public float size_max_percentage = 1.5f;
    @Comment("The min size percentage for unprovided pokemon sizes, where 1.0 is standard size.")
    public float size_min_percentage = 0.25f;

@ConfigEntry.Category("Default Weight Variation")
    @Comment("If not provided in the species jsons, should Pokemon weights be randomized?")
    public boolean weight_do_unprovided = true;
    @Comment("The standard deviation of unprovided pokemon sizes")
    public float weight_dev = //BigDecimal.valueOf(
            0.2f
   // ).setScale(2).floatValue()
            ;
    @Comment("The average size of most pokemon. Change to modify the scaling across the board!")
    public float weight_scale = 1.0f;
    @Comment("The max size percentage for unprovided pokemon sizes, where 1.0 is standard size.")
    public float weight_max_percentage = 1.5f;
    @Comment("The min size percentage for unprovided pokemon sizes, where 1.0 is standard size.")
    public float weight_min_percentage = 0.25f;

@ConfigEntry.Category("\n\nAttribute Scaling")
    @Comment("\nShould a pokemon's atk and sp atk scale with size?")
        public boolean size_do_atk_scaling = true;
    @Comment("Should a pokemon's atk and sp atk scale with weight?")
        public boolean weight_do_atk_scaling = true;
    @Comment("\nShould a pokemon's def and sp def scale with size?")
        public boolean size_do_def_scaling = true;
    @Comment("Should a pokemon's def and sp def scale with weight?")
        public boolean weight_do_def_scaling = true;
    @Comment("\nShould a pokemon's spd scale with size?")
        public boolean size_do_spd_scaling = true;
    @Comment("Should a pokemon's spd scale with weight?")
        public boolean weight_do_spd_scaling = true;
    @Comment("\nShould a pokemon's hp scale with size?")
        public boolean size_do_hp_scaling = true;
    @Comment("Should a pokemon's hp scale with weight?")
        public boolean weight_do_hp_scaling = true;



}