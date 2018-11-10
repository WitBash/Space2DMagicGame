package com.witbash.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.Base2DScreen;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.pool.EnemyPool;
import com.witbash.sound.SoundGame;
import com.witbash.sprite.Background;
import com.witbash.sprite.MainShip;
import com.witbash.sprite.Star;
import com.witbash.utils.EnemiesEmmiter;

public class PlayScreen extends Base2DScreen {

    private static final int STAR_COUNT = 128;

    private Background background;
    private Texture bgTexture;

    private TextureAtlas textureAtlas;
    private Star[] stars;

    private MainShip mainShip;

    private BulletPool bulletPool;

    private SoundGame soundGame = new SoundGame();

    private EnemyPool enemyPool;
    private EnemiesEmmiter enemiesEmmiter;

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(textureAtlas, bulletPool);
        enemyPool = new EnemyPool(bulletPool, worldBounds);
        enemiesEmmiter = new EnemiesEmmiter(enemyPool, worldBounds, textureAtlas);
        soundGame.musicPlayScreen.setLooping(true);
        soundGame.musicPlayScreen.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveObjects(delta);

        enemyPool.updateActiveObjects(delta);
        enemiesEmmiter.generate(delta);
    }

    public void checkCollisions() {
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        soundGame.musicPlayScreen.dispose();
        mainShip.soundBulletDispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
