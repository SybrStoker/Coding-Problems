package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final DemoGame dg;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final String gameOverMessage;
    private final Music gameOverSoundtrack;

    GameOverScreen(DemoGame dm, OrthographicCamera camera){
        this.dg = dm;
        this.camera = camera;
        batch = new SpriteBatch();
        gameOverMessage = "HA, HA! Looser!";

        gameOverSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("gameOverSoundtrack.wav"));//dispose check[*]
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
    public void show() {
        gameOverSoundtrack.setLooping(true);
        gameOverSoundtrack.play();
        gameOverSoundtrack.setVolume(0.75f);
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
        gameOverSoundtrack.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOverSoundtrack.dispose();
    }
}
