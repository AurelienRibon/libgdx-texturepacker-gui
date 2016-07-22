package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingFunctions;
import aurelienribon.ui.css.swing.SwingProperties;
import aurelienribon.ui.css.swing.SwingUtils;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class JLabelProcessor implements DeclarationSetProcessor<JLabel>, SwingProperties {
	@Override
	public void process(JLabel t, DeclarationSet ds) {
		Property p;

		p = horizAlign; if (ds.contains(p)) t.setHorizontalAlignment(SwingUtils.asHAlign(ds.getValue(p, String.class)));
		p = vertAlign; if (ds.contains(p)) t.setVerticalAlignment(SwingUtils.asVAlign(ds.getValue(p, String.class)));
		p = text; if (ds.contains(p)) t.setText(ds.getValue(p, String.class));
		p = icon; if (ds.contains(p)) t.setIcon(ds.getValue(p, Icon.class, SwingFunctions.icon));
	}
}
