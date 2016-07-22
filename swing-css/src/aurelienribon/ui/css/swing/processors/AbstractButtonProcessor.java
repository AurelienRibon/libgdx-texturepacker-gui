package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingFunctions;
import aurelienribon.ui.css.swing.SwingProperties;
import aurelienribon.ui.css.swing.SwingUtils;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.Icon;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class AbstractButtonProcessor implements DeclarationSetProcessor<AbstractButton>, SwingProperties {
	@Override
	public void process(AbstractButton t, DeclarationSet ds) {
		Property p;
		
		p = borderPainted; if (ds.contains(p)) t.setBorderPainted(ds.getValue(p, Boolean.class));
		p = contentAreaFilled; if (ds.contains(p)) t.setContentAreaFilled(ds.getValue(p, Boolean.class));
		p = margin; if (ds.contains(p)) t.setMargin(ds.getValue(p, Insets.class, SwingFunctions.insets));
		p = horizAlign; if (ds.contains(p)) t.setHorizontalAlignment(SwingUtils.asHAlign(ds.getValue(p, String.class)));
		p = vertAlign; if (ds.contains(p)) t.setVerticalAlignment(SwingUtils.asVAlign(ds.getValue(p, String.class)));
		p = text; if (ds.contains(p)) t.setText(ds.getValue(p, String.class));
		p = icon; if (ds.contains(p)) t.setIcon(ds.getValue(p, Icon.class, SwingFunctions.icon));
	}
}
