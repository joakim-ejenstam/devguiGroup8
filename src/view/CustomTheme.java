package view;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

public class CustomTheme extends OceanTheme {

	// Color theme
	
	private static final String THEME = "data" + File.separator
			+ "theme.properties";
	
	private final ColorUIResource primary1;
	private final ColorUIResource primary2;
	private final ColorUIResource primary3;
	
	private final ColorUIResource secondary1;
	private final ColorUIResource secondary2;
	private final ColorUIResource secondary3;
	
	private final Properties prop;
	
	public CustomTheme(){
		this.prop = this.loadColorProperties();
		this.primary1 = new ColorUIResource(Color.decode(prop.getProperty("ui.primeColor1")));
		this.primary2 = new ColorUIResource(Color.decode(prop.getProperty("ui.primeColor2")));
		this.primary3 =new ColorUIResource(Color.decode(prop.getProperty("ui.primeColor3")));
		
		this.secondary1 = new ColorUIResource(Color.decode(prop.getProperty("ui.secondaryColor1")));
		this.secondary2 = new ColorUIResource(Color.decode(prop.getProperty("ui.secondaryColor2")));
		this.secondary3 =new ColorUIResource(Color.decode(prop.getProperty("ui.secondaryColor3")));
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
		Properties colorTheme = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(THEME);
			colorTheme.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorTheme;
	}


/*
private static final String THEME = "data" + File.separator
+ "theme.properties";
private final ColorUIResource primary1 = new ColorUIResource(50, 255, 0);
private final ColorUIResource primary2 = new ColorUIResource(0, 255, 255);
private final ColorUIResource primary3 = new ColorUIResource(0, 0, 0);

protected ColorUIResource getPrimary1() {
  return primary1;
}

protected ColorUIResource getPrimary2() {
  return primary2;
}

protected ColorUIResource getPrimary3() {
  return primary3;
}
*/
}