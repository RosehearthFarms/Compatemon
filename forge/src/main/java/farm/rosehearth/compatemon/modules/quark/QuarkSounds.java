package farm.rosehearth.compatemon.modules.quark;

import com.google.common.collect.Lists;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import org.violetmoon.quark.base.Quark;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zeta.event.load.ZRegister;

import java.util.List;
public class QuarkSounds {
	private static final List<SoundEvent> REGISTRY_DEFERENCE = Lists.newArrayList();
	public static final SoundEvent AMBIENT_DRIPS = register("ambient.drips");
	public static final SoundEvent AMBIENT_OCEAN = register("ambient.ocean");
	public static final SoundEvent AMBIENT_RAIN = register("ambient.rain");
	public static final SoundEvent AMBIENT_WIND = register("ambient.wind");
	public static final SoundEvent AMBIENT_FIRE = register("ambient.fire");
	public static final SoundEvent AMBIENT_CLOCK = register("ambient.clock");
	public static final SoundEvent AMBIENT_CRICKETS = register("ambient.crickets");
	public static final SoundEvent AMBIENT_CHATTER = register("ambient.chatter");
	
	@LoadEvent
	public static void start(ZRegister e) {
		for(SoundEvent event : REGISTRY_DEFERENCE)
			Quark.ZETA.registry.register(event, event.getLocation(), Registries.SOUND_EVENT);
		REGISTRY_DEFERENCE.clear();
	}
	
	public static SoundEvent register(String name) {
		SoundEvent event = SoundEvent.createVariableRangeEvent(new ResourceLocation(Quark.MOD_ID, name));
		REGISTRY_DEFERENCE.add(event);
		return event;
	}
}
