package com.witbash.pool;

import com.witbash.base.SpritesPool;
import com.witbash.sprite.Bullet;

public class BulletPool extends SpritesPool <Bullet>{
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
