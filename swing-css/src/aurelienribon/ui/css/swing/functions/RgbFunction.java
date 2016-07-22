package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class RgbFunction implements Function {
	@Override
	public String getName() {
		return "rgb";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Integer.class, Integer.class, Integer.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"r", "g", "b"}
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
		return new Color(r, g, b);
	}
}
