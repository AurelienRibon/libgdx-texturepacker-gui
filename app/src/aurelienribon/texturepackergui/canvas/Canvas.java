package aurelienribon.texturepackergui.canvas;

import aurelienribon.texturepackergui.canvas.widgets.CanvasBackgroundWidget;
import aurelienribon.texturepackergui.canvas.widgets.InfoPanel;
import aurelienribon.texturepackergui.canvas.widgets.PageChangeButton;
import aurelienribon.texturepackergui.canvas.widgets.SplashWidget;
import aurelienribon.texturepackergui.canvas.widgets.preview.PreviewGroup;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Canvas extends ApplicationAdapter {

	private Callback callback;
	private Assets assets;
	private Stage stage;

	private PreviewGroup previewGroup;
	private InfoPanel infoPanel;

	private AtlasModel atlas;
	private int pageIndex = 0;

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
				previewGroup = new PreviewGroup(assets);
				previewGroup.setListener(new PreviewGroup.Listener() {
					@Override
					public void onZoomChanged(int percentage) {
						infoPanel.setZoomLevel(percentage);
					}
				});

				stage.addActor(previewGroup);
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
		stage.dispose();
		assets.dispose();
		if (atlas != null) {
			atlas.dispose();
		}
	}

	public void reloadPack(String packPath) {
		pageIndex = 0;
		previewGroup.reset();
		infoPanel.setPagesAmount(0);
		if (atlas != null) {
			atlas.dispose();
			atlas = null;
		}

		if (packPath != null) {
			FileHandle packFile = Gdx.files.absolute(packPath);
			if (packFile != null && packFile.exists()) {
				try {
					atlas = new AtlasModel(packFile);

					previewGroup.setPage(atlas, pageIndex);
					infoPanel.setCurrentPage(pageIndex +1);
					infoPanel.setPagesAmount(atlas.getPages().size);

				} catch (GdxRuntimeException ex) {
					atlas.dispose();
					atlas = null;
					callback.atlasError();
				}
			}
		}
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private void showNextPage() {
		if (atlas == null || atlas.getPages().size == 0) return;

		pageIndex = pageIndex +1 >= atlas.getPages().size ? 0 : pageIndex+1;

		previewGroup.setPage(atlas, pageIndex);
		infoPanel.setCurrentPage(pageIndex +1);
	}

	private void showPrevPage() {
		if (atlas == null || atlas.getPages().size == 0) return;

		pageIndex = pageIndex -1 < 0 ? atlas.getPages().size-1 : pageIndex-1;

		previewGroup.setPage(atlas, pageIndex);
		infoPanel.setCurrentPage(pageIndex +1);
	}

	public interface Callback {
		void atlasError();
	}
}
