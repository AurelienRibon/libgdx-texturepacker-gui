package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import java.net.URL;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class IconFunction implements Function {
	@Override
	public String getName() {
		return "icon";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{URL.class},
			{String.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"url"},
			{"filepath"}
		};
	}

	@Override
	public Class getReturn() {
		return Icon.class;
	}

	@Override
	public Object process(List<Object> params) {
		if (params.get(0) instanceof URL) return new ImageIcon((URL) params.get(0));
		if (params.get(0) instanceof String) return new ImageIcon((String) params.get(0));

		assert false;
		return null;
	}
}
