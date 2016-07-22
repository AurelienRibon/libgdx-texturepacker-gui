package aurelienribon.ui.css;

import aurelienribon.ui.css.Selector.Atom;
import aurelienribon.ui.css.antlr.CssLexer;
import aurelienribon.ui.css.antlr.CssParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/**
 * The CSS Style engine lets you easily apply any kind of parameter to your
 * objects, using CSS stylesheets. It is not directly linked with any
 * technology such as Swing, and therefore can be used with any kind of object,
 * being UI components, business models, or game elements.
 * <p/>
 *
 * <h2>What is CSS?</h2>
 * To understand how to customize the engine with your own elements, you must
 * first quickly understand how a CSS stylesheet is built. A stylesheet is
 * composed of rules, which are each defined by a selector, and a group of
 * declarations. Finally, a declaration is made of a property associated to
 * a value. The following image sums-up all of this:
 * <p/>
 *
 * <img src="http://www.w3schools.com/css/selector.gif" />
 * <p/>
 *
 * As it is commonly used in CSS stylesheets, the engine supports: <br/>
 * + Nested selectors, like ".parent .child {...}"<br/>
 * + Groups of selectors, like ".class1, .class2, .class3 {...}"
 * <p/>
 *
 * <h2>How can I use the engine?</h2>
 * The engine is built around 3 kinds of components: {@link Property
 * properties}, {@link Function functions}, and
 * {@link DeclarationSetProcessor processors}, and you will need to register
 * these components to the engine, since it comes as naked as possible.
 * <p/>
 *
 * <b>Important:</b> <font color="#AA0000">remember that the engine is completely naked, this means
 * that it comes without any registered property, function, or processor. You
 * first need to register these entities to the engine to let it understand
 * how to parse CSS stylesheets and apply them to your targets. Some
 * already made entities (for <b>Swing UIs</b> for instance) can be found at
 * the</font> <a href="http://code.google.com/p/java-universal-css-engine/">
 * project website</a>.
 * <p/>
 *
 * 1. <b>Properties:</b> a property defines a style attribute, like "margin",
 * "border" or "color" in common CSS syntax. A property is always associated
 * with a value in declarations, like "color: #FFF", and this value can be made
 * of one or more parameters. Actually, a property can support different sets of
 * parameters. For instance, the common "margin" CSS property can be called with
 * either 1, 2 or 4 parameters, defining the margin insets.
 * <p/>
 *
 * 2. <b>Functions:</b> a function, or a "functional notation", is used to
 * produce a value for a parameter of a declaration value. It takes one or more
 * parameters (which can be functions too), processes them and returns an
 * object.
 * <p/>
 *
 * 3. <b>Processors:</b> a declarations processor is responsible for applying a
 * group of declarations to a target object. These declarations are retrieved
 * from the stylesheet and correspond to the rules which selectors were
 * walidated by the target object.
 * <p/>
 *
 * <h2>Examples</h2>
 * For examples on how to create custom properties, functions and processors,
 * please see the proposed implementation for Swing, at the <a href=
 * "http://code.google.com/p/java-universal-css-engine/">project website</a>.
 * <p/>
 *
 * Simple rule: this rule will by applied to every JButton of the application
 * (if using the Swing backend for properties/functions/processors of course):
 * <pre>
 * javax-swing-JButton {
 *     -swing-foreground: #000;
 *     -swing-background: #FFF;
 * }
 * </pre>
 *
 * Nested seelctors: this rule will be applied to every object registered with
 * the ".redLabel" classname, but only if it is a child of a toolbar:
 * <pre>
 * javax-swing-JToolBar .redLabel {
 *     -swing-foreground: #F00;
 * }
 * </pre>
 *
 * Grouped selectors: this rule will be applied to every object registered with
 * either the ".redLabel" classname, or the ".importantLabel" classname:
 * <pre>
 * .redLabel, .importantLabel {
 *     -swing-foreground: #F00;
 * }
 * </pre>
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Style {
	// -------------------------------------------------------------------------
	// Static API
	// -------------------------------------------------------------------------

	private static final Map<String, Property> registeredProperties = new LinkedHashMap<String, Property>();
	private static final Map<String, Function> registeredFunctions = new LinkedHashMap<String, Function>();
	private static final Map<Class<?>, List<DeclarationSetProcessor<?>>> registeredProcessors = new LinkedHashMap<Class<?>, List<DeclarationSetProcessor<?>>>();
	private static final Map<Object, List<String>> registeredTargets = new LinkedHashMap<Object, List<String>>();
	private static final Map<Class<?>, ChildrenAccessor<?>> registeredChildrenAccessors = new HashMap<Class<?>, ChildrenAccessor<?>>();
	private static final Map<Class<?>, DeclarationSetManager<?>> registeredDeclarationSetManagers = new HashMap<Class<?>, DeclarationSetManager<?>>();
	private static ParamConverter converter;
	private static List<Selector.Atom> lastStack;

	/**
	 * Registers a new property to the engine.
	 * <p/>
	 * A property defines a style attribute, like "margin", "border" or "color"
	 * in common CSS syntax. A property is always associated with a value in
	 * declarations, like "color: #FFF", and this value can be made of one or
	 * more parameters. Actually, a property can support different sets of
	 * parameters. For instance, the common "margin" CSS property can be called
	 * with either 1, 2 or 4 parameters, defining the margin insets.
	 */
	public static void registerProperty(Property property) {
		if (registeredProperties.containsKey(property.getName())) throw new RuntimeException("Property already registered");
		registeredProperties.put(property.getName(), property);
	}

	/**
	 * Registers a new function to the engine.
	 * <p/>
	 * A function, or a "functional notation", is used to produce a value for a
	 * parameter of a declaration value. It takes one or more parameters (which
	 * can be functions too), processes them and returns an object.
	 */
	public static void registerFunction(Function function) {
		if (registeredFunctions.containsKey(function.getName())) throw new RuntimeException("Function already registered");
		registeredFunctions.put(function.getName(), function);
	}

	/**
	 * Registers a new processor to the engine.
	 * <p/>
	 * A declarations processor is responsible for applying a group of
	 * declarations to a target object. These declarations are retrieved from
	 * the stylesheet and correspond to the rules which selectors were walidated
	 * by the target object.
	 */
	public static void registerProcessor(Class<?> clazz, DeclarationSetProcessor<?> processor) {
		List<DeclarationSetProcessor<?>> list = registeredProcessors.get(clazz);
		if (list == null) {
			list = new ArrayList<DeclarationSetProcessor<?>>();
			registeredProcessors.put(clazz, list);
		}

		if (!list.contains(processor)) list.add(processor);
	}

	/**
	 * Registers a target with one ore more CSS classnames or ids.
	 * <p/>
	 * This is used to assign CSS classnames (like ".something"), or ids (like
	 * "#something") to a target. Don't forget to unregister the target when
	 * you dispose of it, to remove it from memory.
	 */
	public static void registerCssClasses(Object target, String... classes) {
		if (!registeredTargets.containsKey(target)) registeredTargets.put(target, new ArrayList<String>());

		for (String name : classes) {
			if (name.startsWith("#")) {
				for (List<String> names : registeredTargets.values()) {
					if (names.contains(name)) throw new RuntimeException("ID " + name + " has already been registered.");
				}
			}

			if (name.startsWith("#") || name.startsWith(".")) {
				String clazz = name.substring(1);
				List<String> regClasses = registeredTargets.get(target);
				if (!regClasses.contains(clazz)) regClasses.add(clazz);
			} else {
				throw new RuntimeException("Names have to start with a '.' for classes or a '#' for ids");
			}
		}
	}

	/**
	 * Registers a children accessor with a parent class.
	 * <p/>
	 * Accessors are used to automatically apply a style to the children of a
	 * target, without requiring it to manually pass it to its children.
	 */
	public static void registerChildrenAccessor(Class<?> parentClass, ChildrenAccessor<?> accessor) {
		registeredChildrenAccessors.put(parentClass, accessor);
	}

	/**
	 * Registers a declaration set manager with a parent class.
	 * <p/>
	 * Managers are used to handle actions needed by pseudo classes. Usually,
	 * they add a mouse listener to the targets to listen for mouse over (for
	 * the ':hover' pseudo class, etc.
	 */
	public static void registerDeclarationSetManager(Class<?> parentClass, DeclarationSetManager<?> manager) {
		registeredDeclarationSetManagers.put(parentClass, manager);
	}

	/**
	 * Unregisters one or more classnames associated to a target.
	 */
	public static void unregisterCssClasses(Object target, String... classes) {
		List<String> regClasses = registeredTargets.get(target);
		if (regClasses != null) return;
		for (String clazz : classes) {
			if (clazz.startsWith("#") || clazz.startsWith(".")) {
				regClasses.remove(clazz);
			} else {
				throw new RuntimeException("Names have to start with a '.' for classes or a '#' for ids");
			}
		}
	}

	/**
	 * Unregisters every classname associated to a target.
	 */
	public static void unregisterAllCssClasses(Object target) {
		registeredTargets.remove(target);
	}

	/**
	 * Applies a stylesheet to a target and its children, if it has a
	 * corresponding children accessor registered.
	 */
	public static void apply(Object target, Style style) {
		applyImpl(target, style, new ArrayList<Selector.Atom>());
	}

	/**
	 * Applies a stylesheet to a target and its children, if it has a
	 * corresponding children accessor registered.
	 */
	public static void apply(Object target, Style style, List<Selector.Atom> stack) {
		applyImpl(target, style, stack);
	}

	/**
	 * Applies a group of declarations to a target. It is often used as an
	 * advanced technique to propagate the declarations of a target to its
	 * children, even if they should not validate the same rules of the
	 * stylesheet. Complicated to understand, but still useful :)
	 */
	public static void apply(Object target, DeclarationSet ds) {
		for (Class<?> clazz : registeredProcessors.keySet()) {
			if (clazz.isInstance(target)) {
				List<DeclarationSetProcessor<?>> processors = registeredProcessors.get(clazz);
				for (DeclarationSetProcessor<?> proc : processors) {
					((DeclarationSetProcessor<Object>) proc).process(target, ds);
				}
			}
		}
	}

	/**
	 * Sets the converter that will be used to convert special objects found
	 * during parsing into something useable.
	 * <p/>
	 * Indeed, some definitions in a CSS stylesheet may correspond to some
	 * objects that are not defined. This is the case for color definitions,
	 * like "#FFF", which need to be assigned to a color object. However, since
	 * the CSS engine is not bound to any UI technology, it will ask you to
	 * create a color object from every color definition, according to your
	 * framework.
	 */
	public static void setParamConverter(ParamConverter converter) {
		Style.converter = converter;
	}

	/**
	 * Gets a list of every registered properties names.
	 */
	public static List<String> getRegisteredPropertiesNames() {
		return Collections.unmodifiableList(new ArrayList<String>(registeredProperties.keySet()));
	}

	/**
	 * Gets a property by its name.
	 */
	public static Property getRegisteredProperty(String name) {
		return registeredProperties.get(name);
	}

	/**
	 * Gets a list of every registered functions.
	 */
	public static List<String> getRegisteredFunctionsNames() {
		return Collections.unmodifiableList(new ArrayList<String>(registeredFunctions.keySet()));
	}

	/**
	 * Gets a function by its name.
	 */
	public static Function getRegisteredFunction(String name) {
		return registeredFunctions.get(name);
	}

	/**
	 * Gets a list of every target registered with a classname.
	 */
	public static List<Object> getRegisteredTargets() {
		return Collections.unmodifiableList(new ArrayList<Object>(registeredTargets.keySet()));
	}

	/**
	 * Gets the CSS classnames associated to the given target.
	 */
	public static List<String> getRegisteredTargetClassNames(Object target) {
		List<String> classes = registeredTargets.get(target);
		if (classes != null) return classes;
		return new ArrayList<String>();
	}

	/**
	 * Gets the rules manual.
	 */
	public static String getPropertiesManual() {
		return generatePropertiesManual();
	}

	/**
	 * Gets the functions manual.
	 */
	public static String getFunctionsManual() {
		return generateFunctionsManual();
	}

	/**
	 * Gets the stack of element containers computed during last apply call.
	 */
	public static List<Atom> getLastStack() {
		return lastStack;
	}

	// -------------------------------------------------------------------------
	// Attrs + ctors
	// -------------------------------------------------------------------------

	private final String styleSheet;
	private final List<Rule> rules = new ArrayList<Rule>();

	/**
	 * Builds a new Style from an URL pointing to a stylesheet.
	 */
	public Style(URL styleSheetUrl) {
		this.styleSheet = getStyleSheet(styleSheetUrl);
		parse(styleSheet);
	}

	/**
	 * Builds a new Style from String stylesheet.
	 */
	public Style(String styleSheet) {
		this.styleSheet = styleSheet;
		parse(styleSheet);
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	/**
	 * Gets the stylesheet content.
	 */
	public String getStyleSheet() {
		return styleSheet;
	}

	/**
	 * Gets the rules extracted from the stylesheet.
	 */
	public List<Rule> getRules() {
		return Collections.unmodifiableList(rules);
	}

	// -------------------------------------------------------------------------
	// Helpers - load
	// -------------------------------------------------------------------------

	private String getStyleSheet(URL styleSheetUrl) {
		if (styleSheetUrl == null) throw new NullPointerException("styleSheetUrl");
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(styleSheetUrl.openStream()));
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[4096];
			int len;
			while ((len = reader.read(buffer)) > -1) sb.append(buffer, 0, len);
			return sb.toString();

		} catch (IOException ex) {
			return null;

		} finally {
			try {reader.close();} catch (IOException ex) {}
		}
	}

	// -------------------------------------------------------------------------
	// Helpers - parse
	// -------------------------------------------------------------------------

	private void parse(String styleSheet) throws StyleException {
		CharStream cs = new ANTLRStringStream(styleSheet);
		CssLexer lexer = new CssLexer(cs);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		CssParser parser = new CssParser(tokens);

		try {
			parser.stylesheet();
		} catch (RecognitionException ex) {
			throw new StyleException(ex.getMessage());
		}

		for (CssParser.Rule parserRule : parser.rules) {
			List<Property> properties = new ArrayList<Property>();
			Map<Property, List<Object>> propertiesValues = new HashMap<Property, List<Object>>();

			for (String name : parserRule.declarations.keySet()) {
				Property property = registeredProperties.get(name);
				if (property == null) throw StyleException.forProperty(name);

				List<Object> params = parserRule.declarations.get(name);
				if (!checkParams(property, params)) {
					throw StyleException.forPropertyParams(property);
				}

				properties.add(property);
				propertiesValues.put(property, params);
			}

			for (CssParser.Selector rawSelector : parserRule.selectors) {
				Selector selector = new Selector(rawSelector.str);
				DeclarationSet ds = new DeclarationSet(this, properties, propertiesValues);
				rules.add(new Rule(selector, ds));
			}
		}
	}

	// -------------------------------------------------------------------------
	// Helpers - check
	// -------------------------------------------------------------------------

	private boolean checkParams(Parameterized call, List<Object> params) {
		boolean valid = false;

		for (Class[] classes : call.getParams()) {
			if (isMatch(params, classes)) {
				valid = true;
				break;
			}
		}

		return valid;
	}

	private boolean isMatch(List<Object> params, Class[] classes) {
		if (params.size() != classes.length) return false;

		for (int i=0; i<params.size(); i++) {
			Object param = params.get(i);
			Class<?> paramClass = getParamClass(param);

			if (param != null && !classes[i].isAssignableFrom(paramClass)) {
				return false;
			}
		}

		return true;
	}

	private Class<?> getParamClass(Object param) {
		if (param == null) return null;

		if (param instanceof CssParser.Function) {
			CssParser.Function parserFunction = (CssParser.Function) param;
			Function function = registeredFunctions.get(parserFunction.name);
			if (function == null) throw StyleException.forFunction(parserFunction.name);

			List<Object> functionParams = parserFunction.params;
			if (!checkParams(function, functionParams)) throw StyleException.forFunctionParams(function);

			return function.getReturn();
		}

		if (param instanceof CssParser.Color) {
			if (converter != null) return converter.getColorClass();
		}

		return param.getClass();
	}

	// -------------------------------------------------------------------------
	// Helpers - apply
	// -------------------------------------------------------------------------

	private static void applyImpl(Object target, Style style, List<Selector.Atom> stack) {
		// Retrieve all the declarations belonging to the target and evaluate
		// their parameters
		Map<String, DeclarationSet> dss = DeclarationSet.dss(style, target, stack);

		for (DeclarationSet ds : dss.values()) {
			for (Property property : ds.getProperties()) {
				for (int i=0; i<ds.getValue(property).size(); i++) {
					Object param = ds.getValue(property).get(i);
					ds.replaceValueParam(property, i, evaluateParam(param));
				}
			}
		}

		// Add the target class and classname to the selectors stack
		Selector.Atom atom = new Selector.Atom(target.getClass(), getRegisteredTargetClassNames(target));
		stack = new ArrayList<Selector.Atom>(stack);
		stack.add(atom);
		lastStack = Collections.unmodifiableList(stack);

		// Apply these declarations to the target
		for (Class<?> clazz : registeredDeclarationSetManagers.keySet()) {
			if (clazz.isInstance(target)) {
				DeclarationSetManager<Object> manager = (DeclarationSetManager<Object>) registeredDeclarationSetManagers.get(clazz);
				manager.manage(target, dss);
			}
		}

		// Iterate over the target children, if there is any
		if (target instanceof Container) {
			Container parent = (Container) target;
			if (parent.getChildren() != null) {
				for (Object child : parent.getChildren()) apply(child, style, stack);
			}

		} else {
			for (Class<?> clazz : registeredChildrenAccessors.keySet()) {
				if (clazz.isInstance(target)) {
					ChildrenAccessor<Object> accessor = (ChildrenAccessor<Object>) registeredChildrenAccessors.get(clazz);
					if (accessor.getChildren(target) != null) {
						for (Object child : accessor.getChildren(target)) apply(child, style, stack);
					}
				}
			}
		}
	}

	private static Object evaluateParam(Object param) throws StyleException {
		if (param instanceof CssParser.Color) {
			CssParser.Color c = (CssParser.Color) param;
			if (converter != null) return converter.convertColor(parseColor(c.str));
		}

		if (param instanceof CssParser.Function) {
			CssParser.Function parserFunction = (CssParser.Function) param;
			Function function = registeredFunctions.get(parserFunction.name);
			List<Object> params = new ArrayList<Object>(parserFunction.params);

			for (int i=0; i<params.size(); i++) {
				params.set(i, evaluateParam(params.get(i)));
			}

			return function.process(params);
		}

		return param;
	}

	// -------------------------------------------------------------------------
	// Helpers - manual
	// -------------------------------------------------------------------------

	private static String generatePropertiesManual() {
		String str = "";

		for (Property property : registeredProperties.values()) {
			str += property.getName() + "\n";

			for (int i=0; i<property.getParams().length; i++) {
				str += "    ";

				for (int ii=0; ii<property.getParams()[i].length; ii++) {
					String clazz = property.getParams()[i][ii].getSimpleName();
					String name = property.getParamsNames()[i][ii];

					if (ii > 0) str += ", ";
					str += prettify(clazz) + " " + name;
				}

				str += "\n";
			}

			str += "\n";
		}

		return str.trim();
	}

	private static String generateFunctionsManual() {
		String str = "";

		for (Function function : registeredFunctions.values()) {
			str += function.getName() + getReturnStatement(function) + "\n";

			for (int i=0; i<function.getParams().length; i++) {
				str += "    ";

				for (int ii=0; ii<function.getParams()[i].length; ii++) {
					String clazz = function.getParams()[i][ii].getSimpleName();
					String name = function.getParamsNames()[i][ii];

					if (ii > 0) str += ", ";
					str += prettify(clazz) + " " + name;
				}

				str += "\n";
			}

			str += "\n";
		}

		return str.trim();
	}

	private static String prettify(String clazz) {
		clazz = clazz.replace("Integer", "int");
		clazz = clazz.replace("Float", "float");
		clazz = clazz.replace("Number", "float");
		clazz = clazz.replace("Boolean", "boolean");
		return clazz;
	}

	private static String getReturnStatement(Function function) {
		return " [returns " + prettify(function.getReturn().getSimpleName()) + "]";
	}

	private static int parseColor(String s) {
		int incr = s.length() == 9 || s.length() == 7 ? 2 : 1;
		boolean alpha = s.length() == 9 || s.length() == 5;

		int i = 1;
		int a = alpha ? hex(s.substring(i,i+=incr)) : 255;
		int r = hex(s.substring(i,i+=incr));
		int g = hex(s.substring(i,i+=incr));
		int b = hex(s.substring(i,i+=incr));

		return (a<<24) | (r<<16) | (g<<8) | b;
	}

	private static int hex(String s) {
		if (s.length() == 1) s = s.concat(s);
		return Integer.parseInt(s, 16);
	}
}
