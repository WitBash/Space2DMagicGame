package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Ship;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 vStarting = new Vector2();
    private Vector2 v0 = new Vector2();


    public Enemy(BulletPool bulletPool, Rect worldBounds) {
        super();
        this.v.set(v0);
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speedOfAppearanceOfTheEnemyShip(), delta);
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            shoot();
            reloadTimer = 0f;
        }
        if (isOutside(worldBounds)) destroy();
    }

    public void set(
            TextureRegion[] regions,
            Vector2 vStarting,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.vStarting.set(vStarting);
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
    }

    private Vector2 speedOfAppearanceOfTheEnemyShip() {
        if (getTop() > worldBounds.getTop()) {
            return v.set(vStarting);
        } else return v.set(v0);
    }
}
