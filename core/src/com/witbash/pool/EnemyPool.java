package com.witbash.pool;

import com.witbash.base.SpritesPool;
import com.witbash.math.Rect;
import com.witbash.sprite.Enemy;

public class EnemyPool extends SpritesPool <Enemy>{

    private  BulletPool bulletPool;
    private Rect worldBounds;


    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,worldBounds);
    }
}
