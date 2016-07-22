package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class EmptyBorderFunction implements Function {
	@Override
	public String getName() {
		return "emptyborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{Integer.class, Integer.class, Integer.class, Integer.class}
		};
}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"top", "left", "bottom", "right"}
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
		return BorderFactory.createEmptyBorder(top, left, bottom, right);
	}
}
