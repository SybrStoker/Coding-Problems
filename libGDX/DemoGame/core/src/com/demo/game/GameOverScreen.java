package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    DemoGame dg;
    SpriteBatch batch;
    OrthographicCamera camera;
    String gameOverMessage;

    GameOverScreen(DemoGame dm, SpriteBatch batch, OrthographicCamera camera){
        this.dg = dm;
        this.batch = batch;
        this.camera = camera;
        gameOverMessage = "HA, HA! Looser!";
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.52f,0.8f,0.92f,0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        dg.font.draw(batch, gameOverMessage, DemoGame.SCREEN_WIDTH_MIDDLE - gameOverMessage.length(), DemoGame.SCREEN_HEIGHT_MIDDLE);
        batch.end();

        if (Gdx.input.isTouched()) {
            dg.setScreen(new GameScreen(dg, camera));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
