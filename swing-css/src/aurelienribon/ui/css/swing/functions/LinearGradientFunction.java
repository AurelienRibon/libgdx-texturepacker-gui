package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class LinearGradientFunction implements Function {
	@Override
	public String getName() {
		return "lineargradient";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Number.class, Number.class, Number.class, Number.class, Number.class, Color.class, Number.class, Color.class},
			{Number.class, Number.class, Number.class, Number.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class},
			{Number.class, Number.class, Number.class, Number.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class},
			{Number.class, Number.class, Number.class, Number.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class, Number.class, Color.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"x1", "y1", "x2", "y2", "fraction1", "color1", "fraction2", "color2"},
			{"x1", "y1", "x2", "y2", "fraction1", "color1", "fraction2", "color2", "fraction3", "color3"},
			{"x1", "y1", "x2", "y2", "fraction1", "color1", "fraction2", "color2", "fraction3", "color3", "fraction4", "color4"},
			{"x1", "y1", "x2", "y2", "fraction1", "color1", "fraction2", "color2", "fraction3", "color3", "fraction4", "color4", "fraction5", "color5"}
		};
	}

	@Override
	public Class getReturn() {
		return LinearGradientPaint.class;
	}

	@Override
	public Object process(List<Object> params) {
		float x1 = ((Number) params.get(0)).floatValue();
		float y1 = ((Number) params.get(1)).floatValue();
		float x2 = ((Number) params.get(2)).floatValue();
		float y2 = ((Number) params.get(3)).floatValue();

		int cnt = (params.size() - 4) / 2;
		float[] fractions = new float[cnt];
		Color[] colors = new Color[cnt];

		for (int i = 0; i < cnt; i++) {
			fractions[i] = ((Number) params.get(4 + i * 2)).floatValue();
			colors[i] = (Color) params.get(5 + i * 2);
		}

		return new LinearGradientPaint(x1, y1, x2, y2, fractions, colors);
	}
}
