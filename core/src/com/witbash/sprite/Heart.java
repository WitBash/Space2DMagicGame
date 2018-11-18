package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Sprite;
import com.witbash.math.Rect;

public class Heart extends Sprite {

    private Vector2 vR = new Vector2(0.2f, -0.2f);
    private Vector2 vL = new Vector2(-0.2f, -0.2f);
    private Rect worldBounds;


    public Heart(TextureRegion region, Rect worldBounds) {
        super(region);
        this.worldBounds = worldBounds;
    }

    public void set(float height, Rect ship) {
        this.pos.set(ship.pos.x, ship.pos.y);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (this.pos.x >= 0) pos.mulAdd(vR, delta);
        else pos.mulAdd(vL, delta);
        if (isOutside(worldBounds)) destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
