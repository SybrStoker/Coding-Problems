package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen{

    private Texture dropSprite;
    private Texture bucketSprite;
    private Texture background;
    private Sound dropSound;
    private Music rainSoundtrack;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Vector3 touchPos;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private OrthographicCamera camera;
    final float MOVEMENT_SPEED = (float) DemoGame.SCREEN_WIDTH / 1.5f;
    final DemoGame game;
    private int score;

    GameScreen(DemoGame game, OrthographicCamera camera){
        this.game = game;
        this.camera = camera;

        dropSprite = new Texture(Gdx.files.internal("drop.png"));
        bucketSprite = new Texture(Gdx.files.internal("bucket.png"));
        background = new Texture(Gdx.files.internal("background.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop-sound.wav"));
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

        rainSoundtrack.setLooping(true);

        batch = new SpriteBatch();

        touchPos = new Vector3();

        bucket = new Rectangle();
        bucket.width = 64;
        bucket.height = 64;
        bucket.x =  (DemoGame.SCREEN_WIDTH - bucket.width) / 2;
        bucket.y = 20;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.52f,0.8f,0.92f,0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(bucketSprite, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropSprite, raindrop.x, raindrop.y);
        }
        game.font.draw(batch, "Score: " + score, 0, DemoGame.SCREEN_HEIGHT);
        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isTouched() && bucket.x != touchPos.x - bucket.width / 2) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if(bucket.x <= touchPos.x - bucket.width / 2) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            if(bucket.x >= touchPos.x - bucket.width / 2) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        }

        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > DemoGame.SCREEN_WIDTH - bucket.width) bucket.x = DemoGame.SCREEN_WIDTH - bucket.width;

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        for(int i = 0; i < raindrops.size; i++){
            raindrops.get(i).y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrops.get(i).y + 64 < 0) raindrops.removeIndex(i);

            if(raindrops.get(i).overlaps(bucket)) {
                dropSound.play();
                raindrops.removeIndex(i);
                score++;
            }
        }

        //keys
        if(Gdx.input.isKeyPressed(Input.Keys.O)) rainSoundtrack.stop();
        if(Gdx.input.isKeyPressed(Input.Keys.P)) rainSoundtrack.play();
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
            dispose();
        }
    }


    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.width = 64;
        raindrop.height = 64;
        raindrop.x = MathUtils.random(0, DemoGame.SCREEN_WIDTH - raindrop.width);
        raindrop.y = DemoGame.SCREEN_HEIGHT;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        rainSoundtrack.play();

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
        dropSprite.dispose();
        bucketSprite.dispose();
        dropSound.dispose();
        rainSoundtrack.dispose();
        batch.dispose();
        game.font.dispose();
    }
}
