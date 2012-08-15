package aurelienribon.texturepackergui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Assets extends AssetManager {
	private static AssetManager manager;

	public static void loadAll() {
		manager = new AssetManager();
		manager.load("res/data/transparent-light.png", Texture.class);
		manager.load("res/data/transparent-dark.png", Texture.class);
		manager.load("res/data/white.png", Texture.class);
		manager.load("res/data/splash.pack", TextureAtlas.class);
		while (!manager.update()) {}
	}

	public static Texture getTransparentLightTex() {return manager.get("res/data/transparent-light.png", Texture.class);}
	public static Texture getTransparentDarkTex() {return manager.get("res/data/transparent-dark.png", Texture.class);}
	public static Texture getWhiteTex() {return manager.get("res/data/white.png", Texture.class);}
	public static TextureAtlas getSplashAtlas() {return manager.get("res/data/splash.pack", TextureAtlas.class);}
}
