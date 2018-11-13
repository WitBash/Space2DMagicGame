package com.witbash.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.ActionListener;
import com.witbash.base.Base2DScreen;
import com.witbash.math.Rect;
import com.witbash.pool.BulletPool;
import com.witbash.pool.EnemyPool;
import com.witbash.pool.ExplosionPool;
import com.witbash.sound.SoundGame;
import com.witbash.sprite.Background;
import com.witbash.sprite.Bullet;
import com.witbash.sprite.Enemy;
import com.witbash.sprite.GameOver;
import com.witbash.sprite.MainShip;
import com.witbash.sprite.NewGame;
import com.witbash.sprite.Star;
import com.witbash.utils.EnemiesEmmiter;

import java.util.List;

public class PlayScreen extends Base2DScreen implements ActionListener {

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
    private ExplosionPool explosionPool;

    private GameOver gameOver;
    private NewGame newGame;

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
        explosionPool = new ExplosionPool(textureAtlas);
        bulletPool = new BulletPool();
        mainShip = new MainShip(textureAtlas, explosionPool, bulletPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemiesEmmiter = new EnemiesEmmiter(enemyPool, worldBounds, textureAtlas);
        soundGame.musicPlayScreen.setLooping(true);
        soundGame.musicPlayScreen.setVolume(0.4f);
        soundGame.musicPlayScreen.play();
        gameOver = new GameOver(textureAtlas, this);
        newGame = new NewGame(textureAtlas, this);
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
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveObjects(delta);
            enemyPool.updateActiveObjects(delta);
            explosionPool.updateActiveObjects(delta);
            enemiesEmmiter.generate(delta);
        } else {
            soundGame.musicPlayScreen.dispose();
            soundGame.musicMenuScreen.play();
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) continue;
            if (mainShip.isBulletCollision(bullet)) {
                bullet.destroy();
                mainShip.damage(bullet.getDamage());
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            for (Bullet bullet : bulletList) {
                if (bullet.isDestroyed() || bullet.getOwner() != mainShip) continue;
                if (enemy.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemy.damage(bullet.getDamage());
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
            explosionPool.drawActiveObjects(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        soundGame.musicPlayScreen.dispose();
        soundGame.musicMenuScreen.dispose();
        soundGame.soundExplosion.dispose();
        soundGame.soundShoot.dispose();
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
        newGame.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        newGame.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == newGame) {
            MenuScreen.playScreen.setScreen(new PlayScreen());
        }
    }
}
