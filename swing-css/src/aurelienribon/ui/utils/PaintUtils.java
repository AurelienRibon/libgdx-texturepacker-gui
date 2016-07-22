package aurelienribon.ui.utils;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.geom.Point2D;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PaintUtils {
	public static int getBrightness(Color c) {
		if (c == null) return -1;
		return (int) Math.sqrt(
			c.getRed() * c.getRed() * .241 +
			c.getGreen() * c.getGreen() * .691 +
			c.getBlue() * c.getBlue() * .068);
	}

	public static int getBrightness(Paint p) {
		if (p == null) return -1;
		if (p instanceof Color) return getBrightness((Color) p);

		if (p instanceof MultipleGradientPaint) {
			MultipleGradientPaint gp = (MultipleGradientPaint) p;
			int b = 128; for (Color c : gp.getColors()) b += getBrightness(c);
			return b / gp.getColors().length;
		}

		assert false;
		return -1;
	}

	public static Paint buildPaint(Paint p, double x, double y, double w, double h) {
		if (p instanceof Color) return p;

		if (p instanceof LinearGradientPaint) {
			LinearGradientPaint gp = (LinearGradientPaint) p;
			double x1 = gp.getStartPoint().getX() * w + x;
			double y1 = gp.getStartPoint().getY() * h + y;
			double x2 = gp.getEndPoint().getX() * w + x;
			double y2 = gp.getEndPoint().getY() * h + y;
			return new LinearGradientPaint(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2), gp.getFractions(), gp.getColors());
		}

		assert false;
		return null;
	}
}
