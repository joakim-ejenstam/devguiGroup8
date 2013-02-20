/**
 * 
 */
package model;

import java.util.Locale;
import java.util.ResourceBundle;

import controller.Config;

/**
 * This class is the provider for the localizied texts.
 * We loud pass the resourcebundle directly to the views, but maybe we want to change
 * to a db as the language backend next version and then we just have to change it in one place: here.
 * Usage: if you want a localized text, just call {@link LocaliziedTexts#getText(String)}
 * if you want to change the locale, save it to the {@link Config} and then call {@link LocaliziedTexts#refreshTexts()}
 * @author simon
 *
 */
public class LocaliziedTexts {

	private Config config; //the Config for this application
	private Locale locale; //the current locale, read from the Config
	private ResourceBundle rb = null; //the ResourceBundle, holding all the texts
	
	/**
	 * Constructor, nothing fancy here.
	 * @param config the {@link Config} object that holds the application configuration
	 */
	public LocaliziedTexts(Config config) {
		this.config = config;
		loadLocale();
	}
	
	/**
	 * Call this method if the locale has changed. It will reload the resourcebundle with the new language.
	 * You can then call {@link LocaliziedTexts#getText()} to get a text in the new language.
	 */
	public void refreshTexts() {
		loadLocale();
	}
	
	/**
	 * This method returns a localized string for e.g. a Label or Button.
	 * At the moment we just call the resourcebundle, but we could call here google translate too. :)
	 * @param key the String of the value you want
	 * @return string the localized value for the specified key
	 */
	public String getText(String key) {
		return this.rb.getString(key);
	}
	
	/**
	 * private method to load the correct resource bundle, depending on the locale out of the config file.
	 */
	private void loadLocale() {
		// load the locale from the config
    	this.locale = new Locale(this.config.getProp("locale"));
    	// load language dependent text from the resource bundle
    	this.rb = ResourceBundle.getBundle("lang",this.locale); //TODO: path
	}

}
