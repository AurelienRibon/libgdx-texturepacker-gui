package aurelienribon.ui.components.processors;

import aurelienribon.ui.components.ArProperties;
import aurelienribon.ui.components.GroupBorder;
import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.swing.SwingFunctions;
import aurelienribon.ui.css.swing.SwingProperties;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Paint;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class JComponentProcessor implements DeclarationSetProcessor<JComponent>, ArProperties {
	@Override
	public void process(JComponent target, DeclarationSet ds) {
		Property p = SwingProperties.border;

		if (ds.contains(p)) {
			GroupBorder border = getGroupBorder(ds);

			if (border != null) {
				p = borderStroke;
				if (ds.contains(p)) border.setStroke(ds.getValue(p, Paint.class));

				p = borderThickness;
				if (ds.contains(p)) border.setThickness(ds.getValue(p, Insets.class, SwingFunctions.insets));

				p = borderHeaderStroke;
				if (ds.contains(p)) border.setHeaderStroke(ds.getValue(p, Paint.class));

				p = borderHeaderThickness;
				if (ds.contains(p)) border.setHeaderThickness(ds.getValue(p, Insets.class, SwingFunctions.insets));

				p = borderHeaderFill;
				if (ds.contains(p)) border.setHeaderFill(ds.getValue(p, Paint.class));

				p = borderTitle;
				if (ds.contains(p)) border.setTitle(ds.getValue(p, String.class));

				p = borderIcon;
				if (ds.contains(p)) border.setTitleIcon(ds.getValue(p, Icon.class, SwingFunctions.icon));

				p = borderFont;
				if (ds.contains(p)) border.setTitleFont(ds.getValue(p, Font.class, SwingFunctions.font));

				p = borderForeground;
				if (ds.contains(p)) border.setTitleForeground(ds.getValue(p, Color.class));

				p = borderMargin;
				if (ds.contains(p)) border.setTitleMargin(ds.getValue(p, Insets.class, SwingFunctions.insets));
			}
		}
	}

	private GroupBorder getGroupBorder(DeclarationSet ds) {
		Property p = SwingProperties.border;
		Object o = ds.getValue(p).get(0);

		if (o instanceof GroupBorder) return (GroupBorder) o;
		if (o instanceof CompoundBorder) {
			Border insideBorder = ((CompoundBorder) ds.getValue(p).get(0)).getInsideBorder();
			Border outsideBorder = ((CompoundBorder) ds.getValue(p).get(0)).getOutsideBorder();
			if (insideBorder instanceof GroupBorder) return (GroupBorder) insideBorder;
			if (outsideBorder instanceof GroupBorder) return (GroupBorder) outsideBorder;
		}

		return null;
	}
}
