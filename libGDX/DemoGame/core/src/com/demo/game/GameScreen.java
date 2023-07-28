package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import com.demo.fallingObjects.Bomb;
import com.demo.fallingObjects.Drop;
import com.demo.fallingObjects.FallingObject;
import com.demo.fallingObjects.Heart;
import com.demo.mechanics.Health;
import com.demo.mechanics.Controls;
import com.demo.mechanics.Spawner;

import java.util.Iterator;


public class GameScreen implements Screen{
    //my classes
    private final DemoGame game;
    private final Controls controls;
    private final Health hp;
    private final Spawner spawner;

    //textures
    private final Texture background;
    private final Texture vaseTexture;
    private final Texture heartTexture;

    //soundtracks
    private final Music rainSoundtrack;
    private final Music mainSoundtrack;

    //rendering
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    //rectangles
    private final Rectangle vase;
    private final Rectangle fullVase;

    //data
    private int score;

    GameScreen(DemoGame game, OrthographicCamera camera){
        //my classes
        this.game = game;
        controls = new Controls();
        hp = new Health();
        spawner = new Spawner();


        //textures
        background = new Texture(Gdx.files.internal("backgroundSmall.png"));//dispose check[*]
        vaseTexture = new Texture(Gdx.files.internal("vase.png"));//dispose check[*]
        heartTexture = new Texture((Gdx.files.internal("heart64.png")));//dispose check[*]

        //soundtracks
        rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));//dispose check[*]
        mainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("mainTheme.wav"));//dispose check[*]

        //rendering
        batch = new SpriteBatch();//dispose check[*]
        this.camera = camera;

        //rectangles
        vase = new Rectangle();
        fullVase = new Rectangle();

        //data etc.
        score = 0;
        setRectanglesPositions();
        rainSoundtrack.setLooping(true);
        mainSoundtrack.setLooping(true);
        spawner.spawnObject();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        drawScene();
        controls.check(fullVase, vase, camera);
        controls.checkKeys(this, rainSoundtrack);

        // check how much time has passed since last time a drop was spawned
        // -> Current time - time a drop spawned > 1 second then spawn a drop
        if(TimeUtils.nanoTime() - spawner.getLastTimeDropSpawned() > 1000000000){
            spawner.spawnObject();
        }

        interactWithFallingObject();
    }
    private void interactWithFallingObject(){
        Iterator<FallingObject> queue = spawner.getObjects().iterator();
        Rectangle hitArea = vase;

        while (queue.hasNext()){
            FallingObject object = queue.next();
            object.fallDown();

            if(hp.isDead()){
                game.setScreen(new GameOverScreen(game, camera));
            }
            if(object.isHitTheGround()) {
                queue.remove();
                if (object instanceof Drop) hp.loose();
            }

            if(object instanceof Bomb) hitArea = fullVase;
            if(object.isCaught(hitArea)){
                if(object instanceof Bomb) ((Bomb) object).damage(hp);
                if(object instanceof Heart) ((Heart) object).heal(hp);

                queue.remove();
                object.playSound();
                score = object.effectGame(score);
            }
        }
    }

    private void setRectanglesPositions(){
        fullVase.width = 64;
        fullVase.height = 128;
        fullVase.x = (DemoGame.SCREEN_WIDTH - fullVase.width) / 2;
        fullVase.y = 20;

        vase.width = 60;
        vase.height = 10;
        vase.x = fullVase.x;
        vase.y = 120;
    }

    private void drawScene(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);

        for(FallingObject ob: spawner.getObjects()) {
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
        rainSoundtrack.setVolume(0.25f);

        rainSoundtrack.play();
        mainSoundtrack.play();
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
        rainSoundtrack.stop();
        mainSoundtrack.stop();
    }

    @Override
    public void dispose() {
        //textures
        background.dispose();
        vaseTexture.dispose();
        heartTexture.dispose();

        //soundtracks
        rainSoundtrack.dispose();
        mainSoundtrack.dispose();

        batch.dispose();

        spawner.dispose();
    }
}
