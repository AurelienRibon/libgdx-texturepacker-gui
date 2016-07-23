package aurelienribon.texturepackergui.canvas;

import aurelienribon.accessors.SpriteAccessor;
import aurelienribon.texturepackergui.PanZoomInputProcessor;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Canvas extends ApplicationAdapter {
	private static Canvas instance;

	private final List<Sprite> sprites = new ArrayList<Sprite>();
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer drawer;
	private InputMultiplexer inputMultiplexer;
	private PanZoomInputProcessor panZoomInputProcessor;
	private Callback callback;
	private Assets assets;

	private Sprite infoLabel;
	private Label lblNextPage;
	private Label lblPreviousPage;
	private Texture bgTex;
	private TextureAtlas atlas;
	private int index = 0;

	private boolean previousPageRequested = false;
	private boolean nextPageRequested = false;
	private boolean packReloadRequested = false;
	private FileHandle packFile = null;

	private Sprite splashBack;
	private Sprite splashGdxLogo;
	private Sprite splashTexture;
	private Sprite splashTitle;
	private final TweenManager tweenManager = new TweenManager();

	/** Singleton accessor */
	public static Canvas inst() {
		if (instance == null) {
			throw new IllegalStateException("Canvas is not initialized yet");
		}
		return instance;
	}

	@Override
	public void create() {
		instance = this;

		assets = new Assets();
		assets.loadAll();

//		Texture.setEnforcePotImages(false);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		// General

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		font = new BitmapFont();
		drawer = new ShapeRenderer();

		//Labels

		infoLabel = new Sprite(assets.getWhiteTex());
		infoLabel.setPosition(0, 0);
		infoLabel.setSize(140, 80);
		infoLabel.setColor(new Color(0x2A/255f, 0x3B/255f, 0x56/255f, 180/255f));

		int lblH = 25;
		Color lblC = new Color(0x2A/255f, 0x6B/255f, 0x56/255f, 180/255f);
		lblNextPage = new Label(10+lblH, 120, lblH, "Next page", font, lblC, Label.Anchor.TOP_RIGHT);
		lblPreviousPage = new Label(15+lblH*2, 120, lblH, "Previous page", font, lblC, Label.Anchor.TOP_RIGHT);

		lblNextPage.setCallback(new Label.TouchCallback() {
			@Override public void touchDown(Label source) {
				nextPageRequested = true;
			}
		});

		lblPreviousPage.setCallback(new Label.TouchCallback() {
			@Override public void touchDown(Label source) {
				previousPageRequested = true;
			}
		});

		lblNextPage.show();
		lblPreviousPage.show();

		// Background

		bgTex = assets.getTransparentLightTex();
		bgTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		// Input

		panZoomInputProcessor = new PanZoomInputProcessor(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(panZoomInputProcessor);
		inputMultiplexer.addProcessor(buttonsInputProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);

		// Splash screen

		splashBack = new Sprite(assets.getWhiteTex());
		splashGdxLogo = assets.getSplashAtlas().createSprite("gdxlogo");
		splashTexture = assets.getSplashAtlas().createSprite("texture");
		splashTitle = assets.getSplashAtlas().createSprite("title");

		// Post animation for one frame just to let Gdx context initialize with screen sizes.
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				float w = Gdx.graphics.getWidth();
				float h = Gdx.graphics.getHeight();

				splashBack.setSize(w, 100);
				splashBack.setOrigin(splashBack.getWidth()/2, splashBack.getHeight()/2);
				splashBack.setColor(42/255f, 59/255f, 87/255f, 0.8f);

				Timeline.createSequence()
                    .push(Tween.set(splashBack, SpriteAccessor.CPOS_XY).target(w/2, h/2))
                    .push(Tween.set(splashBack, SpriteAccessor.SCALE_XY).target(1, 0))
                    .push(Tween.set(splashTexture, SpriteAccessor.CPOS_XY).target(-splashTexture.getWidth(), h/2))
                    .push(Tween.set(splashGdxLogo, SpriteAccessor.CPOS_XY).target(w+splashGdxLogo.getWidth(), h/2+15))
                    .push(Tween.set(splashTitle, SpriteAccessor.CPOS_XY).target(w+splashTitle.getWidth(), h/2-15))
                    .pushPause(1.5f)
                    .push(Tween.to(splashBack, SpriteAccessor.SCALE_XY, 1).target(1, 1).ease(Back.OUT))
                    .push(Tween.to(splashTexture, SpriteAccessor.CPOS_XY, 0.6f).target(w/2-80, h/2))
                    .pushPause(-0.4f)
                    .push(Tween.to(splashGdxLogo, SpriteAccessor.CPOS_XY, 0.6f).target(w/2, h/2+15))
                    .pushPause(-0.4f)
                    .push(Tween.to(splashTitle, SpriteAccessor.CPOS_XY, 0.6f).target(w/2+58, h/2-15))
                    .pushPause(0.6f)
                    .beginParallel()
                        .push(Tween.to(splashTexture, SpriteAccessor.OPACITY, 0.6f).target(0))
                        .push(Tween.to(splashGdxLogo, SpriteAccessor.OPACITY, 0.6f).target(0))
                        .push(Tween.to(splashTitle, SpriteAccessor.OPACITY, 0.6f).target(0))
                    .end()
                    .pushPause(-0.4f)
                    .push(Tween.to(splashBack, SpriteAccessor.SCALE_XY, 1).target(1, 0).ease(Back.IN))
                    .start(tweenManager);
			}
		});
	}

	@Override
	public void render() {
		tweenManager.update(Gdx.graphics.getDeltaTime());

		if (previousPageRequested) {
			previousPageRequested = false;
			index = index-1 < 0 ? sprites.size()-1 : index-1;
		}

		if (nextPageRequested) {
			nextPageRequested = false;
			index = index+1 >= sprites.size() ? 0 : index+1;
		}

		if (packReloadRequested) {
			packReloadRequested = false;
			index = 0;
			panZoomInputProcessor.reset();

			sprites.clear();
			if (atlas != null) atlas.dispose();

			if (packFile != null && packFile.exists()) {
				try {
					atlas = new TextureAtlas(packFile);
					List<Texture> textures = new ArrayList<Texture>();

					for (AtlasRegion region : atlas.getRegions()) {
						if (!textures.contains(region.getTexture()))
							textures.add(region.getTexture());
					}

					for (Texture tex : textures) {
						Sprite sp = new Sprite(tex);
						sp.setOrigin(sp.getWidth()/2, sp.getHeight()/2);
						sp.setPosition(-sp.getOriginX(), -sp.getOriginY());
						sprites.add(sp);
					}

				} catch (GdxRuntimeException ex) {
					atlas = null;
					sprites.clear();
					callback.atlasError();
				}
			}
		}

		// Render

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		float tw = bgTex.getWidth();
		float th = bgTex.getHeight();

		batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
		batch.begin();
		batch.disableBlending();
		batch.draw(bgTex, 0f, 0f, w, h, 0f, 0f, w/tw, h/th);
		batch.enableBlending();
		batch.end();

		if (!sprites.isEmpty()) {
			Sprite sp = sprites.get(index);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			sp.draw(batch);
			batch.end();

			drawer.setProjectionMatrix(camera.combined);
			drawer.begin(ShapeRenderer.ShapeType.Line);
			drawer.setColor(Color.BLACK);
			drawer.rect(sp.getX(), sp.getY(), sp.getWidth(), sp.getHeight());
			drawer.end();
		}

		batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
		batch.begin();
		font.setColor(Color.WHITE);
		lblNextPage.draw(batch);
		lblPreviousPage.draw(batch);
		infoLabel.draw(batch);
		if (sprites.isEmpty()) font.draw(batch, "No page to show", 10, 65);
		else font.draw(batch, "Page " + (index + 1) + " / " + sprites.size(), 10, 65);
		font.draw(batch, String.format(Locale.US, "Zoom: %.0f %%", 100f / camera.zoom), 10, 45);
		font.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 10, 25);
		splashBack.draw(batch);
		splashGdxLogo.draw(batch);
		splashTexture.draw(batch);
		splashTitle.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.gl.glViewport(0, 0, width, height);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void dispose() {
		super.dispose();
		// By some reason LwjglCanvas kills OpenGL context before this method call.
		// Check out for LibGDX fix some time later in here https://github.com/libgdx/libgdx/issues/4203
		assets.dispose();
	}

	public Vector2 screenToWorld(int x, int y) {
		Vector3 v3 = new Vector3(x, y, 0);
		camera.unproject(v3);
		return new Vector2(v3.x, v3.y);
	}

	public void requestPackReload(String packPath) {
		packReloadRequested = true;
		if (packPath != null) packFile = Gdx.files.absolute(packPath);
		else packFile = null;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	//region Accessors
	public OrthographicCamera getCamera() {
		return camera;
	}
	public Assets getAssets() {
		return assets;
	}
	//endregion

	public interface Callback {
		void atlasError();
	}

	private final InputProcessor buttonsInputProcessor = new InputAdapter() {
		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {
			if (button == Input.Buttons.LEFT) {
				if (lblNextPage.touchDown(x, y)) return true;
				if (lblPreviousPage.touchDown(x, y)) return true;
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int x, int y) {
			lblNextPage.touchMoved(x, y);
			lblPreviousPage.touchMoved(x, y);
			return false;
		}
	};
}
