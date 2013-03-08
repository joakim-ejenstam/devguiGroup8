/**
 * 
 */
package controller;

import java.util.HashMap;
import java.util.prefs.Preferences;

/**
 * @author simon
 * This class is used to access the configuration parameter of the application.
 * It used to be a wrapper for properties, but as we can't store/update them in a jar-archive, we switched to 
 * Preferences, which should be used for that anyway...
 */
public class Config {
	
	// the only possible instance of this class (Singleton)
	private static Config instance;
	
	//the preferences object, nobody knows where java stores it...
	private Preferences prefs;
	private HashMap<String, String> defaults;
	
	/**
	 * Private constructor, so that other classes have to use the method {@link getInstance} to make it an Singleton.
	 * Loads first the default properties, than the application properties as saved last invocation.
	 */
	private Config() {
		this.loadDefaultProperties();
		this.prefs = Preferences.userNodeForPackage(Config.class);
	}


	/**
	 * public method to return an instance of the config class. By using this technique, 
	 * we can control how many instances are created and thus use it as a Singleton.
	 * @return the only possible instance of the {@link Config} object
	 */
	public static Config getInstance() {
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
	
	/**
	 * Loads default configuration values into an internal field.
	 * The data-source for the defaults could/should maybe be a file.
	 */
	private void loadDefaultProperties() {
		this.defaults = new HashMap<String, String>(5);
		
		this.defaults.put("windowHeight", "600");
		this.defaults.put("windowWidth", "500");
		this.defaults.put("windowXPos", "0");
		this.defaults.put("windowYPos", "0");
		this.defaults.put("locale", "Locale.ENGLISH");
		
		this.defaults.put("ui.primeColor1", "#5C5C5C");
		this.defaults.put("ui.primeColor2", "#696969");
		this.defaults.put("ui.primeColor3", "#A6A6A6");
		
		this.defaults.put("ui.secondaryColor1", "#030303");
		this.defaults.put("ui.secondaryColor2", "#696969");
		this.defaults.put("ui.secondaryColor3", "#919191");

	}
	

	/**
	 * Returns a configuration value of the application.
	 * (I really liked the handling of default values better at the properties...)
	 * @param key of the configuration value to return
	 * @return String the config value 
	 */
	public String getProp(String key) {
		return this.prefs.get(key, this.defaults.get(key));
	}
	
	/**
	 * Saves a new value to a config value of the application.
	 * @param key of the config attribute
	 * @param value of the config attribute
	 */
	public void setProp(String key, String value) {
		this.prefs.put(key, value);
	}
}
