package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class CanvasBackgroundWidget extends Widget {

    private final Texture bgTex;

    public CanvasBackgroundWidget(Assets assets) {
        setFillParent(true);

        bgTex = assets.getTransparentLightTex();
        bgTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color col = getColor();
        batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
        batch.draw(bgTex, getX(), getY(), getWidth(), getHeight(),
                0f, 0f, getWidth()/bgTex.getWidth(), getHeight()/bgTex.getHeight());
    }
}
