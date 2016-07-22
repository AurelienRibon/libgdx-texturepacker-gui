package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingFunctions;
import aurelienribon.ui.css.swing.SwingProperties;
import java.awt.Insets;
import javax.swing.text.JTextComponent;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class JTextComponentProcessor implements DeclarationSetProcessor<JTextComponent>, SwingProperties {
	@Override
	public void process(JTextComponent t, DeclarationSet ds) {
		Property p;

		p = caretPosition; if (ds.contains(p)) t.setCaretPosition(ds.getValue(p, Integer.class));
		p = editable; if (ds.contains(p)) t.setEditable(ds.getValue(p, Boolean.class));
		p = margin; if (ds.contains(p)) t.setMargin(ds.getValue(p, Insets.class, SwingFunctions.insets));
		p = text; if (ds.contains(p)) t.setText(ds.getValue(p, String.class));
	}
}
