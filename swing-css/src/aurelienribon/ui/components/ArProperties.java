package aurelienribon.ui.components;

import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.primitives.FunctionProperty;
import aurelienribon.ui.css.primitives.SingleParamProperty;
import aurelienribon.ui.css.swing.SwingFunctions;
import java.awt.Color;
import java.awt.Paint;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface ArProperties {
	public static final Property fill = new SingleParamProperty("-ar-fill", Paint.class, "paint");
	public static final Property stroke = new SingleParamProperty("-ar-stroke", Paint.class, "paint");
	public static final Property strokeThickness = new SingleParamProperty("-ar-strokethickness", Integer.class, "thickness");

	public static final Property corderRadius = new SingleParamProperty("-ar-cornerradius", Integer.class, "radius");

	public static final Property borderStroke = new SingleParamProperty("-ar-border-stroke", Paint.class, "stroke");
	public static final Property borderThickness = new FunctionProperty("-ar-border-thickness", SwingFunctions.insets, "thickness");
	public static final Property borderHeaderStroke = new SingleParamProperty("-ar-border-header-stroke", Paint.class, "stroke");
	public static final Property borderHeaderThickness = new FunctionProperty("-ar-border-header-thickness", SwingFunctions.insets, "thickness");
	public static final Property borderHeaderFill = new SingleParamProperty("-ar-border-header-fill", Paint.class, "paint");
	public static final Property borderTitle = new SingleParamProperty("-ar-border-title", String.class, "text");
	public static final Property borderIcon = new FunctionProperty("-ar-border-icon", SwingFunctions.icon, "icon");
	public static final Property borderFont = new FunctionProperty("-ar-border-font", SwingFunctions.font, "font");
	public static final Property borderForeground = new SingleParamProperty("-ar-border-foreground", Color.class, "color");
	public static final Property borderMargin = new FunctionProperty("-ar-border-margin", SwingFunctions.insets, "insets");
}
