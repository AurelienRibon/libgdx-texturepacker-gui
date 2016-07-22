package aurelienribon.ui.css;

import java.util.Map;

/**
 * A declaration set manager is used to handle the different declaration sets
 * associated to the pseudo classes of a given target. For instance, is the
 * declaration set associated to the ':hover' pseudo classe of a button target
 * is not empty, this manager should register a mouse listener with the button
 * that would apply the good declaration set to the button when a mouseOver
 * event rise.
 * <p/>
 * If all targets extends a root class, then it should only be necessary to
 * register a single manager with the CSS engine.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface DeclarationSetManager<T> {
	public void manage(T target, Map<String, DeclarationSet> dss);
}
