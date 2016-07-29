package aurelienribon.texturepackergui.canvas.widgets.preview;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

class NoPageHint extends Container<Label> {
    public NoPageHint(Assets assets) {
        Label lblMessage = new Label("[#ffffffaa]PACK ATLAS TO SEE PAGES", new Label.LabelStyle(assets.getFont(), new Color(0xffffffff)));
        setActor(lblMessage);

        setFillParent(true);
        align(Align.center);
        setTouchable(Touchable.disabled);
        setBackground(new TextureRegionDrawable(assets.getWhiteTex()).tint(new Color(0x00000040)), false);
    }
}
