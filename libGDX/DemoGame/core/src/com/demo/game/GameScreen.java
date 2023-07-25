package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.demo.fallingObjects.Drop;
import com.demo.fallingObjects.FallingObject;

import java.util.Iterator;


public class GameScreen implements Screen{
    private final Texture bucketSprite;
    private final Texture background;
    private final Music rainSoundtrack;
    private final Texture dropTexture;
    private final Sound dropSound;

    private final SpriteBatch batch;
    private final Rectangle bucket;
    private final Vector3 touchPos;
    private final Array<FallingObject> objects;
    private long lastDropTime;
    private final OrthographicCamera camera;
    final float MOVEMENT_SPEED = (float) DemoGame.SCREEN_WIDTH / 1.5f;
    final DemoGame game;
    private int score;

    GameScreen(DemoGame game, OrthographicCamera camera){
        this.game = game;
        this.camera = camera;

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterDrop-sound.wav"));
        dropTexture = new Texture(Gdx.files.internal("drops/regularDrop2.png"));
        bucketSprite = new Texture(Gdx.files.internal("vase.png"));
        background = new Texture(Gdx.files.internal("backgroundSmall.png"));
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

        batch = new SpriteBatch();
        touchPos = new Vector3();
        bucket = new Rectangle();
        objects = new Array<>();

        score = 0;
        rainSoundtrack.setLooping(true);
        setVasePosition();
        objects.add(new Drop(32, 10, 200, dropSound, dropTexture));
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        camera.update();
        drawScene();
        checkInput();

        // check how much time has passed since last time a drop was spawned
        // -> Current time - time a drop spawned > 1 second then spawn a drop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            objects.add(new Drop(32, 10, 200, dropSound, dropTexture));
            lastDropTime = TimeUtils.nanoTime();
        }

        Iterator<FallingObject> queue = objects.iterator();
        while (queue.hasNext()){
            FallingObject object = queue.next();
            object.fallDown();

            if(object.isHitTheGround()) queue.remove();
            if(object.isCaught(bucket)){
                queue.remove();
                object.playSound();
                score = object.effectGame(score);
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

        for(FallingObject ob: objects) {
            batch.draw(ob.getTexture(), ob.getX(), ob.getY());
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
        bucketSprite.dispose();
        rainSoundtrack.dispose();
        batch.dispose();
        game.font.dispose();
        background.dispose();
    }
}
