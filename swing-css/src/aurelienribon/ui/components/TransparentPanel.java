package aurelienribon.ui.components;

import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 * A TransparentPanel is always non-opaque, and always paints its background
 * color. This is useful when you need to paint a color with an alpha channel
 * as background.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TransparentPanel extends JPanel {
	public TransparentPanel() {
		super();
	}

	public TransparentPanel(LayoutManager layout) {
		super(layout);
	}

	public TransparentPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public TransparentPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
