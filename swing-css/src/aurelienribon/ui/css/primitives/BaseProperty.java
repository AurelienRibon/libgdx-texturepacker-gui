package aurelienribon.ui.css.primitives;

import aurelienribon.ui.css.Property;

/**
 * Convenience class to create a property.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public abstract class BaseProperty implements Property {
	private final String name;

	public BaseProperty(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
