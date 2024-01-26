package farm.rosehearth.compatemon;

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty;
import farm.rosehearth.compatemon.config.CompatemonCommonConfigModel;
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

	public static CompatemonCommonConfigModel config() {
		return config;
	}

	public static void init() {
		LOGGER.debug("In Compatemon.init()");
		AutoConfig.register(CompatemonCommonConfigModel.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CompatemonCommonConfigModel.class).getConfig();
		CompatemonKotlin.INSTANCE.initialize();

	}

}