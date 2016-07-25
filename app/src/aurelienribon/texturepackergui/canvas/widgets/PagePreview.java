package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private final NinePatchDrawable pageFrame;
    private final TextureAtlas.AtlasRegion whiteTexture;

    private Texture pageTexture;
    private final Vector2 pagePos = new Vector2();
    private float pageScale = 1f;

    public PagePreview(Assets assets) {
        setFillParent(true);

        pageFrame = new NinePatchDrawable(assets.getCanvasAtlas().createPatch("page_frame")).tint(Color.BLACK);
        whiteTexture = assets.getCanvasAtlas().findRegion("white");

        addListener(new DragZoomListener());
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
        }
    }

    public void setPage(Texture pageTexture) {
        this.pageTexture = pageTexture;
    }

    private boolean isHitPage(float x, float y) {
        if (pageTexture == null) return false;

        return TMP_RECT.set(pagePos.x, pagePos.y, pageTexture.getWidth()*pageScale, pageTexture.getHeight()*pageScale).contains(x, y);
    }

    private void fixPagePosition() {
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

    private class DragZoomListener extends InputListener {
        private final Vector2 lastPos = new Vector2();
        private boolean dragging = false;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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

            pageScale = Math.min(3f, Math.max(0.25f, pageScale - amount*0.2f));

            float postWidth = pageTexture.getWidth() * pageScale;
            float postHeight = pageTexture.getHeight() * pageScale;
            pagePos.x += (preWidth - postWidth) * xNormalized;
            pagePos.y += (preHeight - postHeight) * yNormalized;

            fixPagePosition();
            return true;
        }
    }
}
