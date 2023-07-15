package com.demo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import java.util.Iterator;

public class DemoGame extends ApplicationAdapter {

	private Texture dropSprite;
	private Texture bucketSprite;
	private Sound dropSound;
	private Music rainSoundtrack;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private final int SCREEN_WIDTH = 1280;
	private final int SCREEN_HEIGHT = 720;
	private Vector3 touchPos;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	final float MOVEMENT_SPEED = (float) SCREEN_WIDTH / 1.5f;

	@Override
	public void create (){
		dropSprite = new Texture(Gdx.files.internal("drop.png"));
		bucketSprite = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop-sound.wav"));
		rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

		rainSoundtrack.setLooping(true);
		rainSoundtrack.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		bucket = new Rectangle();
		bucket.width = 64;
		bucket.height = 64;
		bucket.x =  (SCREEN_WIDTH - bucket.width) / 2;
		bucket.y = 20;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();

	}

	@Override
	public void render(){
		ScreenUtils.clear(0.52f,0.8f,0.92f,0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketSprite, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropSprite, raindrop.x, raindrop.y);
		}
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();//what is deltaTime thing?
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

		//add the speed for this movement method(fix teleportation)
		if(Gdx.input.isTouched() && bucket.x != touchPos.x - bucket.width / 2) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if(bucket.x <= touchPos.x - bucket.width / 2) bucket.x += MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			if(bucket.x >= touchPos.x - bucket.width / 2) bucket.x -= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

		}

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > SCREEN_WIDTH - bucket.width) bucket.x = SCREEN_WIDTH - bucket.width;

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();// i don't understand how this line works

		//learn more about itearators(I don't really know how it works)
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();

			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) Gdx.app.exit();

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.width = 64;
		raindrop.height = 64;
		raindrop.x = MathUtils.random(0, SCREEN_WIDTH - raindrop.width);
		raindrop.y = SCREEN_HEIGHT;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void dispose() {
		dropSprite.dispose();
		bucketSprite.dispose();
		dropSound.dispose();
		rainSoundtrack.dispose();
		batch.dispose();
	}


}
