package com.witbash.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Ship;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;

    private Vector2 v0 = new Vector2(0.5f, 0);

    private Vector2 vLeft = new Vector2();
    private Vector2 vRight = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private float xLeft;
    private float xRight;


    public MainShip(TextureAtlas atlas, ExplosionPool explosionPool, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.bulletV.set(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.explosionPool = explosionPool;
        this.hp = 100;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (checkWorldBoundsLeft() && checkWorldBoundsRight()) pos.mulAdd(v, delta);
        else if (!checkWorldBoundsLeft()) pos.mulAdd(vRight, delta);
        else if (!checkWorldBoundsRight()) pos.mulAdd(vLeft, delta);
        xLeft = getLeft();
        xRight = getRight();
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            shoot();
            reloadTimer = 0f;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
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
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        } else {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else stop();
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else stop();
        }
        return super.touchUp(touch, pointer);
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

    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }

    @Override
    public void destroy() {
        boom();
        hp = 0;
        super.destroy();
    }
}
