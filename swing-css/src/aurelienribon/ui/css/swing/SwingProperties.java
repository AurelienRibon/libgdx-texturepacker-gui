package aurelienribon.ui.css.swing;

import aurelienribon.ui.css.*;
import aurelienribon.ui.css.primitives.FunctionProperty;
import aurelienribon.ui.css.primitives.SingleParamProperty;
import java.awt.Color;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface SwingProperties {
	public static final Property background = new SingleParamProperty("-swing-background", Color.class, "color");
	public static final Property foreground = new SingleParamProperty("-swing-foreground", Color.class, "color");

	public static final Property visible = new SingleParamProperty("-swing-visible", Boolean.class, "b");
	public static final Property enabled = new SingleParamProperty("-swing-enabled", Boolean.class, "b");
	public static final Property focusable = new SingleParamProperty("-swing-focusable", Boolean.class, "b");
	public static final Property opaque = new SingleParamProperty("-swing-opaque", Boolean.class, "b");
	public static final Property editable = new SingleParamProperty("-swing-editable", Boolean.class, "b");
	public static final Property lineWrap = new SingleParamProperty("-swing-linewrap", Boolean.class, "b");
	public static final Property wrapStyleWord = new SingleParamProperty("-swing-wrapstyleword", Boolean.class, "b");

	public static final Property tabSize = new SingleParamProperty("-swing-tabsize", Integer.class, "size");
	public static final Property caretPosition = new SingleParamProperty("-swing-caretposition", Integer.class, "pos");

	public static final Property tooltip = new SingleParamProperty("-swing-tooltiptext", String.class, "tooltip");
	public static final Property text = new SingleParamProperty("-swing-text", String.class, "text");
	public static final Property horizAlign = new SingleParamProperty("-swing-horizontalalignment", String.class, "halign");
	public static final Property vertAlign = new SingleParamProperty("-swing-verticalalignment", String.class, "valign");

	public static final Property border = new SingleParamProperty("-swing-border", Border.class, "border");

	public static final Property margin = new FunctionProperty("-swing-margin", SwingFunctions.insets, "insets");
	public static final Property font = new FunctionProperty("-swing-font", SwingFunctions.font, "font");
	public static final Property icon = new FunctionProperty("-swing-icon", SwingFunctions.icon, "icon");

	public static final Property borderPainted = new SingleParamProperty("-swing-borderpainted", Boolean.class, "b");
	public static final Property contentAreaFilled = new SingleParamProperty("-swing-contentareafilled", Boolean.class, "b");

	// -------------------------------------------------------------------------

	public static final Property fontFamily = new SingleParamProperty("-swing-font-family", String.class, "family");
	public static final Property fontStyle = new SingleParamProperty("-swing-font-style", String.class, "style");
	public static final Property fontSize = new SingleParamProperty("-swing-font-size", Integer.class, "size");
}
