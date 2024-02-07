package farm.rosehearth.compatemon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import farm.rosehearth.compatemon.config.Configuration;
import farm.rosehearth.compatemon.modules.apotheosis.ApotheosisConfig;
import farm.rosehearth.compatemon.modules.pehkui.PehkuiConfig;
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
	private static final ArrayList<String> modsToConfigure = new ArrayList<String>();
	private static final Map<String, Boolean> modsToEnable = new HashMap<String, Boolean>();
	
	/**
	 *
	 */
	static {
		// Add each modid needed for compatability here
		modsToConfigure.add(MOD_ID_APOTHEOSIS);
		modsToConfigure.add(MOD_ID_PEHKUI);
	}
	
	/**
	 * Links the various version implementations to the main mod. Allows for differing code.
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
			boolean bEnabled = config.getBoolean("Enable " + modID.toUpperCase() + " Module", "general", true, "Enables the " + modID.toUpperCase() + " Module");
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

	}
	
	/**
	 * Loads the configs for each mod. Config files are located in the common group, even if a particular mod is
	 * specific to a particular modAPI
	 */
	private static void loadConfigs(){
		if(ShouldLoadMod(MOD_ID_PEHKUI)) {
			pehkuiConfig = new Configuration(new File(configDir, MOD_ID_PEHKUI + ".cfg"));
			PehkuiConfig.load(pehkuiConfig);
			if(pehkuiConfig.hasChanged()) pehkuiConfig.save();
		}
		
		if(ShouldLoadMod(MOD_ID_APOTHEOSIS)) {
			apothConfig = new Configuration(new File(configDir, MOD_ID_APOTHEOSIS + ".cfg"));
			ApotheosisConfig.load(apothConfig);
			if(apothConfig.hasChanged()) apothConfig.save();
		}
	}
	
	public static boolean ShouldLoadMod(String mod_id){     return modsToEnable.get(mod_id);    }

	
}