package aurelienribon.ui.components;

import aurelienribon.ui.components.TabPanelModel.TabModel;
import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Property;
import aurelienribon.ui.utils.PaintUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
class TabPanelHeaderSubPanel extends JComponent {
	private static final ImageIcon IC_CROSS1 = new ImageIcon(TabPanelHeaderSubPanel.class.getResource("ic_cross1_dark.png"));
	private static final ImageIcon IC_CROSS2 = new ImageIcon(TabPanelHeaderSubPanel.class.getResource("ic_cross2_dark.png"));

	private final TabModel model;
	private final Callback callback;
	private final JLabel descLabel = new JLabel();
	private final JLabel crossLabel = new JLabel(IC_CROSS1);

	private Paint stroke = Color.RED;
	private Paint fill = Color.RED;

	public TabPanelHeaderSubPanel(TabModel model, Callback callback) {
		this.model = model;
		this.callback = callback;

		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);

		JPanel p = new JPanel(new BorderLayout());
		p.setOpaque(false);
		p.add(Box.createHorizontalStrut(12), BorderLayout.WEST);
		p.add(crossLabel, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(Box.createHorizontalStrut(6), BorderLayout.WEST);
		add(descLabel, BorderLayout.CENTER);
		add(p, BorderLayout.EAST);
	}

	public interface Callback {
		public void selectRequested(TabModel tm);
		public void closeRequested(TabModel tm);
		public void closeAllRequested();
		public void closeOthersRequested(TabModel tm);
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public TabModel getModel() {
		return model;
	}

	public Paint getStroke() {
		return stroke;
	}

	public Paint getFill() {
		return fill;
	}

	public void setStroke(Paint stroke) {
		this.stroke = stroke;
		repaint();
	}

	public void setFill(Paint fill) {
		this.fill = fill;
		repaint();
	}

	// -------------------------------------------------------------------------
	// Paint
	// -------------------------------------------------------------------------

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g.create();
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		if (fill != null) {
			gg.setPaint(PaintUtils.buildPaint(fill, 0, 0, w, h));
			gg.fillRect(0, 0, w, h);
		}

		if (stroke != null) {
			gg.setPaint(PaintUtils.buildPaint(stroke, 0, 0, w, h));
			gg.drawLine(0, 0, w-1, 0);
			gg.drawLine(0, 0, 0, h-1);
			gg.drawLine(w-1, 0, w-1, h-1);
		}

		updateLabels();
		gg.dispose();
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private void updateLabels() {
		descLabel.setForeground(getForeground());
		descLabel.setFont(getFont());
		descLabel.setText(model.title);
		descLabel.setIcon(model.icon);
		descLabel.setIconTextGap(6);

		crossLabel.setVisible(model.closable);
	}

	// -------------------------------------------------------------------------
	// Inputs
	// -------------------------------------------------------------------------

	private final MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (e.getX() < getWidth() - crossLabel.getWidth()) {
					callback.selectRequested(model);
				} else {
					callback.closeRequested(model);
				}

			} else if (SwingUtilities.isMiddleMouseButton(e)) {
				callback.closeRequested(model);
			}

			if (e.isPopupTrigger()) new PopupMenu().show(TabPanelHeaderSubPanel.this, e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) new PopupMenu().show(TabPanelHeaderSubPanel.this, e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (e.getX() < getWidth() - crossLabel.getWidth()) {
				crossLabel.setIcon(IC_CROSS1);
			} else {
				crossLabel.setIcon(IC_CROSS2);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			crossLabel.setIcon(IC_CROSS1);
		}
	};

	private class PopupMenu extends JPopupMenu {
		public PopupMenu() {
			add(closeAction);
			add(closeAllAction);
			add(closeOthersAction);
		}

		private final Action closeAction = new AbstractAction("Close") {
			@Override public void actionPerformed(ActionEvent e) {callback.closeRequested(model);}
		};

		private final Action closeAllAction = new AbstractAction("Close all") {
			@Override public void actionPerformed(ActionEvent e) {callback.closeAllRequested();}
		};

		private final Action closeOthersAction = new AbstractAction("Close others") {
			@Override public void actionPerformed(ActionEvent e) {callback.closeOthersRequested(model);}
		};
	};

	// -------------------------------------------------------------------------
	// StyleProcessor
	// -------------------------------------------------------------------------

	public static class Processor implements DeclarationSetProcessor<TabPanelHeaderSubPanel>, ArProperties {
		@Override
		public void process(TabPanelHeaderSubPanel target, DeclarationSet rs) {
			Property p;
			p = stroke; if (rs.contains(p)) target.setStroke(rs.getValue(p, Paint.class));
			p = fill; if (rs.contains(p)) target.setFill(rs.getValue(p, Paint.class));
		}
	}
}
