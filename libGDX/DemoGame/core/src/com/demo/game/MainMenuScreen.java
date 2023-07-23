package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final DemoGame game;
    private OrthographicCamera camera;
    private String welcomeMessage = "Welcome to this really bad game -_- ";

    MainMenuScreen(DemoGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DemoGame.SCREEN_WIDTH, DemoGame.SCREEN_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.52f,0.8f,0.92f,0);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, welcomeMessage, DemoGame.SCREEN_WIDTH_MIDDLE - welcomeMessage.length(), DemoGame.SCREEN_HEIGHT_MIDDLE);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game, camera));
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
