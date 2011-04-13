package aurelienribon.texturepackergui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.ArrayList;
import java.util.List;

public class App implements ApplicationListener {
	private SpriteBatch sb;
	private BitmapFont font;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	private List<Sprite> sprites;
	private int index = 0;

	private Texture backgroundTexture;

	@Override
	public void create() {
		sb = new SpriteBatch();

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		sprites = new ArrayList<Sprite>();

		backgroundTexture = new Texture(Gdx.files.classpath("aurelienribon/texturepackergui/gfx/transparent.png"));
		backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		
		Gdx.input.setInputProcessor(inputProcessor);
		AppEvents.addEventListener(AppEvents.PackDoneListener.class, packDoneListener);
		AppEvents.addEventListener(AppEvents.PreviousPageRequestedListener.class, previousPageRequestedListener);
		AppEvents.addEventListener(AppEvents.NextPageRequestedListener.class, nextPageRequestedListener);
	}

	@Override
	public void resume() {
	}

	@Override
	public void render() {
		GL10 gl = Gdx.gl10;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		float tw = backgroundTexture.getWidth();
		float th = backgroundTexture.getHeight();

		sb.begin();
		sb.draw(backgroundTexture, 0f, 0f, w, h, 0f, 0f, w/tw, h/th);
		sb.end();

		sb.begin();
		camera.apply(gl);
		if (!sprites.isEmpty())
			sprites.get(index).draw(sb);
		sb.end();

		sb.begin();
		font.draw(sb, "Drag to pan, scroll to zoom", 5, 45);
		if (sprites.isEmpty())
			font.draw(sb, "No page to show", 5, 25);
		else
			font.draw(sb, "Page " + (index + 1) + " / " + sprites.size(), 5, 25);
		sb.end();
	}

	private AppEvents.PreviousPageRequestedListener previousPageRequestedListener = new AppEvents.PreviousPageRequestedListener() {
		@Override
		public void onEvent() {
			index = (index - 1) % sprites.size();
		}
	};

	private AppEvents.NextPageRequestedListener nextPageRequestedListener = new AppEvents.NextPageRequestedListener() {
		@Override
		public void onEvent() {
			index = (index + 1) % sprites.size();
		}
	};
	
	private AppEvents.PackDoneListener packDoneListener = new AppEvents.PackDoneListener() {
		@Override
		public void onEvent() {
			index = 0;
			
			if (atlas != null)
				atlas.dispose();
			atlas = new TextureAtlas(Gdx.files.absolute(AppContext.outputDir).child("pack"));

			List<Texture> textures = new ArrayList<Texture>();
			for (AtlasRegion region : atlas.getRegions())
				if (!textures.contains(region.getTexture()))
					textures.add(region.getTexture());

			sprites.clear();
			for (Texture tex : textures) {
				Sprite sp = new Sprite(tex);
				sp.setOrigin(sp.getWidth()/2, sp.getHeight()/2);
				sp.setPosition(-sp.getOriginX(), -sp.getOriginY());
				sprites.add(sp);
			}
		}
	};

	private InputProcessor inputProcessor = new InputAdapter() {
		private int lastTouchX = 0;
		private int lastTouchY = 0;

		@Override
		public boolean scrolled(int amount) {
			camera.zoom *= amount > 0 ? 1.1f : 0.9f;
			camera.update();
			return true;
		}

		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {
			lastTouchX = x;
			lastTouchY = y;
			return true;
		}

		@Override
		public boolean touchDragged(int x, int y, int pointer) {
			camera.position.add((lastTouchX - x) * camera.zoom, (y - lastTouchY) * camera.zoom, 0);
			camera.update();
			lastTouchX = x;
			lastTouchY = y;
			return true;
		}
	};

	// -------------------------------------------------------------------------

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
		if (atlas != null)
			atlas.dispose();
		sb.dispose();
	}
}
