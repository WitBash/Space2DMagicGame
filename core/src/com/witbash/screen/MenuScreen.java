package com.witbash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.witbash.Space2DMagic;
import com.witbash.base.Base2DScreen;
import com.witbash.math.Rect;
import com.witbash.sprite.Background;
import com.witbash.sprite.ButtonClose;
import com.witbash.sprite.ButtonPlay;
import com.witbash.sprite.Star;

public class MenuScreen extends Base2DScreen {

    private static final int STAR_COUNT = 256;

    private Background background;
    private Texture bgTexture;

    private TextureAtlas textureAtlas;
    private Star[] stars;

    private ButtonPlay buttonPlay;
    private ButtonClose buttonClose;

    private Game playScreen;

    public MenuScreen(Game playScreen) {
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
        buttonPlay = new ButtonPlay(textureAtlas);
        buttonClose = new ButtonClose(textureAtlas);
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
        buttonPlay.setRight(0.23f);
        buttonPlay.setTop(0.38f);
        buttonClose.draw(batch);
        buttonClose.setRight(0.38f);
        buttonClose.setTop(0.5f);
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
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (buttonClose.isMe(touch)) buttonClose.setScale(0.5f);
        if (buttonPlay.isMe(touch)) buttonPlay.setScale(0.5f);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (buttonClose.isMe(touch)) {
            buttonClose.setScale(1f);
            Gdx.app.exit();
        }
        if (buttonPlay.isMe(touch)) {
            buttonPlay.setScale(1f);
            playScreen.setScreen(new PlayScreen());
        }
        return false;
    }
}
