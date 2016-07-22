package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class CompoundBorderFunction implements Function {
	@Override
	public String getName() {
		return "compoundborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{},
			{Border.class, Border.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{},
			{"outsideBorder", "insideBorder"}
		};
	}

	@Override
	public Class getReturn() {
		return Border.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.isEmpty()) {
			return BorderFactory.createCompoundBorder();
		}

		if (params.size() == 2) {
			Border outsideBorder = (Border) params.get(0);
			Border insideBorder = (Border) params.get(1);
			return BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
		}

		assert false;
		return null;
	}
}
