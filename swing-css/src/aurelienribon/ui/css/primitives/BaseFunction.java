package aurelienribon.ui.css.primitives;

import aurelienribon.ui.css.Function;

/**
 * Convenience class to create a function.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public abstract class BaseFunction implements Function {
	private final String name;

	public BaseFunction(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
