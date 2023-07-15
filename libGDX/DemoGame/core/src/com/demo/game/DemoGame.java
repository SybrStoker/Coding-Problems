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
	private int screenWidth = 800;
	private int screenheigh = 480;
	private Vector3 touchPos;
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	@Override
	public void create (){
		dropSprite = new Texture(Gdx.files.internal("drop.png"));
		bucketSprite = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop-sound.wav"));
		rainSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("rain-soundtrack.mp3"));

		rainSoundtrack.setLooping(true);
		rainSoundtrack.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenheigh);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		bucket = new Rectangle();
		bucket.width = 64;
		bucket.height = 64;
		bucket.x =  (screenWidth - bucket.width) / 2;
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

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();//what is deltaTime thing?
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		//add the speed for this movement method(fix teleportation)
		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - bucket.width / 2;
		}

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > screenWidth - bucket.width) bucket.x = screenWidth - bucket.width;

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


	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.width = 64;
		raindrop.height = 64;
		raindrop.x = MathUtils.random(0, screenWidth-raindrop.width);
		raindrop.y = screenheigh;
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
