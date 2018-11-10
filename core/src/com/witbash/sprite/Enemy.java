package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Ship;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.pool.EnemyPool;
import com.witbash.pool.ExplosionPool;
import com.witbash.utils.Regions;

public class Enemy extends Ship {

    private Vector2 vStarting = new Vector2();
    private Vector2 v0 = new Vector2();


    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool,Rect worldBounds) {
        super();
        this.v.set(v0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
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
        if (getBottom() < worldBounds.getBottom()) {
            boom();
            destroy();
        }
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

    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }

    private Vector2 speedOfAppearanceOfTheEnemyShip() {
        if (getTop() > worldBounds.getTop()) {
            return v.set(vStarting);
        } else return v.set(v0);
    }

    @Override
    public void destroy() {
        boom();
        hp = 0;
        super.destroy();
    }
}
