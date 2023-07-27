package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.demo.fallingObjects.Bomb;
import com.demo.fallingObjects.Drop;
import com.demo.fallingObjects.FallingObject;
import com.demo.mechanics.Health;
import com.demo.mechanics.Controls;

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
    private final Texture bombTexture;
    private final Sound explosionSound;
    private final Texture heartTexture;

    private final SpriteBatch batch;
    private final Rectangle vase;
    private final Rectangle fullVase;
    private final Array<FallingObject> objects;
    private long lastDropTime;
    private final OrthographicCamera camera;
    final DemoGame game;
    private int score;
    private int objectNumber;
    private final Health hp;
    Controls controls;

    GameScreen(DemoGame game, OrthographicCamera camera){
        this.game = game;
        this.camera = camera;

        background = new Texture(Gdx.files.internal("backgroundSmall.png"));//dispose check[*]
        vaseTexture = new Texture(Gdx.files.internal("vase.png"));//dispose check[*]
        bombTexture = new Texture(Gdx.files.internal("bomb.png"));//dispose check[*]
        heartTexture = new Texture((Gdx.files.internal("heart64.png")));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));//dispose check[*]
        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterDrop-sound.wav"));//dispose check[*]
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));//dispose check[*]
        dropTexture1 = new Texture(Gdx.files.internal("drops/regularDrop1.png"));//dispose check[*]
        dropTexture2 = new Texture(Gdx.files.internal("drops/regularDrop2.png"));//dispose check[*]
        dropTexture3 = new Texture(Gdx.files.internal("drops/regularDrop3.png"));//dispose check[*]
        dropTexture4 = new Texture(Gdx.files.internal("drops/roundDrop1.png"));//dispose check[*]
        dropTexture5 = new Texture(Gdx.files.internal("drops/roundDrop2.png"));//dispose check[*]
        dropTexture6 = new Texture(Gdx.files.internal("drops/roundDrop3.png"));//dispose check[*]
        dropTextures = new Array<>();
        dropTextures.add(dropTexture1);
        dropTextures.add(dropTexture2);
        dropTextures.add(dropTexture3);
        dropTextures.add(dropTexture4);
        dropTextures.add(dropTexture5);
        dropTextures.add(dropTexture6);

        batch = new SpriteBatch();//dispose check[*]
        vase = new Rectangle();
        fullVase = new Rectangle();
        objects = new Array<>();
        controls = new Controls();
        hp = new Health();

        score = 0;
        rainSoundtrack.setLooping(true);
        setVasePosition();
        spawnObject();
    }

    @Override
    public void render(float delta) {
        camera.update();
        drawScene();
        controls.check(fullVase, vase, camera);
        controls.checkKeys(this, rainSoundtrack);

        // check how much time has passed since last time a drop was spawned
        // -> Current time - time a drop spawned > 1 second then spawn a drop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            spawnObject();
        }

        interactWithFallingObject();
    }
    private void interactWithFallingObject(){
        Iterator<FallingObject> queue = objects.iterator();
        Rectangle hitArea = vase;

        while (queue.hasNext()){
            FallingObject object = queue.next();
            object.fallDown();

            if(hp.isDead()){
                rainSoundtrack.stop();
                game.setScreen(new GameOverScreen(game, batch, camera));
            }
            if(object.isHitTheGround()) {
                queue.remove();
                if (!(object instanceof Bomb)) hp.loose();
            }
            if(object instanceof Bomb)hitArea = fullVase;
            if(object.isCaught(hitArea)){
                queue.remove();
                object.playSound();
                score = object.effectGame(score);
            }
        }
    }

    private void setVasePosition(){
        fullVase.width = 64;
        fullVase.height = 128;
        fullVase.x = (DemoGame.SCREEN_WIDTH - fullVase.width) / 2;
        fullVase.y = 20;

        vase.width = 60;
        vase.height = 10;
        vase.x = fullVase.x;
        vase.y = 120;
    }

    private void spawnObject(){
        objectNumber++;
        objects.add(new Drop(32, 10, 200, dropSound, dropTextures));
        lastDropTime = TimeUtils.nanoTime();

        if(objectNumber % 20 == 0){
            objects.add(new Bomb(32, 32, 250, explosionSound, bombTexture));
        }
    }

    private void drawScene(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);

        for(FallingObject ob: objects) {
            batch.draw(ob.getTexture(), ob.getX(), ob.getY());
        }

        batch.draw(vaseTexture, fullVase.x, fullVase.y);
        game.font.draw(batch, "Score: " + score, 0, DemoGame.SCREEN_HEIGHT);

        for(int i = 1; i <= hp.getAmountOfHealth(); i++){
            batch.draw(heartTexture,DemoGame.SCREEN_WIDTH - (i * heartTexture.getWidth()), DemoGame.SCREEN_HEIGHT - heartTexture.getHeight());
        }
        batch.end();
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
        bombTexture.dispose();
        dropSound.dispose();
        explosionSound.dispose();
        batch.dispose();
        background.dispose();
        rainSoundtrack.dispose();
    }
}
