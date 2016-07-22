package aurelienribon.ui.css;

/**
 * If a class implements the Container interface, then its children are the
 * one returned by the getChildren() method, no matter what ChildrenAccessor
 * is registered to the CSS engine. This let you return what you want
 * instead of following the default behavior.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface Container {
	public Object[] getChildren();
}
