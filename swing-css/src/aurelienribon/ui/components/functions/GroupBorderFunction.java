package aurelienribon.ui.components.functions;

import aurelienribon.ui.components.GroupBorder;
import aurelienribon.ui.css.Function;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class GroupBorderFunction implements Function {
	@Override
	public String getName() {
		return "arui-groupborder";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{String.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"noarg"}
		};
	}

	@Override
	public Class getReturn() {
		return GroupBorder.class;
	}

	@Override
	public Object process(List<Object> params) {
		return new GroupBorder();
	}
}
