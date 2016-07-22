package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MatteBorderFunction implements Function {
	@Override
	public String getName() {
		return "matteborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Integer.class, Integer.class, Integer.class, Integer.class, Color.class},
			{Integer.class, Integer.class, Integer.class, Integer.class, Icon.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"top", "left", "bottom", "up", "color"},
			{"top", "left", "bottom", "up", "tileIcon"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		int top = (Integer) params.get(0);
		int left = (Integer) params.get(1);
		int bottom = (Integer) params.get(2);
		int right = (Integer) params.get(3);

		if (params.get(4) instanceof Color) {
			Color color = (Color) params.get(4);
			return BorderFactory.createMatteBorder(top, left, bottom, right, color);
		}

		if (params.get(4) instanceof Icon) {
			Icon tileIcon = (Icon) params.get(4);
			return BorderFactory.createMatteBorder(top, left, bottom, right, tileIcon);
		}

		assert false;
		return null;
	}
}
