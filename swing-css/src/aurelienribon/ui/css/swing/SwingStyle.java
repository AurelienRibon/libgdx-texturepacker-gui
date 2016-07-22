package aurelienribon.ui.css.swing;

import aurelienribon.ui.css.*;
import aurelienribon.ui.css.swing.processors.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SwingStyle {
	private static boolean isInitialized = false;

	/**
	 * Registers all the properties, functions and processors of the Swing
	 * backend to the CSS engine. Also registers a children accessor used
	 * to return every children of an AWT Container, and a param converter
	 * used to convert undefined colors into AWT Color objects.
	 */
	public static void init() {
		if (isInitialized) return;
		isInitialized = true;

		Style.registerProperty(SwingProperties.background);
		Style.registerProperty(SwingProperties.foreground);
		Style.registerProperty(SwingProperties.visible);
		Style.registerProperty(SwingProperties.enabled);
		Style.registerProperty(SwingProperties.focusable);
		Style.registerProperty(SwingProperties.opaque);
		Style.registerProperty(SwingProperties.editable);
		Style.registerProperty(SwingProperties.lineWrap);
		Style.registerProperty(SwingProperties.wrapStyleWord);
		Style.registerProperty(SwingProperties.tabSize);
		Style.registerProperty(SwingProperties.caretPosition);
		Style.registerProperty(SwingProperties.tooltip);
		Style.registerProperty(SwingProperties.text);
		Style.registerProperty(SwingProperties.horizAlign);
		Style.registerProperty(SwingProperties.vertAlign);
		Style.registerProperty(SwingProperties.margin);
		Style.registerProperty(SwingProperties.font);
		Style.registerProperty(SwingProperties.icon);
		Style.registerProperty(SwingProperties.border);
		Style.registerProperty(SwingProperties.borderPainted);
		Style.registerProperty(SwingProperties.contentAreaFilled);
		Style.registerProperty(SwingProperties.fontFamily);
		Style.registerProperty(SwingProperties.fontStyle);
		Style.registerProperty(SwingProperties.fontSize);

		Style.registerFunction(SwingFunctions.url);
		Style.registerFunction(SwingFunctions.font);
		Style.registerFunction(SwingFunctions.icon);
		Style.registerFunction(SwingFunctions.insets);
		Style.registerFunction(SwingFunctions.rgb);
		Style.registerFunction(SwingFunctions.rgba);
		Style.registerFunction(SwingFunctions.linearGradient);
		Style.registerFunction(SwingFunctions.texture);
		Style.registerFunction(SwingFunctions.bevelBorder);
		Style.registerFunction(SwingFunctions.compoundBorder);
		Style.registerFunction(SwingFunctions.dashedBorder);
		Style.registerFunction(SwingFunctions.emptyBorder);
		Style.registerFunction(SwingFunctions.etchedBorder);
		Style.registerFunction(SwingFunctions.lineBorder);
		Style.registerFunction(SwingFunctions.matteBorder);
		Style.registerFunction(SwingFunctions.titledBorder);

		Style.registerProcessor(Component.class, new ComponentProcessor());
		Style.registerProcessor(JComponent.class, new JComponentProcessor());
		Style.registerProcessor(AbstractButton.class, new AbstractButtonProcessor());
		Style.registerProcessor(JLabel.class, new JLabelProcessor());
		Style.registerProcessor(JTextComponent.class,new JTextComponentProcessor());
		Style.registerProcessor(JTextArea.class, new JTextAreaProcessor());

		Style.registerDeclarationSetManager(Component.class, new SwingDeclarationSetManager());

		Style.registerChildrenAccessor(Container.class, new ChildrenAccessor<Container>() {
			@Override public List<?> getChildren(Container target) {
				return Arrays.asList(target.getComponents());
			}
		});

		Style.setParamConverter(new ParamConverter() {
			@Override public Object convertColor(int argb) {
				return new Color(argb, true);
			}

			@Override public Class getColorClass() {
				return Color.class;
			}
		});
	}
}
