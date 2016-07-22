package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class EtchedBorderFunction implements Function {
	@Override
	public String getName() {
		return "etchedborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{},
			{Integer.class},
			{Color.class, Color.class},
			{Integer.class, Color.class, Color.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{},
			{"type"},
			{"highlight", "shadow"},
			{"type", "highlight", "shadow"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.isEmpty()) {
			return BorderFactory.createEtchedBorder();
		}

		if (params.size() == 1) {
			int type = (Integer) params.get(0);
			return BorderFactory.createEtchedBorder(type);
		}

		if (params.size() == 2) {
			Color highlight = (Color) params.get(0);
			Color shadow = (Color) params.get(1);
			return BorderFactory.createEtchedBorder(highlight, shadow);
		}

		if (params.size() == 3) {
			int type = (Integer) params.get(0);
			Color highlight = (Color) params.get(1);
			Color shadow = (Color) params.get(2);
			return BorderFactory.createEtchedBorder(type, highlight, shadow);
		}

		assert false;
		return null;
	}
}
