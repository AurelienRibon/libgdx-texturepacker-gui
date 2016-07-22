package aurelienribon.ui.css.swing;

import aurelienribon.ui.css.StyleException;
import java.awt.Font;
import javax.swing.AbstractButton;
import javax.swing.SwingConstants;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SwingUtils {
	public static int asHAlign(String val) {
		if (val.equals("leading")) return SwingConstants.LEADING;
		else if (val.equals("trailing")) return SwingConstants.TRAILING;
		else if (val.equals("left")) return SwingConstants.LEFT;
		else if (val.equals("right")) return SwingConstants.RIGHT;
		else if (val.equals("center")) return SwingConstants.CENTER;

		assert false;
		return -1;
	}

	public static int asVAlign(String val) {
		if (val.equals("bottom")) return AbstractButton.BOTTOM;
		else if (val.equals("top")) return AbstractButton.TOP;
		else if (val.equals("center")) return AbstractButton.CENTER;
		else throw StyleException.forKeyword(val, "bottom", "top", "center");
	}

	public static int asFontStyle(String val) {
		if (val.equals("plain")) return Font.PLAIN;
		else if (val.equals("italic")) return Font.ITALIC;
		else if (val.equals("bold")) return Font.BOLD;
		else if (val.equals("italicbold")) return Font.BOLD | Font.ITALIC;
		else throw StyleException.forKeyword(val, "plain", "italic", "bold", "italicbold");
	}
}
