package view;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

import controller.Config;

/**
 * Custom theme with chosen colors
 * @author Matteh
 *
 */
public class CustomTheme extends OceanTheme {

	private final ColorUIResource primary1;
	private final ColorUIResource primary2;
	private final ColorUIResource primary3;
	
	private final ColorUIResource secondary1;
	private final ColorUIResource secondary2;
	private final ColorUIResource secondary3;

	/**
	 * Constructor for the class
	 * @param cf Config to be used to load color resources
	 */
	public CustomTheme(Config cf){
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
}