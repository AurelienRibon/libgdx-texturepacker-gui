package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.accessors.SpriteAccessor;
import aurelienribon.texturepackergui.canvas.Assets;
import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Back;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.crashinvaders.common.scene2d.actions.ActionsExt;

public class SplashWidget extends Widget {
    static {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
    }

    private final TweenManager tweenManager;
    private final Sprite splashBack;
    private final Sprite splashGdxLogo;
    private final Sprite splashTexture;
    private final Sprite splashTitle;

    public SplashWidget(Assets assets) {
        setTouchable(Touchable.disabled);
        setFillParent(true);
        tweenManager = new TweenManager();

        splashBack = new Sprite(assets.getWhiteTex());
        splashGdxLogo = assets.getCanvasAtlas().createSprite("gdxlogo");
        splashTexture = assets.getCanvasAtlas().createSprite("texture");
        splashTitle = assets.getCanvasAtlas().createSprite("title");

        // Post animation for one frame just to let widget to be laid out.
        addAction(ActionsExt.post(Actions.run(new Runnable() {
            public void run() {
                float w = getWidth();
                float h = getHeight();
                splashBack.setSize(w, 100);
                splashBack.setOrigin(splashBack.getWidth() / 2, splashBack.getHeight() / 2);
                splashBack.setColor(42 / 255f, 59 / 255f, 87 / 255f, 0.8f);
                Timeline.createSequence()
                        .push(Tween.set(splashBack, SpriteAccessor.CPOS_XY).target(w / 2, h / 2))
                        .push(Tween.set(splashBack, SpriteAccessor.SCALE_XY).target(1, 0))
                        .push(Tween.set(splashTexture, SpriteAccessor.CPOS_XY).target(-splashTexture.getWidth(), h / 2))
                        .push(Tween.set(splashGdxLogo, SpriteAccessor.CPOS_XY).target(w + splashGdxLogo.getWidth(), h / 2 + 15))
                        .push(Tween.set(splashTitle, SpriteAccessor.CPOS_XY).target(w + splashTitle.getWidth(), h / 2 - 15))
                        .pushPause(0.5f)
                        .push(Tween.to(splashBack, SpriteAccessor.SCALE_XY, 0.5f).target(1, 1).ease(Back.OUT))
                        .push(Tween.to(splashTexture, SpriteAccessor.CPOS_XY, 0.6f).target(w / 2 - 80, h / 2))
                        .pushPause(-0.4f)
                        .push(Tween.to(splashGdxLogo, SpriteAccessor.CPOS_XY, 0.6f).target(w / 2, h / 2 + 15))
                        .pushPause(-0.4f)
                        .push(Tween.to(splashTitle, SpriteAccessor.CPOS_XY, 0.6f).target(w / 2 + 58, h / 2 - 15))
                        .pushPause(0.6f)
                        .beginParallel()
                        .push(Tween.to(splashTexture, SpriteAccessor.OPACITY, 0.6f).target(0))
                        .push(Tween.to(splashGdxLogo, SpriteAccessor.OPACITY, 0.6f).target(0))
                        .push(Tween.to(splashTitle, SpriteAccessor.OPACITY, 0.6f).target(0))
                        .end()
                        .pushPause(-0.4f)
                        .push(Tween.to(splashBack, SpriteAccessor.SCALE_XY, 0.5f).target(1, 0).ease(Back.IN))
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                if (type == COMPLETE) remove();
                            }
                        })
                        .start(tweenManager);
            }
        })));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        tweenManager.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        splashBack.draw(batch);
        splashGdxLogo.draw(batch);
        splashTexture.draw(batch);
        splashTitle.draw(batch);
    }
}
