package aurelienribon.ui.components;

import aurelienribon.ui.components.processors.JComponentProcessor;
import aurelienribon.ui.css.Style;
import javax.swing.JComponent;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ArStyle {

	/**
	 * Registers all the properties, functions and processors of the Arui
	 * backend to the CSS engine. Requires the Swing backend to be initialized.
	 */
	public static void init() {
		Style.registerProperty(ArProperties.fill);
		Style.registerProperty(ArProperties.stroke);
		Style.registerProperty(ArProperties.strokeThickness);
		Style.registerProperty(ArProperties.corderRadius);
		Style.registerProperty(ArProperties.borderStroke);
		Style.registerProperty(ArProperties.borderThickness);
		Style.registerProperty(ArProperties.borderHeaderStroke);
		Style.registerProperty(ArProperties.borderHeaderThickness);
		Style.registerProperty(ArProperties.borderHeaderFill);
		Style.registerProperty(ArProperties.borderTitle);
		Style.registerProperty(ArProperties.borderIcon);
		Style.registerProperty(ArProperties.borderFont);
		Style.registerProperty(ArProperties.borderForeground);
		Style.registerProperty(ArProperties.borderMargin);

		Style.registerFunction(ArFunctions.groupBorder);

		Style.registerProcessor(JComponent.class, new JComponentProcessor());
		Style.registerProcessor(Button.class, new Button.Processor());
		Style.registerProcessor(TabPanel.class, new TabPanel.Processor());
		Style.registerProcessor(TabPanelHeaderPanel.class, new TabPanelHeaderPanel.Processor());
		Style.registerProcessor(TabPanelHeaderSubPanel.class, new TabPanelHeaderSubPanel.Processor());
		Style.registerProcessor(PaintedPanel.class, new PaintedPanel.Processor());
	}
}
