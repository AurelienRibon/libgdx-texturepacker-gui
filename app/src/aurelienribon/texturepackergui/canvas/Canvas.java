package aurelienribon.texturepackergui.canvas;

import aurelienribon.texturepackergui.canvas.widgets.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class Canvas extends ApplicationAdapter {

	private final List<Sprite> sprites = new ArrayList<Sprite>();
	private Callback callback;
	private Assets assets;

	private TextureAtlas atlas;
	private int index = 0;

	private Stage stage;

	private PagePreview pagePreview;
	private InfoPanel infoPanel;

	@Override
	public void create() {
		assets = new Assets();
		assets.loadAll();

		stage = new Stage(new ScreenViewport());

		Gdx.input.setInputProcessor(stage);

		// Stage layout
		{
			// Background
			{
				CanvasBackgroundWidget canvasBackgroundWidget = new CanvasBackgroundWidget(assets);
				stage.addActor(canvasBackgroundWidget);
			}

			// Page preview
			{
				pagePreview = new PagePreview(assets);
				pagePreview.setListener(new PagePreview.Listener() {
					@Override
					public void onZoomChanged(int percentage) {
						infoPanel.setZoomLevel(percentage);
					}
				});

				stage.addActor(pagePreview);
			}

			// Page buttons
			{
				VerticalGroup verticalGroup = new VerticalGroup();
				verticalGroup.space(6f);

				PageChangeButton btnNextPage = new PageChangeButton(assets, "Next page");
				PageChangeButton btnPrevPage = new PageChangeButton(assets, "Previous page");

				btnNextPage.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						showNextPage();
					}
				});
				btnPrevPage.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						showPrevPage();
					}
				});

				verticalGroup.addActor(btnNextPage);
				verticalGroup.addActor(btnPrevPage);

				Container container = new Container<>(verticalGroup);
				container.setFillParent(true);
				container.align(Align.topRight);
				container.padTop(10f);
				stage.addActor(container);
			}

			// Info pane
			{
				infoPanel = new InfoPanel(assets);

				Container container = new Container<>(infoPanel);
				container.setFillParent(true);
				container.align(Align.bottomLeft);
				stage.addActor(container);
			}

			// SplashScreen
			{
				SplashWidget splashWidget = new SplashWidget(assets);
				stage.addActor(splashWidget);
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	// By some reason LwjglCanvas kills OpenGL context before this method call.
	// Check out for LibGDX fix some time later in here https://github.com/libgdx/libgdx/issues/4203
	@Override
	public void dispose() {
		super.dispose();
		if (atlas != null) atlas.dispose();
		stage.dispose();
		assets.dispose();
	}

	public void reloadPack(String packPath) {
		index = 0;
		sprites.clear();
		pagePreview.reset();
		infoPanel.setPagesAmount(0);
		if (atlas != null) atlas.dispose();

		if (packPath != null) {
			FileHandle packFile = Gdx.files.absolute(packPath);
			if (packFile != null && packFile.exists()) {
				try {
					atlas = new TextureAtlas(packFile);
					List<Texture> textures = new ArrayList<>();

					// We could use simple atlas.getTextures(), but it returns them in random order...
					for (TextureRegion region : atlas.getRegions()) {
						if (!textures.contains(region.getTexture()))
							textures.add(region.getTexture());
					}

					for (Texture tex : textures) {
						Sprite sp = new Sprite(tex);
						sp.setOrigin(sp.getWidth() / 2, sp.getHeight() / 2);
						sp.setPosition(-sp.getOriginX(), -sp.getOriginY());
						sprites.add(sp);
					}

					pagePreview.setPage(sprites.get(index).getTexture());
					infoPanel.setCurrentPage(index+1);
					infoPanel.setPagesAmount(sprites.size());

				} catch (GdxRuntimeException ex) {
					atlas = null;
					sprites.clear();
					callback.atlasError();
				}
			}
		}
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private void showNextPage() {
		if (atlas == null || sprites.size() == 0) return;

		index = index+1 >= sprites.size() ? 0 : index+1;

		pagePreview.setPage(sprites.get(index).getTexture());
		infoPanel.setCurrentPage(index+1);
	}

	private void showPrevPage() {
		if (atlas == null || sprites.size() == 0) return;

		index = index-1 < 0 ? sprites.size()-1 : index-1;

		pagePreview.setPage(sprites.get(index).getTexture());
		infoPanel.setCurrentPage(index+1);
	}

	public interface Callback {
		void atlasError();
	}
}
