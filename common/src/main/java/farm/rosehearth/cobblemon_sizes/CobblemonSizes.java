package farm.rosehearth.cobblemon_sizes;

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty;
import farm.rosehearth.cobblemon_sizes.config.CobblemonSizesCommonConfigModel;
import farm.rosehearth.cobblemon_sizes.api.cobblemon.properties.SizeScaleProperty;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.data.CobblemonDataProvider;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.util.TriConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mojang.logging.LogUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class CobblemonSizes {
	public static final String MODID = "cobblemon_sizes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static CobblemonSizesCommonConfigModel config;
	private static Random r;

	public static CobblemonSizesCommonConfigModel config() {
		return config;
	}

	public static void init() {
		LOGGER.debug("In CobblemonSizes.init()");
		AutoConfig.register(CobblemonSizesCommonConfigModel.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CobblemonSizesCommonConfigModel.class).getConfig();
		r = new Random();
		CobblemonSizesKotlin.INSTANCE.initialize();
		//CustomPokemonProperty.Companion.register(SizeScaleProperty.INSTANCE);

	}

	public static float getSizeModifier(){
		if(!CobblemonSizes.config().size_do_unprovided) return 1.0f;
		double new_size = CobblemonSizes.config().size_scale +
				(r.nextGaussian() * CobblemonSizes.config.size_dev);

		new_size = new_size > CobblemonSizes.config().size_max_percentage ?
				CobblemonSizes.config().size_max_percentage :
				new_size;
		new_size = new_size < CobblemonSizes.config().size_min_percentage ?
				CobblemonSizes.config().size_min_percentage :
				new_size;
		new_size = new_size <= 0.00 ? 0.25 : new_size;

		return BigDecimal.valueOf(new_size).setScale(2, RoundingMode.UP).floatValue();
	}
	public static float getWeightModifier(){
		if(!CobblemonSizes.config().weight_do_unprovided) return 1.0f;
		double new_weight = CobblemonSizes.config().weight_scale +
				(r.nextGaussian() * CobblemonSizes.config.weight_dev);

		new_weight = new_weight > CobblemonSizes.config().weight_max_percentage ?
				CobblemonSizes.config().weight_max_percentage :
				new_weight;
		new_weight = new_weight < CobblemonSizes.config().weight_min_percentage ?
				CobblemonSizes.config().weight_min_percentage :
				new_weight;
		new_weight = new_weight <= 0.00 ? 0.25 : new_weight;
		return BigDecimal.valueOf(new_weight).setScale(2, RoundingMode.UP).floatValue() ;
	}
}