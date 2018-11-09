package com.witbash.pool;

import com.witbash.base.SpritesPool;
import com.witbash.math.Rect;
import com.witbash.sound.SoundGame;
import com.witbash.sprite.Enemy;

public class EnemyPool extends SpritesPool <Enemy>{

    private  BulletPool bulletPool;
    private Rect worldBounds;
//    private SoundGame shootEnemySound;


    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,worldBounds);
    }
}
