package com.demo.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DemoGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_WIDTH_MIDDLE = SCREEN_WIDTH / 2;
	public static final int SCREEN_HEIGHT = 720;
	public static final int SCREEN_HEIGHT_MIDDLE = SCREEN_HEIGHT / 2;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose(){
		batch.dispose();
		font.dispose();
	}
}
