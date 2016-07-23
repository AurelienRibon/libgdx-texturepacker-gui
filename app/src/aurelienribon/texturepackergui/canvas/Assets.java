package aurelienribon.texturepackergui.canvas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Assets extends AssetManager {
	private final AssetManager manager;
	private boolean initialized = false;

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
		manager.load("data/white.png", Texture.class);
		manager.load("data/splash.pack", TextureAtlas.class);
		while (!manager.update()) {}
	}

	public void dispose() {
		if (!initialized) {
			throw new IllegalStateException("Assets are not initialized yet");
		}
		initialized = false;
		manager.dispose();
	}

	public AssetManager getManager() {
		return manager;
	}

	public Texture getTransparentLightTex() {return manager.get("data/transparent-light.png", Texture.class);}
	public Texture getTransparentDarkTex() {return manager.get("data/transparent-dark.png", Texture.class);}
	public Texture getWhiteTex() {return manager.get("data/white.png", Texture.class);}
	public TextureAtlas getSplashAtlas() {return manager.get("data/splash.pack", TextureAtlas.class);}
}
