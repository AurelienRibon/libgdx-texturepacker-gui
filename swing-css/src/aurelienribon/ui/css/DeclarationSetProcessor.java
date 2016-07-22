package aurelienribon.ui.css;

/**
 * A declarations processor is responsible for applying a group of declarations
 * to a target object. These declarations are retrieved from the stylesheet and
 * correspond to the rules which selectors were walidated by the target object.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface DeclarationSetProcessor<T> {
	public void process(T t, DeclarationSet ds);
}
