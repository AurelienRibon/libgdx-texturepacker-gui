package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingProperties;
import javax.swing.JTextArea;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class JTextAreaProcessor implements DeclarationSetProcessor<JTextArea>, SwingProperties {
	@Override
	public void process(JTextArea t, DeclarationSet ds) {
		Property p;

		p = lineWrap; if (ds.contains(p)) t.setLineWrap(ds.getValue(p, Boolean.class));
		p = wrapStyleWord; if (ds.contains(p)) t.setWrapStyleWord(ds.getValue(p, Boolean.class));
		p = tabSize; if (ds.contains(p)) t.setTabSize(ds.getValue(p, Integer.class));
	}
}
