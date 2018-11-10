package com.witbash.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.witbash.base.SpritesPool;
import com.witbash.sprite.Explosion;

public class ExplosionPool extends SpritesPool <Explosion>{

    TextureRegion region;

    public ExplosionPool(TextureAtlas atlas) {
        this.region = atlas.findRegion("explosion");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(region,9,9,74);
    }


}
