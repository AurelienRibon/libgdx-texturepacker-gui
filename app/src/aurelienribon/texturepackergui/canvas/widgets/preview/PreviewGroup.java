package aurelienribon.texturepackergui.canvas.widgets.preview;

import aurelienribon.texturepackergui.canvas.Assets;
import aurelienribon.texturepackergui.canvas.AtlasModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class PreviewGroup extends WidgetGroup {
    private static final float SAVE_PADDING = 24f;
    private static final Rectangle TMP_RECT = new Rectangle();
    private static final int[] ZOOM_LEVELS = {5, 10, 16, 25, 33, 50, 66, 100, 150, 200, 300, 400, 600, 800, 1000};
    private static final int DEFAULT_ZOOM_INDEX = 7;

    private final Assets assets;

    private final NoPageHint noPageHint;
    private PagePreview pagePreview;
    private boolean pageMoved;

    private int zoomIndex = DEFAULT_ZOOM_INDEX;
    private Listener listener;

    public PreviewGroup(Assets assets) {
        this.assets = assets;
        setFillParent(true);

        noPageHint = new NoPageHint(assets);
        addActor(noPageHint);

        addActor(new OuterFade(assets));
        addListener(new PanZoomListener());
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);

        if (stage != null) {
            stage.setScrollFocus(this);
        }
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();

        if (!pageMoved) {
            fitPageAtCenter();
        }
        fixPagePosition();
    }

    public void setPage(AtlasModel atlas, int pageIndex) {
        if (atlas == null) throw new IllegalArgumentException("atlas cannot be null");
        if (pagePreview != null && pagePreview.getPage().getPageIndex() == pageIndex) return;

        if (pagePreview != null) { removeActor(pagePreview); }
        pagePreview = new PagePreview(assets, atlas.getPages().get(pageIndex));
        addActor(pagePreview);

        pageMoved = false;
        noPageHint.setVisible(false);

        fitPageAtCenter();
        fixPagePosition();

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void reset() {
        setZoomIndex(DEFAULT_ZOOM_INDEX);
        pageMoved = false;
        if (pagePreview != null) {
            removeActor(pagePreview);
            pagePreview = null;
        }
        noPageHint.setVisible(true);
    }

    private void setZoomIndex(int zoomIndex) {
        if (pagePreview == null) return;

        this.zoomIndex = zoomIndex;
        int zoomPercentage = ZOOM_LEVELS[zoomIndex];
        float pageScale = (float)zoomPercentage / 100f;
        pagePreview.setScale(pageScale);

        if (listener != null) {
            listener.onZoomChanged(ZOOM_LEVELS[zoomIndex]);
        }
    }

    private boolean isHitPage(float x, float y) {
        if (pagePreview == null) return false;

        return TMP_RECT.set(
                pagePreview.getX(),
                pagePreview.getY(),
                pagePreview.getWidth() * pagePreview.getScaleX(),
                pagePreview.getHeight() * pagePreview.getScaleY())
                .contains(x, y);
    }

    private void fitPageAtCenter() {
        if (pagePreview == null) return;
        if (getWidth() <= 0f || getHeight() <= 0f) return;

        for (int i = ZOOM_LEVELS.length - 1; i >= 0; i--) {
            float zoomScale = (float)ZOOM_LEVELS[i] / 100f;
            float width = pagePreview.getWidth() * zoomScale;
            float height = pagePreview.getHeight() * zoomScale;

            if (width <= getWidth() && height <= getHeight()) {
                setZoomIndex(i);
                break;
            }
        }

        pagePreview.setPosition(
                (getWidth() - pagePreview.getWidth() * pagePreview.getScaleX()) * 0.5f,
                (getHeight() - pagePreview.getHeight() * pagePreview.getScaleY()) * 0.5f);
    }

    private void fixPagePosition() {
        if (pagePreview == null) return;

        float x = pagePreview.getX();
        float y = pagePreview.getY();
        float width = pagePreview.getWidth() * pagePreview.getScaleX();
        float height = pagePreview.getHeight() * pagePreview.getScaleY();
        float availableWidth = getWidth();
        float availableHeight = getHeight();

        if (x < -width + SAVE_PADDING) pagePreview.setX(-width + SAVE_PADDING);
        if (x > availableWidth - SAVE_PADDING) pagePreview.setX(availableWidth - SAVE_PADDING);
        if (y < -height + SAVE_PADDING) pagePreview.setY(-height + SAVE_PADDING);
        if (y > availableHeight - SAVE_PADDING) pagePreview.setY(availableHeight - SAVE_PADDING);
    }

    public interface Listener {
        void onZoomChanged(int percentage);
    }

    private class PanZoomListener extends InputListener {

        private final Vector2 lastPos = new Vector2();
        private boolean dragging = false;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (pagePreview == null) return false;
            if (button != 0) return false;
//            if (!isHitPage(x, y)) return false;

            dragging = true;
            lastPos.set(x, y);
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (dragging) {
                pagePreview.setPosition(
                        pagePreview.getX() - lastPos.x + x,
                        pagePreview.getY() - lastPos.y + y);

                fixPagePosition();
                lastPos.set(x, y);
                pageMoved = true;
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (button != 0) return;

            dragging = false;
        }

        @Override
        public boolean scrolled(InputEvent event, float x, float y, int amount) {
            if (pagePreview == null) return false;

            float preWidth = pagePreview.getWidth() * pagePreview.getScaleX();
            float preHeight = pagePreview.getHeight() * pagePreview.getScaleY();
            float xNormalized = x < pagePreview.getX() ? 0f : x > pagePreview.getX()+preWidth ? 1f : (x-pagePreview.getX())/preWidth;
            float yNormalized = y < pagePreview.getY() ? 0f : y > pagePreview.getY()+preHeight ? 1f : (y-pagePreview.getY())/preHeight;

            setZoomIndex(Math.max(0, Math.min(ZOOM_LEVELS.length-1, zoomIndex - amount)));

            float postWidth = pagePreview.getWidth() * pagePreview.getScaleX();
            float postHeight = pagePreview.getHeight() * pagePreview.getScaleY();
            pagePreview.setPosition(
                    pagePreview.getX() + (preWidth - postWidth) * xNormalized,
                    pagePreview.getY() + (preHeight - postHeight) * yNormalized);

            fixPagePosition();
            return true;
        }
    }

    /** Draws fade outside of page (when  page is present) */
    private class OuterFade extends Widget {
        private final Color COLOR_DIM = new Color(0x11112288);

        private final TextureRegion whiteTexture;

        public OuterFade(Assets assets) {
            whiteTexture = assets.getCanvasAtlas().findRegion("white");
            setFillParent(true);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            if (pagePreview == null) return;

            Color col;
            float x = pagePreview.getX();
            float y = pagePreview.getY();
            float width = pagePreview.getWidth() * pagePreview.getScaleX();
            float height = pagePreview.getHeight() * pagePreview.getScaleY();

            // Fading all around page
            col = COLOR_DIM;
            batch.setColor(col.r, col.g, col.b, col.a * getColor().a * parentAlpha);
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + y + height,
                    getWidth(),
                    getHeight());
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + y - getHeight(),
                    getWidth(),
                    getHeight());
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + y,
                    x,
                    height);
            batch.draw(whiteTexture,
                    getX() + x + width,
                    getY() + y,
                    getWidth(),
                    height);
        }
    }
}
