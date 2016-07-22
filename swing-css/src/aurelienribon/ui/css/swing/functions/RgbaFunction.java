package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class RgbaFunction implements Function {
	@Override
	public String getName() {
		return "rgba";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Integer.class, Integer.class, Integer.class, Float.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"r", "g", "b", "a"}
		};
	}

	@Override
	public Class getReturn() {
		return Color.class;
	}

	@Override
	public Object process(List<Object> params) {
		int r = (Integer) params.get(0);
		int g = (Integer) params.get(1);
		int b = (Integer) params.get(2);
		int a = (int) (((Float) params.get(3)) * 255);
		return new Color(r, g, b, a);
	}
}
