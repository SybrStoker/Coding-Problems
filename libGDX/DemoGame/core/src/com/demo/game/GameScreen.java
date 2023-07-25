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
    private final Texture vaseTexture;
    private final Texture background;
    private final Music rainSoundtrack;

    private final Texture dropTexture1;
    private final Texture dropTexture2;
    private final Texture dropTexture3;
    private final Texture dropTexture4;
    private final Texture dropTexture5;
    private final Texture dropTexture6;
    private final Array<Texture> dropTextures;
    private final Sound dropSound;

    private final SpriteBatch batch;
    private final Rectangle vase;
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
        dropTexture1 = new Texture(Gdx.files.internal("drops/regularDrop1.png"));
        dropTexture2 = new Texture(Gdx.files.internal("drops/regularDrop2.png"));
        dropTexture3 = new Texture(Gdx.files.internal("drops/regularDrop3.png"));
        dropTexture4 = new Texture(Gdx.files.internal("drops/roundDrop1.png"));
        dropTexture5 = new Texture(Gdx.files.internal("drops/roundDrop2.png"));
        dropTexture6 = new Texture(Gdx.files.internal("drops/roundDrop3.png"));
        dropTextures = new Array<>();
        dropTextures.add(dropTexture1);
        dropTextures.add(dropTexture2);
        dropTextures.add(dropTexture3);
        dropTextures.add(dropTexture4);
        dropTextures.add(dropTexture5);
        dropTextures.add(dropTexture6);

        vaseTexture = new Texture(Gdx.files.internal("vase.png"));
        background = new Texture(Gdx.files.internal("backgroundSmall.png"));
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

        batch = new SpriteBatch();
        touchPos = new Vector3();
        vase = new Rectangle();
        objects = new Array<>();

        score = 0;
        rainSoundtrack.setLooping(true);
        setVasePosition();
        spawnObject();
    }

    @Override
    public void render(float delta) {
        camera.update();
        drawScene();
        checkInput();

        // check how much time has passed since last time a drop was spawned
        // -> Current time - time a drop spawned > 1 second then spawn a drop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            spawnObject();
        }

        Iterator<FallingObject> queue = objects.iterator();
        while (queue.hasNext()){
            FallingObject object = queue.next();
            object.fallDown();

            if(object.isHitTheGround()) queue.remove();
            if(object.isCaught(vase)){
                queue.remove();
                object.playSound();
                score = object.effectGame(score);
            }
        }
    }

    private void setVasePosition(){
        vase.width = 60;
        vase.height = 10;
        vase.x =  (DemoGame.SCREEN_WIDTH - vase.width) / 2;
        vase.y = 120;
    }

    private void spawnObject(){
        objects.add(new Drop(32, 10, 200, dropSound, dropTextures));
        lastDropTime = TimeUtils.nanoTime();
    }

    private void drawScene(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(vaseTexture, vase.x, vase.y - 100);

        for(FallingObject ob: objects) {
            batch.draw(ob.getTexture(), ob.getX(), ob.getY());
        }

        game.font.draw(batch, "Score: " + score, 0, DemoGame.SCREEN_HEIGHT);
        batch.end();
    }

    private void checkInput(){
        checkKeys();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) vase.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) vase.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isTouched() && vase.x != touchPos.x - vase.width / 2) {
            touchPos.set(Gdx.input.getX(),0, 0);
            camera.unproject(touchPos);

            if(vase.x <= touchPos.x - vase.width / 2) vase.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            if(vase.x >= touchPos.x - vase.width / 2) vase.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        }

        if(vase.x < 0) vase.x = 0;
        if(vase.x > DemoGame.SCREEN_WIDTH - vase.width) vase.x = DemoGame.SCREEN_WIDTH - vase.width;
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
        dropTexture1.dispose();
        dropTexture2.dispose();
        dropTexture3.dispose();
        dropTexture4.dispose();
        dropTexture5.dispose();
        dropTexture6.dispose();
        vaseTexture.dispose();
        rainSoundtrack.dispose();
        batch.dispose();
        game.font.dispose();
        background.dispose();
    }
}
