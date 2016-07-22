package aurelienribon.ui.components;

import aurelienribon.ui.utils.PaintUtils;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * A GroupBorder is a highly customizable titled border. It is composed of a
 * stroke around your component, and a header. The body stroke can be any
 * {@link Paint} object, and the stroke thickness can be specified with
 * independant values for the four sides. The header supports the same kind of
 * stroke, as well as a fill {@link Paint} object, a title and an icon.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class GroupBorder implements Border {
	private final JLabel label = new JLabel();

	private Paint stroke = Color.GRAY;
	private Insets thickness = new Insets(1, 1, 1, 1);
	private Paint headerStroke = Color.GRAY;
	private Insets headerThickness = new Insets(1, 1, 0, 1);
	private Paint headerFill = Color.LIGHT_GRAY;
	private String title = "";
	private Icon titleIcon = null;
	private Font titleFont = null;
	private Color titleForeground = Color.BLACK;
	private Insets titleMargin = new Insets(0, 0, 0, 0);
	private int titleHorizontalAlignment = SwingConstants.LEFT;
	private int titleVerticalAlignment = SwingConstants.CENTER;

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D gg = (Graphics2D) g.create();
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.translate(x, y);

		int hh = getHeaderHeight(c);

		// Header fill

		if (headerFill != null && headerStroke != null) {
			int fx = headerThickness.left;
			int fy = headerThickness.top;
			int fw = width - headerThickness.left - headerThickness.right;
			int fh = hh - headerThickness.top - headerThickness.bottom;
			gg.setPaint(PaintUtils.buildPaint(headerFill, fx, fy, fw, fh));
			gg.fillRect(fx, fy, fw, fh);
		} else if (headerFill != null) {
			gg.setPaint(PaintUtils.buildPaint(headerFill, 0, 0, width, hh));
			gg.fillRect(0, 0, width, hh);
		}

		// Header stroke

		if (headerStroke != null) {
			gg.setPaint(PaintUtils.buildPaint(headerStroke, 0, 0, width, hh));

			if (headerThickness.top > 0) {
				int gap = headerThickness.top / 2;
				gg.setStroke(new BasicStroke(headerThickness.top, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(0, gap, width-1, gap);
			}

			if (headerThickness.left > 0) {
				int gap = headerThickness.left / 2;
				gg.setStroke(new BasicStroke(headerThickness.left, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(gap, 0, gap, hh-1);
			}

			if (headerThickness.bottom > 0) {
				int gap = headerThickness.bottom / 2;
				gg.setStroke(new BasicStroke(headerThickness.bottom, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(0, gap+hh-1, width-1, gap+hh-1);
			}

			if (headerThickness.right > 0) {
				int gap = headerThickness.right / 2;
				gg.setStroke(new BasicStroke(headerThickness.right, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(gap+width-1, 0, gap+width-1, hh-1);
			}
		}

		// Body stroke

		if (stroke != null) {
			gg.setPaint(PaintUtils.buildPaint(stroke, 0, hh, width, height-hh));

			if (thickness.top > 0) {
				int gap = thickness.top / 2;
				gg.setStroke(new BasicStroke(thickness.top, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(0, hh+gap, width-1, hh+gap);
			}

			if (thickness.left > 0) {
				int gap = thickness.left / 2;
				gg.setStroke(new BasicStroke(thickness.left, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(gap, hh, gap, height-1);
			}

			if (thickness.bottom > 0) {
				int gap = thickness.bottom / 2;
				gg.setStroke(new BasicStroke(thickness.bottom, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(0, gap+height-1, width-1, gap+height-1);
			}

			if (thickness.right > 0) {
				int gap = thickness.right / 2;
				gg.setStroke(new BasicStroke(thickness.right, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				gg.drawLine(gap+width-1, hh, gap+width-1, height-1);
			}
		}

		// Header title

		int labelW = width - titleMargin.left - titleMargin.right;
		int labelH = hh - titleMargin.top - titleMargin.bottom;

		if (headerStroke != null) labelW -= headerThickness.left + headerThickness.right;
		if (headerStroke != null) labelH -= headerThickness.top + headerThickness.bottom;

		if (headerStroke != null) {
			gg.translate(headerThickness.left + titleMargin.left, headerThickness.top + titleMargin.top);
		} else {
			gg.translate(titleMargin.left, titleMargin.top);
		}

		label.setSize(labelW, labelH);
		label.paint(gg);

		// Dispose

		gg.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		Insets insets = new Insets(getHeaderHeight(c), 0, 0, 0);
		if (stroke != null) {
			insets.top += thickness.top;
			insets.left += thickness.left;
			insets.bottom += thickness.bottom;
			insets.right += thickness.right;
		}
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	// -------------------------------------------------------------------------
	// Getters / setters
	// -------------------------------------------------------------------------

	public Paint getStroke() {
		return stroke;
	}

	public Insets getThickness() {
		return thickness;
	}

	public Paint getHeaderStroke() {
		return headerStroke;
	}

	public Insets getHeaderThickness() {
		return headerThickness;
	}

	public Paint getHeaderFill() {
		return headerFill;
	}

	public String getTitle() {
		return title;
	}

	public Icon getTitleIcon() {
		return titleIcon;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public Color getTitleForeground() {
		return titleForeground;
	}

	public Insets getTitleMargin() {
		return titleMargin;
	}

	public int getTitleHorizontalAlignment() {
		return titleHorizontalAlignment;
	}

	public int getTitleVerticalAlignment() {
		return titleVerticalAlignment;
	}

	public void setStroke(Paint stroke) {
		this.stroke = stroke;
	}

	public void setThickness(Insets thickness) {
		this.thickness = thickness;
	}

	public void setHeaderStroke(Paint headerStroke) {
		this.headerStroke = headerStroke;
	}

	public void setHeaderThickness(Insets headerThickness) {
		this.headerThickness = headerThickness;
	}

	public void setHeaderFill(Paint headerFill) {
		this.headerFill = headerFill;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleIcon(Icon titleIcon) {
		this.titleIcon = titleIcon;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public void setTitleForeground(Color titleForeground) {
		this.titleForeground = titleForeground;
	}

	public void setTitleMargin(Insets titleMargin) {
		this.titleMargin = titleMargin;
	}

	public void setTitleHorizontalAlignment(int titleHorizontalAlignment) {
		this.titleHorizontalAlignment = titleHorizontalAlignment;
	}

	public void setTitleVerticalAlignment(int titleVerticalAlignment) {
		this.titleVerticalAlignment = titleVerticalAlignment;
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private int getHeaderHeight(Component c) {
		int h = 0;
		if (headerStroke != null) h += headerThickness.top + headerThickness.bottom;
		h += titleMargin.top + titleMargin.bottom;
		h += getLabel(c).getPreferredSize().height;
		return h;
	}

	private JLabel getLabel(Component c) {
		label.setText(title);
		label.setIcon(titleIcon);
		label.setForeground(titleForeground != null ? titleForeground : c.getForeground());
		label.setFont(titleFont != null ? titleFont : c.getFont());
		label.setHorizontalAlignment(titleHorizontalAlignment);
		label.setVerticalAlignment(titleVerticalAlignment);
		return label;
	}
}
