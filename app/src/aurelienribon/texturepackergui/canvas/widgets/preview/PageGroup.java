package aurelienribon.texturepackergui.canvas.widgets.preview;

import aurelienribon.texturepackergui.canvas.Assets;
import aurelienribon.texturepackergui.canvas.AtlasModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

class PageGroup extends Group {
    private static final Vector2 tmpCoords = new Vector2();
    private static final Rectangle tmpBounds = new Rectangle();

    private final AtlasModel.Page page;
    private final NinePatchDrawable borderFrame;

    public PageGroup(Assets assets, AtlasModel.Page page) {
        this.page = page;
        setTransform(false);

        borderFrame = new NinePatchDrawable(assets.getCanvasAtlas().createPatch("white_frame")).tint(Color.BLACK);

        setSize(page.getTexture().getWidth(), page.getTexture().getHeight());
        setTouchable(Touchable.disabled);

        addActor(new RegionSpotlight(assets));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = getY();
        float width = getWidth() * getScaleX();
        float height = getHeight() * getScaleY();

        Color col = getColor();
        batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
        batch.draw(page.getTexture(), x, y, width, height);

        batch.setColor(Color.BLACK);
        borderFrame.draw(batch, x, y, width, height);

        // Draws children after all
        super.draw(batch, parentAlpha);
    }

    public AtlasModel.Page getPage() {
        return page;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private Vector2 screenToLocal(int screenX, int screenY) {
        Vector2 screenCoords = tmpCoords.set(screenX, screenY);
        Vector2 stageCoords = getStage().screenToStageCoordinates(screenCoords);
        Vector2 localCoords = stageToLocalCoordinates(stageCoords);
        return localCoords;
    }

    private class RegionSpotlight extends Actor {
        private final Color colorSpotlight = new Color(0xff9500ff);
        private final Color colorTextFrame = new Color(0x2a6b56aa);
        private final float framePad = 1f;

        private final TextureRegion whiteTex;
        private final NinePatch spotlightBorder;
        private final BitmapFont font;
        private final GlyphLayout glText;

        private boolean active;
        private AtlasRegion region;

        public RegionSpotlight(Assets assets) {
            whiteTex = assets.getWhiteTex();
            spotlightBorder = assets.getCanvasAtlas().createPatch("white_frame");
            font = assets.getFont();
            glText = new GlyphLayout();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            if (region == null) return;

            // Frame
            float scale = PageGroup.this.getScaleX();
            float x = getX() + (region.getRegionX() - framePad) * scale;
            float y = getY() + (region.getTexture().getHeight() - region.getRegionY() - region.getRegionHeight() - framePad) * scale; // Texture region has top-left axis origin
            float width = (region.getRegionWidth() + framePad*2f) * scale;
            float height = (region.getRegionHeight() + framePad*2f) * scale;

            batch.setColor(colorSpotlight);
            spotlightBorder.draw(batch, x, y, width, height);

            // Text
            float textX = x + width*0.5f - glText.width*0.5f;
            float textY = y - glText.height - 4f;
            batch.setColor(colorTextFrame);
            batch.draw(whiteTex, textX-10f, textY - 6f, glText.width+20f, glText.height + 10f);
            batch.setColor(Color.WHITE);
            font.getData().setScale(1f);
            font.draw(batch, glText, textX, textY + glText.height);
        }

        @Override
        public void act(float delta) {
            super.act(delta);

            PageGroup pp = PageGroup.this;
            Vector2 pointerPos = pp.screenToLocal(Gdx.input.getX(), Gdx.input.getY());
            boolean withinPage = tmpBounds.set(0f, 0f, pp.getWidth(), pp.getHeight()).contains(pointerPos);

            if (!withinPage && active) {
                clearSpotlight();
            }

            if (withinPage) {
                AtlasRegion region = hitRegion(pointerPos);
                if (region != null) {
                    spotlightRegion(region);
                }

                if (region == null && active) {
                    clearSpotlight();
                }
            }
        }

        private void clearSpotlight() {
            region = null;
            active = false;
        }

        private void spotlightRegion(AtlasRegion region) {
            if (this.region == region) return;

            this.region = region;
            active = true;

            // You can customize what is shown when user hover on region in here
            StringBuilder sb = new StringBuilder();
            sb.append(region.name);
            if (region.index >= 0) {
                sb.append("[#fbf236ff] idx:" + region.index);
            }

            font.getData().setScale(1f);
            glText.setText(font, sb.toString(), Color.WHITE, 0f, Align.left, false);
        }

        private AtlasRegion hitRegion(Vector2 position) {
            for (AtlasRegion region : page.getRegions()) {
                if (tmpBounds.set(
                        region.getRegionX(),
                        region.getTexture().getHeight() - region.getRegionY() - region.getRegionHeight(), // Texture region has top-left axis origin
                        region.getRegionWidth(),
                        region.getRegionHeight())
                        .contains(position)) {
                    return region;
                }
            }
            return null;
        }
    }
}
