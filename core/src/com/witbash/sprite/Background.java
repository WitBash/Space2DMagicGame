package com.witbash.sprite;

import com.witbash.base.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.witbash.math.Rect;


public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
