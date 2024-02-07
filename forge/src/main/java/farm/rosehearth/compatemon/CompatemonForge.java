package farm.rosehearth.compatemon;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;


@Mod(Compatemon.MODID)
@Mod.EventBusSubscriber()
public class CompatemonForge implements CompatemonImplementation{

    public CompatemonForge(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }
    
    /**
     * Initializes the mod
     * @param event
     */
    @SubscribeEvent
    public  void init(FMLCommonSetupEvent event){
        Compatemon.LOGGER.debug("In CobblemonSizesForge.init()");
        Compatemon.preInitialize(this);
        Compatemon.init();
    }
    
    /**
     *
     * @return FORGE
     */
    @NotNull
    @Override
    public ModAPI getModAPI() {
        return ModAPI.FORGE;
    }
    
    /**
     *
     * @param modID
     * @return true if the modID is installed
     */
    @Override
    public boolean isModInstalled(@NotNull String modID) {
        return ModList.get().isLoaded(modID);
    }
    
}
