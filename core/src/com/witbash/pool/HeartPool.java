package com.witbash.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.witbash.base.SpritesPool;
import com.witbash.math.Rect;
import com.witbash.sprite.Heart;

public class HeartPool extends SpritesPool<Heart> {

    TextureRegion region;
    Rect worldBounds;

    public HeartPool(TextureAtlas atlas, Rect worldBounds) {
        this.region = atlas.findRegion("heart");
        this.worldBounds = worldBounds;
    }

    @Override
    protected Heart newObject() {
        return new Heart(region, worldBounds);
    }

    public void removeHeart(Rect ship) {
        Heart heart = obtain();
        heart.set(0.05f, ship);
    }
}
