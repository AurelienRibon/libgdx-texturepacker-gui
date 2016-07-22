package aurelienribon.ui.css.swing.processors;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingFunctions;
import aurelienribon.ui.css.swing.SwingProperties;
import aurelienribon.ui.css.swing.SwingUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ComponentProcessor implements DeclarationSetProcessor<Component>, SwingProperties {
	@Override
	public void process(Component t, DeclarationSet ds) {
		Property p;

		p = background; if (ds.contains(p)) t.setBackground(ds.getValue(p, Color.class));
		p = foreground; if (ds.contains(p)) t.setForeground(ds.getValue(p, Color.class));
		p = visible; if (ds.contains(p)) t.setVisible(ds.getValue(p, Boolean.class));
		p = enabled; if (ds.contains(p)) t.setEnabled(ds.getValue(p, Boolean.class));
		p = focusable; if (ds.contains(p)) t.setFocusable(ds.getValue(p, Boolean.class));
		p = font; if (ds.contains(p)) t.setFont(ds.getValue(p, Font.class, SwingFunctions.font));

		p = fontFamily; if (ds.contains(p)) {
			String v = ds.getValue(p, String.class);
			Font f = t.getFont();
			t.setFont(new Font(v, f.getStyle(), f.getSize()));
		}

		p = fontStyle; if (ds.contains(p)) {
			String v = ds.getValue(p, String.class);
			Font f = t.getFont();
			t.setFont(new Font(f.getFamily(), SwingUtils.asFontStyle(v), f.getSize()));
		}

		p = fontSize; if (ds.contains(p)) {
			int v = ds.getValue(p, Integer.class);
			Font f = t.getFont();
			t.setFont(new Font(f.getFamily(), f.getStyle(), v));
		}
	}
}
