package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TitledBorderFunction implements Function {
	@Override
	public String getName() {
		return "titledborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Border.class},
			{String.class},
			{Border.class, String.class},
			{Border.class, String.class, Integer.class, Integer.class},
			{Border.class, String.class, Integer.class, Integer.class, Font.class},
			{Border.class, String.class, Integer.class, Integer.class, Font.class, Color.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"border"},
			{"title"},
			{"border", "title"},
			{"border", "title", "titleJustification", "titlePosition"},
			{"border", "title", "titleJustification", "titlePosition", "titleFont"},
			{"border", "title", "titleJustification", "titlePosition", "titleFont", "titleColor"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.size() == 1 && params.get(0) instanceof Border) {
			Border border = (Border) params.get(0);
			return BorderFactory.createTitledBorder(border);
		}

		if (params.size() == 1 && params.get(0) instanceof String) {
			String title = (String) params.get(0);
			return BorderFactory.createTitledBorder(title);
		}

		if (params.size() == 2) {
			Border border = (Border) params.get(0);
			String title = (String) params.get(1);
			return BorderFactory.createTitledBorder(border, title);
		}

		if (params.size() == 4) {
			Border border = (Border) params.get(0);
			String title = (String) params.get(1);
			int titleJustification = (Integer) params.get(2);
			int titlePosition = (Integer) params.get(3);
			return BorderFactory.createTitledBorder(border, title, titleJustification, titlePosition);
		}

		if (params.size() == 5) {
			Border border = (Border) params.get(0);
			String title = (String) params.get(1);
			int titleJustification = (Integer) params.get(2);
			int titlePosition = (Integer) params.get(3);
			Font titleFont = (Font) params.get(4);
			return BorderFactory.createTitledBorder(border, title, titleJustification, titlePosition, titleFont);
		}

		if (params.size() == 6) {
			Border border = (Border) params.get(0);
			String title = (String) params.get(1);
			int titleJustification = (Integer) params.get(2);
			int titlePosition = (Integer) params.get(3);
			Font titleFont = (Font) params.get(4);
			Color titleColor = (Color) params.get(5);
			return BorderFactory.createTitledBorder(border, title, titleJustification, titlePosition, titleFont, titleColor);
		}

		assert false;
		return null;
	}
}
