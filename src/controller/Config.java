/**
 * 
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author simon
 * This class is used to access the configuration parameter of the application.
 */
public class Config {
	
	// the only possible instance of this class (Singleton)
	private static Config instance;
	
	// file with default config values
	private static final String DEFAULT_PROPS = "data"+File.separator+"default_props.properties";
	// file with application config values
	private static final String APP_PROPS = "data"+File.separator+"app_props.properties";
	// default values
	Properties defaultProps;
	// values saved at the last invocation
	Properties appProps;
	
	/**
	 * Private constructor, so that other classes have to use the method {@link getInstance} to make it an Singleton.
	 * Loads first the default properties, than the application properties as saved last invocation.
	 * @throws IOException if config values couldn't be loaded
	 */
	private Config() throws IOException {
		this.loadDefaultProperties();
		this.loadApplicationProperties();
	}


	/**
	 * public method to return an instance of the config class. By using this technique, 
	 * we can control how many instances are created and thus use it as a Singleton.
	 * @return the only possible instance of the {@link Config} object
	 * @throws InstantiationException if the config values could not be loaded
	 */
	public static Config getInstance() throws InstantiationException {
		if(instance == null)
			try {
				instance = new Config();
			} catch (IOException e) {
				throw new InstantiationException("Cannot load config values from the properties file. "+e.getLocalizedMessage());
			}
		return instance;
	}
	
	
	/**
	 * Loads default configuration values from the property file.
	 * @throws IOException if file could not be read
	 */
	private void loadDefaultProperties() throws IOException {
		this.defaultProps = new Properties();
		FileInputStream in = new FileInputStream(DEFAULT_PROPS);
		defaultProps.load(in);
		in.close();
	}
	
	/**
	 * Loads configuration values as saved at the last invocation of the program 
	 * from the property file.
	 * @throws IOException if file could not be read
	 */
	private void loadApplicationProperties() {
		this.appProps = new Properties(this.defaultProps); //initialize with default props, in case 
		
		FileInputStream in = null;
		try {
			in = new FileInputStream(APP_PROPS);
			appProps.load(in);
			in.close();
		} catch (IOException e) {
			//doesn't matter, we'll just stick to the default config values, we already got. :)
		} finally { //Let's try to tidy up if just the appProps.load() failed
			if(in != null) {
				try {
					in.close();
				} catch (IOException e2) {
					// we can't help here anymore...
				}
			}
		}
	}
	
	public void saveApplicationProperties() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(APP_PROPS);
			this.appProps.store(out, "saved as program exited.");
			out.close();
		} catch (IOException e) {
			e.printStackTrace(); //Not the best handling, but the user will have to live with an unsaved config
		} finally { //Let's tidy up if appProps.stor() failed
			try {
				out.close();
			} catch (IOException e) {
				//We can't do anything here anymore...
			}
		}	
	}

	/**
	 * Returns a configuration value of the application.
	 * @param key of the configuration value to return
	 * @return String the config value 
	 */
	public String getProp(String key) {
		return this.appProps.getProperty(key);
	}
	
	/**
	 * Saves a new value to a config value of the application.
	 * @param key of the config attribute
	 * @param value of the config attribute
	 */
	public void setProp(String key, String value) {
		this.appProps.setProperty(key, value);
	}
	
	/**
	 * Returns a default configuration value of the application.
	 * This could e.g. be used for a "reset"-button in the user interface configuration.
	 * BTW as these are the default values, they can't be set'ed, but just get'ed.
	 * @param key of the configuration value to return
	 * @return String of the config value
	 */
	public String getDefaultProp(String key) {
		return this.defaultProps.getProperty(key);
	}
	
	

}
