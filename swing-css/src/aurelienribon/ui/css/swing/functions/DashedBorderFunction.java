package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Paint;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class DashedBorderFunction implements Function {
	@Override
	public String getName() {
		return "dashedborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Paint.class},
			{Paint.class, Number.class, Number.class},
			{Paint.class, Number.class, Number.class, Number.class, Boolean.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"paint"},
			{"paint", "length", "spacing"},
			{"paint", "thickness", "length", "spacing", "rounded"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.size() == 1) {
			Paint paint = (Paint) params.get(0);
			return BorderFactory.createDashedBorder(paint);
		}

		if (params.size() == 2) {
			Paint paint = (Paint) params.get(0);
			float length = (Float) params.get(1);
			float spacing = (Float) params.get(2);
			return BorderFactory.createDashedBorder(paint, length, spacing);
		}

		if (params.size() == 4) {
			Paint paint = (Paint) params.get(0);
			float thickness = (Float) params.get(1);
			float length = (Float) params.get(2);
			float spacing = (Float) params.get(3);
			boolean rounded = (Boolean) params.get(4);
			return BorderFactory.createDashedBorder(paint, thickness, length, spacing, rounded);
		}

		assert false;
		return null;
	}
}
