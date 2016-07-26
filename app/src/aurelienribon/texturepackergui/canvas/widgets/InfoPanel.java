package aurelienribon.texturepackergui.canvas.widgets;

import aurelienribon.texturepackergui.canvas.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class InfoPanel extends WidgetGroup {
    public static final float WIDTH = 140f;
    public static final float HEIGHT = 80f;
    private final Label lblCurrentPage;
    private final Label lblZoom;
    private final Label lblFps;

    private int pagesAmount, currentPage;

    public InfoPanel(Assets assets) {
        setTouchable(Touchable.disabled);

        // Background
        {
            Image imgBackground = new Image(assets.getWhiteTex());
            imgBackground.setScaling(Scaling.stretch);
            imgBackground.setColor(new Color(0x2a3b56aa));

            Container container = new Container<>(imgBackground);
            container.setFillParent(true);
            container.fill();
            addActor(container);
        }

        // Labels
        {
            VerticalGroup verticalGroup = new VerticalGroup();
            verticalGroup.align(Align.left);
            verticalGroup.space(4f);

            Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont(), Color.WHITE);
            lblCurrentPage = new Label("", labelStyle);
            lblZoom = new Label("", labelStyle);
            lblFps = new Label("", labelStyle);

            verticalGroup.addActor(lblCurrentPage);
            verticalGroup.addActor(lblZoom);
            verticalGroup.addActor(lblFps);

            Container container = new Container<>(verticalGroup);
            container.setFillParent(true);
            container.left();
            container.padLeft(10f);
            addActor(container);
        }

        updatePagesTest();
        setZoomLevel(100f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        int fps = Gdx.graphics.getFramesPerSecond();
        lblFps.setText("FPS: " + fps);
    }

    @Override
    public float getPrefWidth() { return WIDTH; }

    @Override
    public float getPrefHeight() { return HEIGHT; }

    public void setZoomLevel(float zoom) {
        lblZoom.setText(String.format("Zoom: %.0f%%", zoom));
    }

    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
        updatePagesTest();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        updatePagesTest();
    }

    private void updatePagesTest() {
        if (pagesAmount <= 0) {
            lblCurrentPage.setText("No page to show");
        } else {
            lblCurrentPage.setText("Page " + currentPage + " / " + pagesAmount);
        }
    }
}
