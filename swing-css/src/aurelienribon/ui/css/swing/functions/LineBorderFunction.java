package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class LineBorderFunction implements Function {
	@Override
	public String getName() {
		return "lineborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Color.class},
			{Color.class, Integer.class},
			{Color.class, Integer.class, Boolean.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"color"},
			{"color", "thickness"},
			{"color", "thickness", "rounded"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.size() == 1) {
			Color color = (Color) params.get(0);
			return BorderFactory.createLineBorder(color);
		}

		if (params.size() == 2) {
			Color color = (Color) params.get(0);
			int thickness = (Integer) params.get(1);
			return BorderFactory.createLineBorder(color, thickness);
		}

		if (params.size() == 2) {
			Color color = (Color) params.get(0);
			int thickness = (Integer) params.get(1);
			boolean rounded = (Boolean) params.get(2);
			return BorderFactory.createLineBorder(color, thickness, rounded);
		}

		assert false;
		return null;
	}
}
