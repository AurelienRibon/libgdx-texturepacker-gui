package aurelienribon.ui.css;

/**
 * Some definitions in a CSS stylesheet may correspond to some objects that
 * are not defined. This is the case for color definitions, like "#FFF", which
 * need to be assigned to a color object. However, since the CSS engine is not
 * bound to any UI technology, it will ask you to create a color object from
 * every color definition, according to your framework.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface ParamConverter {
	/**
	 * Creates an object representing a color from a color definition (argb).
	 */
	public Object convertColor(int argb);

	/**
	 * Gets the class of the returned result for convertColor method.
	 */
	public Class<?> getColorClass();
}
