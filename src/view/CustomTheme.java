package view;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

import controller.Config;

public class CustomTheme extends OceanTheme {

	// Color theme
	
	private static final String THEME = "theme.properties";
	
	private final ColorUIResource primary1;
	private final ColorUIResource primary2;
	private final ColorUIResource primary3;
	
	private final ColorUIResource secondary1;
	private final ColorUIResource secondary2;
	private final ColorUIResource secondary3;
	
	private ResourceBundle rb = null;
//	private final Properties prop;
	
	public CustomTheme(Config cf){
//		this.prop = this.loadColorProperties();
		this.primary1 = new ColorUIResource(Color.decode(cf.getProp("ui.primeColor1")));
		this.primary2 = new ColorUIResource(Color.decode(cf.getProp("ui.primeColor2")));
		this.primary3 =new ColorUIResource(Color.decode(cf.getProp("ui.primeColor3")));
		
		this.secondary1 = new ColorUIResource(Color.decode(cf.getProp("ui.secondaryColor1")));
		this.secondary2 = new ColorUIResource(Color.decode(cf.getProp("ui.secondaryColor2")));
		this.secondary3 =new ColorUIResource(Color.decode(cf.getProp("ui.secondaryColor3")));
	}

	protected ColorUIResource getPrimary1() {
		return primary1;
	}

	protected ColorUIResource getPrimary2() {
		return primary2;
	}

	protected ColorUIResource getPrimary3() {
		return primary3;
	}

	protected ColorUIResource getSecondary1() {
		return secondary1;
	}

	protected ColorUIResource getSecondary2() {
		return secondary2;
	}

	protected ColorUIResource getSecondary3() {
		return secondary3;
	}
	
	/**
	 * Loads the color configuration
	 */
	private Properties loadColorProperties() {
		URL path = ClassLoader.getSystemResource(THEME);
		
		Properties colorTheme = new Properties();
		try {
			System.out.println("p: "+THEME + "-" );
			colorTheme.load(new FileInputStream(path.getFile()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return colorTheme;
	}
}
