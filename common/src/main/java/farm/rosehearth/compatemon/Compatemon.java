package farm.rosehearth.compatemon;

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty;
import farm.rosehearth.compatemon.config.CompatemonCommonConfigModel;
import farm.rosehearth.compatemon.api.cobblemon.properties.SizeScaleProperty;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Compatemon {
	public static final String MODID = "compatemon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static CompatemonCommonConfigModel config;
	private static Random r;

	public static CompatemonCommonConfigModel config() {
		return config;
	}

	public static void init() {
		LOGGER.debug("In Compatemon.init()");
		AutoConfig.register(CompatemonCommonConfigModel.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CompatemonCommonConfigModel.class).getConfig();
		r = new Random();
		//CompatemonKotlin.INSTANCE.initialize();
		CustomPokemonProperty.Companion.register(SizeScaleProperty.INSTANCE);

	}

	public static float getSizeModifier(){
		if(!Compatemon.config().size_do_unprovided) return 1.0f;
		double new_size = Compatemon.config().size_scale +
				(r.nextGaussian() * Compatemon.config.size_dev);

		new_size = new_size > Compatemon.config().size_max_percentage ?
				Compatemon.config().size_max_percentage :
				new_size;
		new_size = new_size < Compatemon.config().size_min_percentage ?
				Compatemon.config().size_min_percentage :
				new_size;
		new_size = new_size <= 0.00 ? 0.25 : new_size;

		return BigDecimal.valueOf(new_size).setScale(2, RoundingMode.UP).floatValue();
	}
	public static float getWeightModifier(){
		if(!Compatemon.config().weight_do_unprovided) return 1.0f;
		double new_weight = Compatemon.config().weight_scale +
				(r.nextGaussian() * Compatemon.config.weight_dev);

		new_weight = new_weight > Compatemon.config().weight_max_percentage ?
				Compatemon.config().weight_max_percentage :
				new_weight;
		new_weight = new_weight < Compatemon.config().weight_min_percentage ?
				Compatemon.config().weight_min_percentage :
				new_weight;
		new_weight = new_weight <= 0.00 ? 0.25 : new_weight;
		return BigDecimal.valueOf(new_weight).setScale(2, RoundingMode.UP).floatValue() ;
	}
}