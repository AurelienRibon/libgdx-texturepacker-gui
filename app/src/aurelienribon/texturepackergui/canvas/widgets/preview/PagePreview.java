package aurelienribon.texturepackergui.canvas.widgets.preview;

import aurelienribon.texturepackergui.canvas.Assets;
import aurelienribon.texturepackergui.canvas.AtlasModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

class PagePreview extends Actor {
    private final NinePatchDrawable borderFrame;
    private final AtlasModel.Page page;

    public PagePreview(Assets assets, AtlasModel.Page page) {
        borderFrame = new NinePatchDrawable(assets.getCanvasAtlas().createPatch("page_frame")).tint(Color.BLACK);
        this.page = page;

        setSize(page.getTexture().getWidth(), page.getTexture().getHeight());
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth() * getScaleX();
        float height = getHeight() * getScaleY();

        Color col = getColor();
        batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
        batch.draw(page.getTexture(), x, y, width, height);

        batch.setColor(Color.BLACK);
        borderFrame.draw(batch, x, y, width, height);
    }

    public AtlasModel.Page getPage() {
        return page;
    }
}
