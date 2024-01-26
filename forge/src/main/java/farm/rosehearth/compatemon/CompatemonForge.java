package farm.rosehearth.compatemon;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Compatemon.MODID)
@Mod.EventBusSubscriber()
public class CompatemonForge {

    public CompatemonForge(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }
    @SubscribeEvent
    public  void init(FMLCommonSetupEvent event){

        Compatemon.LOGGER.debug("In CobblemonSizesForge.init()");
        Compatemon.init();
    }
   
}
