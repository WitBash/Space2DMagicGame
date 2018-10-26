package com.witbash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceMagic extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("space.jpg");
		region = new TextureRegion(img,50,35,100,200);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.125f, 0.7f, 0.8f, 0.6f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(0.9f,0.25f,0.25f,0.8f);
		batch.draw(img, 150, 100,500,278);
		batch.setColor(0.9f,0.5f,0.5f,0.5f);
		batch.draw(region, 0, 0,50,100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
