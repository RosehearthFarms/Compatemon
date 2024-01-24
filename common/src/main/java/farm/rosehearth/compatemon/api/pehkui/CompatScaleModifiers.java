package farm.rosehearth.compatemon.api.pehkui;

import farm.rosehearth.compatemon.Compatemon;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import virtuoel.pehkui.api.*;
import net.minecraft.util.*;

class CompatScaleModifiers  {
    public static final ScaleModifier COBBLEMON_ATK_MULTIPIER = register("cobblemon_atk_multiplier", new TypedScaleModifier(() -> ScaleTypes.BASE));
    public static final ScaleModifier COBBLEMON_ATK_DIVISOR = register("cobblemon_atk_divisor", new TypedScaleModifier(() -> ScaleTypes.BASE));

    private static ScaleModifier  register(String path, ScaleModifier scaleModifier){
        return ScaleRegistries.register(
                ScaleRegistries.SCALE_MODIFIERS,new ResourceLocation(Compatemon.MODID,path),scaleModifier
        );
    };
}
