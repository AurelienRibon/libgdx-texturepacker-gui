package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SoftBevelBorderFunction implements Function {
	@Override
	public String getName() {
		return "softbevelborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{String.class},
			{String.class, Color.class, Color.class},
			{String.class, Color.class, Color.class, Color.class, Color.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"type"},
			{"type", "highlight", "shadow"},
			{"type", "highlightOuter", "highlightInner", "shadowOuter", "shadowInner"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		String typeStr = (String) params.get(0);
		int type = typeStr.equals("lowered") ? BevelBorder.LOWERED : BevelBorder.RAISED;

		if (params.size() == 1) {
			return BorderFactory.createSoftBevelBorder(type);
		}

		if (params.size() == 3) {
			Color highlight = (Color) params.get(1);
			Color shadow = (Color) params.get(2);
			return BorderFactory.createSoftBevelBorder(type, highlight, shadow);
		}

		if (params.size() == 5) {
			Color highlightOuter = (Color) params.get(1);
			Color highlightInner = (Color) params.get(2);
			Color shadowOuter = (Color) params.get(3);
			Color shadowInner = (Color) params.get(4);
			return BorderFactory.createSoftBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
		}

		assert false;
		return null;
	}
}
