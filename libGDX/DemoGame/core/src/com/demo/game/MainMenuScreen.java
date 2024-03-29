package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final DemoGame dg;
    private final OrthographicCamera camera;
    private final String welcomeMessage;

    MainMenuScreen(DemoGame game){
        this.dg = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DemoGame.SCREEN_WIDTH, DemoGame.SCREEN_HEIGHT);
        welcomeMessage = "Click";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.52f,0.8f,0.92f,0);
        camera.update();

        dg.batch.setProjectionMatrix(camera.combined);
        dg.batch.begin();
        dg.font.draw(dg.batch, welcomeMessage, DemoGame.SCREEN_WIDTH_MIDDLE - welcomeMessage.length(), DemoGame.SCREEN_HEIGHT_MIDDLE);
        dg.batch.end();

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
    }
}
