package aurelienribon.texturepackergui.canvas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Assets extends AssetManager {
	private final AssetManager manager;
	private boolean initialized = false;
	private BitmapFont font;

	public Assets() {
		manager = new AssetManager();
	}

	public void loadAll() {
		if (initialized) {
			throw new IllegalStateException("Assets are already been initialized");
		}
		initialized = true;

		manager.load("data/transparent-light.png", Texture.class);
		manager.load("data/transparent-dark.png", Texture.class);
		manager.load("data/canvas.atlas", TextureAtlas.class);
		while (!manager.update()) {}

		font = new BitmapFont();
		font.getData().markupEnabled = true;
	}

	public void dispose() {
		if (!initialized) {
			throw new IllegalStateException("Assets are not initialized yet");
		}
		initialized = false;
		manager.dispose();
		font.dispose();
	}

	public AssetManager getManager() {
		return manager;
	}

	public Texture getTransparentLightTex() {return manager.get("data/transparent-light.png", Texture.class);}
	public Texture getTransparentDarkTex() {return manager.get("data/transparent-dark.png", Texture.class);}
	public TextureRegion getWhiteTex() {return getCanvasAtlas().findRegion("white");}
	public TextureAtlas getCanvasAtlas() {return manager.get("data/canvas.atlas", TextureAtlas.class);}

	public BitmapFont getFont() {
		return font;
	}
}
