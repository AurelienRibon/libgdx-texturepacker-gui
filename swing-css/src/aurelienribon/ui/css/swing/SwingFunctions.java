package aurelienribon.ui.css.swing;

import aurelienribon.ui.css.Function;
import aurelienribon.ui.css.swing.functions.*;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface SwingFunctions {
	// General
	public static Function font = new FontFunction();
	public static Function icon = new IconFunction();
	public static Function insets = new InsetsFunction();
	public static Function url = new UrlFunction();

	// Colors + paints
	public static Function rgb = new RgbFunction();
	public static Function rgba = new RgbaFunction();
	public static Function linearGradient = new LinearGradientFunction();
	public static Function texture = new TextureFunction();

	// Borders
	public static Function bevelBorder = new BevelBorderFunction();
	public static Function compoundBorder = new CompoundBorderFunction();
	public static Function dashedBorder = new DashedBorderFunction();
	public static Function emptyBorder = new EmptyBorderFunction();
	public static Function etchedBorder = new EtchedBorderFunction();
	public static Function lineBorder = new LineBorderFunction();
	public static Function matteBorder = new MatteBorderFunction();
	public static Function titledBorder = new TitledBorderFunction();
}
