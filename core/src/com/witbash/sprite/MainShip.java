package com.witbash.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Sprite;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.sound.SoundGame;

public class MainShip extends Sprite {

    Vector2 v0 = new Vector2(0.5f, 0);
    Vector2 v = new Vector2();

    private Vector2 vLeft = new Vector2();
    private Vector2 vRight = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    private BulletPool bulletPool;
    private TextureAtlas atlas;

    private Rect worldBounds;
    private SoundGame soundGame = new SoundGame();

    float xLeft;
    float xRight;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.atlas = atlas;
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
    }

    @Override
    public void update(float delta) {
        if (checkWorldBoundsLeft() && checkWorldBoundsRight()) pos.mulAdd(v, delta);
        else if (!checkWorldBoundsLeft()) pos.mulAdd(vRight, delta);
        else if (!checkWorldBoundsRight()) pos.mulAdd(vLeft, delta);
        xLeft = getLeft();
        xRight = getRight();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) moveRight();
                else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) moveLeft();
                else stop();
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                shoot();
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x >= pos.x) { // в зависимости от того, в какой
            // стороне от корабля произошло нажатие
            moveRight();
        } else {
            moveLeft();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        stop();
        return false;
    }

    public void moveRight() {
        v.set(v0);
        vRight.set(v0);
    }

    public void moveLeft() {
        v.set(v0).rotate(180);
        vLeft.set(v0).rotate(180);
    }

    public void stop() {
        v.setZero();
        vLeft.setZero();
        vRight.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, atlas.findRegion("bulletMainShip"),
                pos, new Vector2(0, 0.5f), 0.01f, 1, worldBounds);
        soundGame.soundShoot.play();
    }

    public boolean checkWorldBoundsLeft() {
        if (xLeft >= worldBounds.getLeft() + 0.01f) {
            return true;
        } else return false;
    }

    public boolean checkWorldBoundsRight() {
        if (xRight <= worldBounds.getRight() - 0.01f) {
            return true;
        } else return false;
    }

    public void soundBulletDispose() {
        soundGame.soundShoot.dispose();
    }
}
