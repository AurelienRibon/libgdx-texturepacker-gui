package aurelienribon.ui.css;

import java.util.List;

/**
 * A children accessor is used by the CSS engine to automatically apply a style
 * to the children of a target object, and so on. Therefore, applying a style
 * to a root object also applies it to every objects int he application.
 * <p/>
 * By default, the engine does not know how to access the children of a target
 * object, since it is generic and only delas with raw Object instances.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface ChildrenAccessor<T> {
	public List<?> getChildren(T target);
}
