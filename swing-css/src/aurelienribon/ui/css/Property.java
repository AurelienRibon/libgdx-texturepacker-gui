package aurelienribon.ui.css;

/**
 * A property defines a style attribute, like "margin", "border" or "color" in
 * common CSS syntax. A property is always associated with a value in
 * declarations, like "color: #FFF", and this value can be made of one or more
 * parameters. Actually, a property can support different sets of parameters.
 * For instance, the common "margin" CSS property can be called with either
 * 1, 2 or 4 parameters, defining the margin insets.
 * <p/>
 * Therefore, the engine tests the parameters associated to a property when
 * it parses the file, to ensure that the stylesheet is valid. That's why a
 * Property implementation needs to implement the getParams() function: to
 * tell the engine what are the valid configurations of parameters for a given
 * property.
 * <p/>
 * See <a href="http://www.w3schools.com/css/css_syntax.asp">w3schools</a> for
 * more information about CSS syntax.
 *
 * @see Rule
 * @see DeclarationSet
 * @see Style
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface Property extends Parameterized {
	/**
	 * Gets the name of the property, as it will be called in a CSS rule.
	 */
	public String getName();

	/**
	 * Gets the parameters configuration. A configuration describes one or
	 * more sets of valid parameter classes.
	 * <p/>
	 * For instance, the common "margin" CSS property can be called with either
	 * 1, 2 or 4 parameters, defining the margin insets. You would actually
	 * implement that as follows:
	 * <pre>
	 * return new Class[][] {
	 *     {Integer.class},
	 *     {Integer.class, Integer.class},
	 *     {Integer.class, Integer.class, Integer.class, Integer.class},
	 * };
	 * </pre>
	 */
	@Override
	public Class[][] getParams();

	/**
	 * Gets the parameters names.
	 */
	@Override
	public String[][] getParamsNames();
}
