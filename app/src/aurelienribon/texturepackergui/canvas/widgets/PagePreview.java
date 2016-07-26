package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class PagePreview extends Widget {
    private static final float SAVE_PADDING = 24f;
    private static final Color COLOR_DIM = new Color(0x11112288);
    private static final Rectangle TMP_RECT = new Rectangle();

    private static final int[] ZOOM_LEVELS = {5, 10, 16, 25, 33, 50, 66, 100, 150, 200, 300, 400, 600, 800, 1000};
    private static final int DEFAULT_ZOOM_INDEX = 7;
    private int zoomIndex = DEFAULT_ZOOM_INDEX;

    private final NinePatchDrawable pageFrame;
    private final TextureRegion whiteTexture;
    private final BitmapFont font;
    private final GlyphLayout glNoPreviewHint;
    private final PanZoomListener panZoomListener;

    private Texture pageTexture;
    private final Vector2 pagePos = new Vector2();
    private float pageScale = 1f;
    private boolean pageMoved;

    private Listener listener;

    public PagePreview(Assets assets) {
        setFillParent(true);

        font = assets.getFont();
        glNoPreviewHint = new GlyphLayout(font, "[#aaaaaaff]pack atlas to see pages");

        pageFrame = new NinePatchDrawable(assets.getCanvasAtlas().createPatch("page_frame")).tint(Color.BLACK);
        whiteTexture = assets.getCanvasAtlas().findRegion("white");

        addListener(panZoomListener = new PanZoomListener());
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);

        if (stage != null) {
            stage.setScrollFocus(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (pageTexture != null) {
            Color col;
            float x = getX() + pagePos.x;
            float y = getY() + pagePos.y;
            float width = pageTexture.getWidth() * pageScale;
            float height = pageTexture.getHeight() * pageScale;

            // Fading all around page
            col = COLOR_DIM;
            batch.setColor(col.r, col.g, col.b, col.a * getColor().a * parentAlpha);
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + pagePos.y + height,
                    getWidth(),
                    getHeight());
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + pagePos.y - getHeight(),
                    getWidth(),
                    getHeight());
            batch.draw(whiteTexture,
                    getX() + 0f,
                    getY() + pagePos.y,
                    pagePos.x,
                    height);
            batch.draw(whiteTexture,
                    getX() + pagePos.x + width,
                    getY() + pagePos.y,
                    getWidth(),
                    height);

            // Drawing page
            col = getColor();
            batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
            batch.draw(pageTexture, x, y, width, height, 0f, 1f, 1f, 0f);

            // Black frame around page
            pageFrame.draw(batch, x, y, width, height);
        } else {
            // Blank image hint
            batch.setColor(0f, 0f, 0f, 0.75f);
            batch.draw(whiteTexture, getX(), getY(), getWidth(), getHeight());

            batch.setColor(Color.WHITE);
            font.draw(batch, glNoPreviewHint,
                    getX() + (getWidth() - glNoPreviewHint.width)*0.5f,
                    getY() + glNoPreviewHint.height + (getHeight() - glNoPreviewHint.height)*0.5f);
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

    public void setPage(Texture pageTexture) {
        if (pageTexture == null) throw new IllegalArgumentException("pageTexture cannot be null");
        if (this.pageTexture == pageTexture) return;

        this.pageTexture = pageTexture;
        pageMoved = false;

        fitPageAtCenter();
        fixPagePosition();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void reset() {
        this.pageTexture = null;
        setZoomIndex(DEFAULT_ZOOM_INDEX);
        pageScale = 1f;
        pageMoved = false;
    }

    private void setZoomIndex(int zoomIndex) {
        this.zoomIndex = zoomIndex;
        int zoomPercentage = ZOOM_LEVELS[zoomIndex];
        pageScale = (float)zoomPercentage / 100f;

        if (listener != null) {
            listener.onZoomChanged(ZOOM_LEVELS[zoomIndex]);
        }
    }

    private boolean isHitPage(float x, float y) {
        if (pageTexture == null) return false;

        return TMP_RECT.set(pagePos.x, pagePos.y, pageTexture.getWidth()*pageScale, pageTexture.getHeight()*pageScale).contains(x, y);
    }

    private void fitPageAtCenter() {
        if (pageTexture == null) return;
        if (getWidth() <= 0f || getHeight() <= 0f) return;

        for (int i = ZOOM_LEVELS.length - 1; i >= 0; i--) {
            float zoomScale = (float)ZOOM_LEVELS[i] / 100f;
            float width = pageTexture.getWidth() * zoomScale;
            float height = pageTexture.getHeight() * zoomScale;

            if (width <= getWidth() && height <= getHeight()) {
                setZoomIndex(i);
                break;
            }
        }

        pagePos.set(
                (getWidth() - pageTexture.getWidth() * pageScale) * 0.5f,
                (getHeight() - pageTexture.getHeight() * pageScale) * 0.5f);

    }

    private void fixPagePosition() {
        if (pageTexture == null) return;

        float x = pagePos.x;
        float y = pagePos.y;
        float width = pageTexture.getWidth() * pageScale;
        float height = pageTexture.getHeight() * pageScale;
        float availableWidth = getWidth();
        float availableHeight = getHeight();

        if (x < -width + SAVE_PADDING) pagePos.x = -width + SAVE_PADDING;
        if (x > availableWidth - SAVE_PADDING) pagePos.x = availableWidth - SAVE_PADDING;
        if (y < -height + SAVE_PADDING) pagePos.y = -height + SAVE_PADDING;
        if (y > availableHeight - SAVE_PADDING) pagePos.y = availableHeight - SAVE_PADDING;
    }

    public interface Listener {
        void onZoomChanged(int percentage);
    }

    private class PanZoomListener extends InputListener {

        private final Vector2 lastPos = new Vector2();
        private boolean dragging = false;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (pageTexture == null) return false;
            if (button != 0) return false;
//            if (!isHitPage(x, y)) return false;

            dragging = true;
            lastPos.set(x, y);
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (dragging) {
                pagePos.sub(lastPos.sub(x, y));
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
            if (pageTexture == null) return false;

            float preWidth = pageTexture.getWidth() * pageScale;
            float preHeight = pageTexture.getHeight() * pageScale;
            float xNormalized = x < pagePos.x ? 0f : x > pagePos.x+preWidth ? 1f : (x-pagePos.x)/preWidth;
            float yNormalized = y < pagePos.y ? 0f : y > pagePos.y+preHeight ? 1f : (y-pagePos.y)/preHeight;

            setZoomIndex(Math.max(0, Math.min(ZOOM_LEVELS.length-1, zoomIndex - amount)));

            float postWidth = pageTexture.getWidth() * pageScale;
            float postHeight = pageTexture.getHeight() * pageScale;
            pagePos.x += (preWidth - postWidth) * xNormalized;
            pagePos.y += (preHeight - postHeight) * yNormalized;

            fixPagePosition();
            return true;
        }
    }
}
