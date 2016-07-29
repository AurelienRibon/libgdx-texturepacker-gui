package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class PageChangeButton extends WidgetGroup {
    public static final float WIDTH = 128f;
    public static final float HEIGHT = 26f;

    private static final Color COLOR_REGULAR = new Color(0x2a6b56aa);
    private static final Color COLOR_FOCUSED = new Color(0x2a6b56ff);

    private final Label lblText;
    private final Image imgBackground;

    public PageChangeButton(Assets assets, String text) {

        // Background
        {
            imgBackground = new Image(assets.getWhiteTex());
            imgBackground.setColor(COLOR_REGULAR);
            imgBackground.setScaling(Scaling.stretch);
            imgBackground.setTouchable(Touchable.disabled);

            Container container = new Container<>(imgBackground);
            container.setFillParent(true);
            container.fill();
            addActor(container);
        }

        // Text
        {
            lblText = new Label(text, new Label.LabelStyle(assets.getFont(), Color.WHITE));
            lblText.setTouchable(Touchable.disabled);

            Container container = new Container<>(lblText);
            container.setFillParent(true);
            container.align(Align.left);
            container.padLeft(12f);
            addActor(container);
        }

        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    animateFocused();
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    animateUnfocused();
                }
            }
        });
    }

    private void animateFocused() {
        imgBackground.clearActions();
        imgBackground.addAction(Actions.color(COLOR_FOCUSED, 0.10f));
    }

    private void animateUnfocused() {
        imgBackground.clearActions();
        imgBackground.addAction(Actions.color(COLOR_REGULAR, 0.10f));
    }

    @Override
    public float getPrefWidth() {
        return WIDTH;
    }
    @Override
    public float getPrefHeight() {
        return HEIGHT;
    }
}
