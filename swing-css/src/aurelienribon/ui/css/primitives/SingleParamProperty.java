package aurelienribon.ui.css.primitives;

/**
 * Convenience class to define a property that accepts only one parameter as 
 * value.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SingleParamProperty extends BaseProperty {
	private Class paramClass;
	private String paramName;

	public SingleParamProperty(String name, Class paramClass, String paramName) {
		super(name);
		this.paramClass = paramClass;
		this.paramName = paramName;
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {{paramClass}};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {{paramName}};
	}
}
