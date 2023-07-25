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
import com.badlogic.gdx.utils.TimeUtils;



public class GameScreen implements Screen{
    private final Texture dropSprite;
    private final Texture bucketSprite;
    private final Texture background;
    private final Sound dropSound;
    private final Music rainSoundtrack;

    private final SpriteBatch batch;
    private final Rectangle bucket;
    private final Vector3 touchPos;
    private final Array<Rectangle> raindrops;
    private long lastDropTime;
    private final OrthographicCamera camera;
    final float MOVEMENT_SPEED = (float) DemoGame.SCREEN_WIDTH / 1.5f;
    final DemoGame game;
    private int score;

    GameScreen(DemoGame game, OrthographicCamera camera){
        this.game = game;
        this.camera = camera;

        dropSprite = new Texture(Gdx.files.internal("drops/regularDrop2.png"));
        bucketSprite = new Texture(Gdx.files.internal("vase.png"));
        background = new Texture(Gdx.files.internal("backgroundSmall.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterDrop-sound.wav"));
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

        batch = new SpriteBatch();
        touchPos = new Vector3();
        bucket = new Rectangle();
        raindrops = new Array<>();

        rainSoundtrack.setLooping(true);
        setVasePosition();
        spawnRaindrop();
    }

    @Override
    public void render(float delta) {
        camera.update();
        drawScene();
        checkInput();

        // check how much time has passed since last time a drop was spawned
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        for(int i = 0; i < raindrops.size; i++){
            raindrops.get(i).y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrops.get(i).y < 0) raindrops.removeIndex(i); //remove the object if hits the ground

            if(raindrops.get(i).overlaps(bucket)) {
                dropSound.play();
                raindrops.removeIndex(i);
                score++;
            }
        }
    }

    private void setVasePosition(){
        bucket.width = 60;
        bucket.height = 10;
        bucket.x =  (DemoGame.SCREEN_WIDTH - bucket.width) / 2;
        bucket.y = 120;
    }

    private void drawScene(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(bucketSprite, bucket.x, bucket.y - 100);

        for(Rectangle raindrop: raindrops) {
            batch.draw(dropSprite, raindrop.x, raindrop.y);
        }

        game.font.draw(batch, "Score: " + score, 0, DemoGame.SCREEN_HEIGHT);
        batch.end();
    }

    private void checkInput(){
        checkKeys();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isTouched() && bucket.x != touchPos.x - bucket.width / 2) {
            touchPos.set(Gdx.input.getX(),0, 0);
            camera.unproject(touchPos);

            if(bucket.x <= touchPos.x - bucket.width / 2) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            if(bucket.x >= touchPos.x - bucket.width / 2) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        }

        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > DemoGame.SCREEN_WIDTH - bucket.width) bucket.x = DemoGame.SCREEN_WIDTH - bucket.width;
    }

    private void checkKeys(){
        if(Gdx.input.isKeyPressed(Input.Keys.O)) rainSoundtrack.stop();
        if(Gdx.input.isKeyPressed(Input.Keys.P)) rainSoundtrack.play();
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
            dispose();
        }
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.width = 32;
        raindrop.height = 10;
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
        background.dispose();
    }
}
