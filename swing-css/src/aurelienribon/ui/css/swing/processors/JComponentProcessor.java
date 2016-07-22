package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingProperties;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class JComponentProcessor implements DeclarationSetProcessor<JComponent>, SwingProperties {
	@Override
	public void process(JComponent t, DeclarationSet ds) {
		Property p;

		p = opaque; if (ds.contains(p)) t.setOpaque(ds.getValue(p, Boolean.class));
		p = tooltip; if (ds.contains(p)) t.setToolTipText(ds.getValue(p, String.class));
		p = border; if (ds.contains(p)) t.setBorder(ds.getValue(p, Border.class));
	}
}
