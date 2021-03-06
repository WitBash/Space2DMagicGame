package com.witbash.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.pool.ExplosionPool;
import com.witbash.sound.SoundGame;
import com.witbash.sprite.Bullet;
import com.witbash.sprite.Explosion;

public abstract class Ship extends Sprite {

    protected Vector2 v = new Vector2();
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;

    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected  int bulletDamage;

    protected SoundGame soundGame = new SoundGame();

    protected  float reloadInterval;
    protected  float reloadTimer;

    protected  float damageAnimateInterval = 0.1f;
    protected  float damageAnimateTimer;

    protected int hp;
    protected TextureRegion bulletRegion;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public Ship() {
        super();
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion,
                pos, bulletV, bulletHeight, bulletDamage, worldBounds);
        soundGame.soundShoot.play();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer+=delta;
        if(damageAnimateTimer>=damageAnimateInterval) frame = 0;
    }

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(),pos);
        soundGame.soundExplosion.play();
    }

    public void damage(int damage){
        frame = 1;
        damageAnimateTimer = 0f;
        hp-=damage;
        if(hp<=0) destroy();
    }

    public int getHp() {
        return hp;
    }
}
