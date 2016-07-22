package aurelienribon.ui.css.swing.functions;

import aurelienribon.ui.css.Function;
import aurelienribon.ui.css.swing.SwingFunctions;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TextureFunction implements Function {
	@Override
	public String getName() {
		return "texture";
	}

	@Override
	public Class[][] getParams() {
		return new Class[][] {
			{URL.class},
			{URL.class, Number.class, Number.class}
		};
	}

	@Override
	public String[][] getParamsNames() {
		return new String[][] {
			{"imageUrl"},
			{"imageUrl", "width", "height"}
		};
	}

	@Override
	public Class getReturn() {
		return TexturePaint.class;
	}

	@Override
	public Object process(List<Object> params) {
		BufferedImage img;

		try {
			img = ImageIO.read((URL) params.get(0));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		if (params.size() == 1) {
			Rectangle2D rect = new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight());
			return new TexturePaint(img, rect);
		}

		if (params.size() == 3) {
			float w = ((Number) params.get(1)).floatValue();
			float h = ((Number) params.get(2)).floatValue();
			Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
			return new TexturePaint(img, rect);
		}

		assert false;
		return null;
	}
}
