package farm.rosehearth.compatemon.config;

import org.apache.logging.log4j.Logger;

public interface IConfig {
	
	String MOD_ID = null;
	
	void setup(String modID);
	
	void load(Configuration c);
	
}
