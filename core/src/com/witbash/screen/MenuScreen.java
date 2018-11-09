package com.witbash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.base.ActionListener;
import com.witbash.base.Base2DScreen;
import com.witbash.math.Rect;
import com.witbash.sound.SoundGame;
import com.witbash.sprite.Background;
import com.witbash.sprite.ButtonClose;
import com.witbash.sprite.ButtonPlay;
import com.witbash.sprite.Star;

public class MenuScreen extends Base2DScreen implements ActionListener {

    private static final int STAR_COUNT = 256;

    private Background background;
    private Texture bgTexture;

    private TextureAtlas textureAtlas;
    private Star[] stars;

    private ButtonPlay buttonPlay;
    private ButtonClose buttonClose;

    private Game playScreen;

    private SoundGame soundGame = new SoundGame();

    public MenuScreen(Game playScreen) {
        super();
        this.playScreen = playScreen;
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("menuAtlasNew.pack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }
        buttonPlay = new ButtonPlay(textureAtlas, this);
        buttonClose = new ButtonClose(textureAtlas, this);
        soundGame.musicMenuScreen.setLooping(true);
        soundGame.musicMenuScreen.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        buttonPlay.draw(batch);
        buttonClose.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        buttonPlay.resize(worldBounds);
        buttonClose.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        soundGame.musicMenuScreen.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonClose.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonClose.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonClose) Gdx.app.exit();
        if (src == buttonPlay) playScreen.setScreen(new PlayScreen());
    }
}
