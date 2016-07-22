package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Insets;
import java.util.List;
import javax.swing.Icon;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class InsetsFunction implements Function {
	@Override
	public String getName() {
		return "insets";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Integer.class, Integer.class, Integer.class, Integer.class},
			{Integer.class, Integer.class},
			{Integer.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"top", "left", "bottom", "right"},
			{"topBottom", "leftRight"},
			{"thickness"}
		};
	}

	@Override
	public Class getReturn() {
		return Insets.class;
	}

	@Override
	public Object process(List<Object> params) {
		int top, left, bottom, right;

		if (params.size() == 1) {
			top = left = bottom = right = (Integer) params.get(0);
			return new Insets(top, left, bottom, right);
		}

		if (params.size() == 2) {
			top = bottom = (Integer) params.get(0);
			left = right = (Integer) params.get(1);
			return new Insets(top, left, bottom, right);
		}

		if (params.size() == 4) {
			top = (Integer) params.get(0);
			left = (Integer) params.get(1);
			bottom = (Integer) params.get(2);
			right = (Integer) params.get(3);
			return new Insets(top, left, bottom, right);
		}

		assert false;
		return null;
	}
}
