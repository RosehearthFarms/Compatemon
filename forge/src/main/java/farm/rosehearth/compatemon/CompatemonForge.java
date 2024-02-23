package farm.rosehearth.compatemon;

import farm.rosehearth.compatemon.util.RunnableReloader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
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
        MinecraftForge.EVENT_BUS.addListener(this::reloads);
        MinecraftForge.EVENT_BUS.addListener(this::reloadConfigs);
    }
    
    /**
     * Initializes the mod
     * @param event
     */
    @SubscribeEvent
    public  void init(FMLCommonSetupEvent event){
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
    
    @Override
    public void postCommonInitialization() {
        CompatemonForgeKotlin.INSTANCE.postCommonInit();
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void reloads(AddReloadListenerEvent e) {
        e.addListener(RunnableReloader.of(() -> MinecraftForge.EVENT_BUS.post(new CompatemonReloadEvent())));
        Compatemon.loadConfigs(false);
    }
    
    @SubscribeEvent
    public void reloadConfigs(CompatemonReloadEvent event){
    }
    
    @Override
    public void registerEvents() {
        // In Forge, Subscribe Events are handled using the annotation @SubscribeEvent.
        // In Fabric, call them in this function.
    }
    
    @NotNull
    @Override
    public String persistentDataKey() {
        return "ForgeData";
    }
    
    public static class CompatemonReloadEvent extends Event {}
}
