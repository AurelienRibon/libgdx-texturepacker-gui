package aurelienribon.ui.css;

import java.util.*;

/**
 * A declaration set defines a group of declarations. In CSS, a declaration is
 * a property associated to a value (one or more parameters). Declaration sets
 * are mainly used in two ways:
 * <p/>
 * 1. To group every declarations associated to a rule in a CSS stylesheet.
 * 2. To group every declarations that are part of rules which selectors
 * correspond to a target object, when a style is applied to this target object.
 * <p/>
 * See <a href="http://www.w3schools.com/css/css_syntax.asp">w3schools</a> for
 * more information about CSS syntax.
 *
 * @see Property
 * @see Rule
 * @see Style
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class DeclarationSet {
	public static Map<String, DeclarationSet> dss(Style style, Object target, List<Selector.Atom> stack) {
		Map<String, DeclarationSet> dss = new LinkedHashMap<String, DeclarationSet>();
		List<String> pseudoClassesToTry = new ArrayList<String>();
		List<String> pseudoClassesTried = new ArrayList<String>();
		pseudoClassesToTry.add("");

		while (!pseudoClassesToTry.isEmpty()) {
			String pseudoClass = pseudoClassesToTry.remove(0);
			pseudoClassesTried.add(pseudoClass);

			List<Property> properties = new ArrayList<Property>();
			Map<Property, List<Object>> propertiesValues = new HashMap<Property, List<Object>>();

			for (Rule rule : style.getRules()) {
				assert rule != null;
				assert rule.getDeclarations() != null;

				if (!isTargetValid(rule.getSelector(), target)) continue;
				if (!isStackValid(rule.getSelector(), stack)) continue;

				String rulePseudoClass = rule.getSelector().getPseudoClass();

				if (!rulePseudoClass.equals(pseudoClass)) {
					if (!pseudoClassesTried.contains(rulePseudoClass)) pseudoClassesToTry.add(rulePseudoClass);
					continue;
				}

				properties.addAll(rule.getDeclarations().getProperties());

				for (Property property : rule.getDeclarations().getProperties()) {
					assert rule.getDeclarations().getValue(property) != null;

					List<Object> value = new ArrayList<Object>();
					value.addAll(rule.getDeclarations().getValue(property));
					propertiesValues.put(property, value);
				}
			}

			if (!properties.isEmpty()) {
				DeclarationSet ds = new DeclarationSet(style, properties, propertiesValues);
				dss.put(pseudoClass, ds);
			}
		}

		return dss;
	}





	private final Style style;
	private final List<Property> properties;
	private final Map<Property, List<Object>> propertiesValues;

	/**
	 * Constructor used to group every declarations associated to a rule in a
	 * CSS stylesheet.
	 * @param style The style applied to the target.
	 * @param properties A list of properties.
	 * @param propertiesValues A map of values associated to the properties.
	 */
	public DeclarationSet(Style style, List<Property> properties, Map<Property, List<Object>> propertiesValues) {
		this.style = style;
		this.properties = Collections.unmodifiableList(new ArrayList<Property>(properties));
		this.propertiesValues = Collections.unmodifiableMap(new HashMap<Property, List<Object>>(propertiesValues));
	}

	/**
	 * Constructor used to group every declarations that are part of rules which
	 * selectors correspond to a target object, when a style is applied to this
	 * target object.
	 * @param style The style applied to the target.
	 * @param target The target object.
	 * @param stack The stack of classnames for the target. Used to know if the
	 * target correspond to some nested selectors.
	 * @param pseudoClass Only the rules with the given pseudo class will be
	 * scanned.
	 */
	public DeclarationSet(Style style, Object target, List<Selector.Atom> stack, String pseudoClass) {
		List<Property> tProperties = new ArrayList<Property>();
		Map<Property, List<Object>> tPropertiesValues = new HashMap<Property, List<Object>>();

		for (Rule rule : style.getRules()) {
			assert rule != null;
			assert rule.getDeclarations() != null;

			if (!rule.getSelector().getPseudoClass().equals(pseudoClass)) continue;
			if (!isTargetValid(rule.getSelector(), target)) continue;
			if (!isStackValid(rule.getSelector(), stack)) continue;

			tProperties.addAll(rule.getDeclarations().getProperties());

			for (Property property : rule.getDeclarations().getProperties()) {
				assert rule.getDeclarations().getValue(property) != null;

				List<Object> value = new ArrayList<Object>();
				value.addAll(rule.getDeclarations().getValue(property));
				tPropertiesValues.put(property, value);
			}
		}

		this.style = style;
		this.properties = Collections.unmodifiableList(tProperties);
		this.propertiesValues = Collections.unmodifiableMap(tPropertiesValues);
	}

	/**
	 * Constructor used to build a new declaration set from an old one, by
	 * keeping only the declarations corresponding to the given properties.
	 * @param ds A declaration set.
	 * @param propertiesToKeep An array of the properties to keep.
	 */
	public DeclarationSet(DeclarationSet ds, Property[] propertiesToKeep) {
		List<Property> tProperties = new ArrayList<Property>();
		Map<Property, List<Object>> tPropertiesValues = new HashMap<Property, List<Object>>();

		for (Property property : propertiesToKeep) {
			if (ds.getProperties().contains(property)) {
				tProperties.add(property);
				tPropertiesValues.put(property, ds.getValue(property));
			}
		}

		this.style = ds.getStyle();
		this.properties = Collections.unmodifiableList(new ArrayList<Property>(tProperties));
		this.propertiesValues = Collections.unmodifiableMap(new HashMap<Property, List<Object>>(tPropertiesValues));
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	/**
	 * Gets the style used to extract these declarations.
	 */
	public Style getStyle() {
		return style;
	}

	/**
	 * Returns true if the declaration set does not contain any declaration.
	 */
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	/**
	 * Gets the list of every properties contained in this set.
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * Gets the map of the values associated to the properties contained in this
	 * declaration set.
	 */
	public Map<Property, List<Object>> getPropertiesValues() {
		return propertiesValues;
	}

	/**
	 * Gets the value (a list of one or more parameters) associated to the
	 * given property.
	 */
	public List<Object> getValue(Property property) {
		return propertiesValues.get(property);
	}

	/**
	 * Convenience method to return the first and only parameter of a value.
	 * Shouldn't be used if the value has more than one parameter.
	 */
	public <T> T getValue(Property property, Class<T> paramClass) {
		return (T) getValue(property).get(0);
	}

	/**
	 * Convenience method to return the first and only parameter of a value if
	 * it is of class paramClass. Else, returns the result of the given
	 * function. Should be used with properties based on functions.
	 * @see FunctionProperty
	 */
	public <T> T getValue(Property property, Class<T> paramClass, Function function) {
		List<Object> params = getValue(property);
		Object param = getValue(property).get(0);

		if (paramClass.isInstance(param)) return (T) param;
		return (T) function.process(params);
	}

	/**
	 * Returns true of the declaration set contains a declaration with the
	 * given property.
	 */
	public boolean contains(Property property) {
		return properties.contains(property);
	}

	/**
	 * Returns true if the property p1 comes after the property p2 in the
	 * declaration set. If true, p1 should override p2 if they define similar
	 * behavior.
	 */
	public boolean isAfter(Property p1, Property p2) {
		return properties.indexOf(p1) > properties.indexOf(p2);
	}

	// -------------------------------------------------------------------------
	// Package API
	// -------------------------------------------------------------------------

	void replaceValueParam(Property property, int paramIdx, Object param) {
		propertiesValues.get(property).set(paramIdx, param);
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static boolean isTargetValid(Selector selector, Object target) {
		if (!selector.getLastAtom().getType().isAssignableFrom(target.getClass())) return false;

		List<String> targetClasses = Style.getRegisteredTargetClassNames(target);
		for (String clazz : selector.getLastAtom().getClasses()) {
			if (!targetClasses.contains(clazz)) return false;
		}

		return true;
	}

	private static boolean isStackValid(Selector selector, List<Selector.Atom> stack) {
		int previousStackIdx = -1;
		int stackIdx = -1;

		for (int i=0; i<selector.getAtoms().size()-1; i++) {
			Selector.Atom selectorAtom = selector.getAtoms().get(i);
			stackIdx = getNextValidStackIdx(selectorAtom, stack, stackIdx+1);
			if (stackIdx == -1) return false;
			if (selectorAtom.isStrictNext() && (stackIdx - previousStackIdx) != 1) return false;
			previousStackIdx = stackIdx;
		}

		if (selector.getLastAtom().isStrictNext() && (stack.size() - stackIdx) != 1) return false;

		return true;
	}

	private static int getNextValidStackIdx(Selector.Atom selectorAtom, List<Selector.Atom> stack, int from) {
		for (int i=from; i<stack.size(); i++) {
			Selector.Atom stackAtom = stack.get(i);

			if (!selectorAtom.getType().isAssignableFrom(stackAtom.getType())) continue;
			if (!includes(stackAtom.getClasses(), selectorAtom.getClasses())) continue;

			return i;
		}

		return -1;
	}

	private static boolean includes(List<String> strs1, List<String> strs2) {
		for (String str : strs2) if (!strs1.contains(str)) return false;
		return true;
	}
}
