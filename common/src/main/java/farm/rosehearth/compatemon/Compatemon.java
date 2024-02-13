package farm.rosehearth.compatemon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import farm.rosehearth.compatemon.config.Configuration;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig;
import farm.rosehearth.compatemon.modules.quark.QuarkConfig;
import farm.rosehearth.compatemon.modules.sophisticatedcore.SophisticatedConfig;
import farm.rosehearth.compatemon.utils.CompateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static farm.rosehearth.compatemon.utils.CompatemonDataKeys.*;


public class Compatemon {
	public static final String MODID = "compatemon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	//private static CompatemonCommonConfigModel config;
	
	public static CompatemonImplementation implementation;
	public static File configDir;
	public static Configuration config;
	public static Configuration apothConfig;
	public static Configuration pehkuiConfig;
	public static Configuration quarkConfig;
	public static final Map<String,Configuration> configs = new HashMap<>();
	private static final ArrayList<String> modsToConfigure = new ArrayList<String>();
	private static final Map<String, Boolean> modsToEnable = new HashMap<String, Boolean>();
	
	/**
	 * Add the MOD_IDs for each mod we add compatability for.
	 * Start with Fabric+Forge mods, then add modAPI specific ones after
	 */
	static {
		// Common
		modsToConfigure.add(MOD_ID_PEHKUI);
		// Forge
		modsToConfigure.add(MOD_ID_APOTHEOSIS);
		modsToConfigure.add(MOD_ID_QUARK);
		modsToConfigure.add(MOD_ID_SC);
		// Fabric
		// Quilt
	}
	
	/**
	 * Links the various modAPI implementations to the main mod. Allows for differing code.
	 * @param imp
	 */
	public static void preInitialize(CompatemonImplementation imp){
		implementation = imp;
		
		// Create the config file for Compatemon
		configDir = new File("config",MODID);
		config = new Configuration(new File(configDir, MODID + ".cfg"));
		config.setTitle("Compatemon Module Control");
		config.setComment("This file allows individual modules to be enabled or disabled.");
		
		// Adds each mod to the Compatemon config file
		for (String modID: modsToConfigure){
			
			boolean bEnabled = config.getBoolean("Enable " + modID.toUpperCase() + " Module", "general", implementation.isModInstalled(modID), "Enables the " + modID.toUpperCase() + " Module");
			modsToEnable.put(modID,implementation.isModInstalled(modID) && bEnabled);
			
		}
		
		if(config.hasChanged()) config.save();
	}
	
	/**
	 * Initializes Common code between modAPIs
	 */
	public static void init() {
		loadConfigs();
		CompatemonKotlin.INSTANCE.initialize();
		Compatemon.implementation.postCommonInitialization();
	}
	
	/**
	 * Loads the configs for each mod. Config files are located in the common group, even if a particular mod is
	 * specific to a particular modAPI
	 */
	private static void loadConfigs(){
		for(var m : modsToConfigure)
		{
			if(ShouldLoadMod(m)) {
				Configuration c = new Configuration(new File(configDir, m + ".cfg"));
				configs.put(m, c);
				
				switch(m){
					case MOD_ID_APOTHEOSIS:
						ApotheosisConfig.load(c);
						break;
					case MOD_ID_QUARK:
						QuarkConfig.load(c);
						break;
					case MOD_ID_PEHKUI:
						PehkuiConfig.load(c);
						break;
					case MOD_ID_SC:
						SophisticatedConfig.load(c);
						break;
				}
				if(c.hasChanged()) c.save();
			}
		}
		
		
	}
	
	public static boolean ShouldLoadMod(String mod_id){     return modsToEnable.get(mod_id);    }

	
}