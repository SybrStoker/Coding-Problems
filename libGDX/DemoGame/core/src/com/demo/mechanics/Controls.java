package com.demo.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.demo.game.DemoGame;
import com.demo.game.GameScreen;

public class Controls {
    private final Vector3 touchPos;
    public Controls(){
        this.touchPos = new Vector3();
    }
    public void check(Rectangle fullVase, Rectangle vase, OrthographicCamera camera){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            fullVase.x -= DemoGame.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            vase.x = fullVase.x;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            fullVase.x += DemoGame.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
            vase.x = fullVase.x;
        }

        if(Gdx.input.isTouched() && fullVase.x != touchPos.x - fullVase.width / 2) {
            touchPos.set(Gdx.input.getX(),0, 0);
            camera.unproject(touchPos);

            if(fullVase.x <= touchPos.x - fullVase.width / 2){
                fullVase.x += DemoGame.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
                vase.x = fullVase.x;
            }

            if(fullVase.x >= touchPos.x - fullVase.width / 2){
                fullVase.x -= DemoGame.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
                vase.x = fullVase.x;
            }
        }

        //don't let the vase to-get off the screen
        if(fullVase.x < 0) fullVase.x = 0;
        if(fullVase.x > DemoGame.SCREEN_WIDTH - fullVase.width) fullVase.x = DemoGame.SCREEN_WIDTH - fullVase.width;
    }

    public void checkKeys(GameScreen gm, Music rainSoundtrack){
        if(Gdx.input.isKeyPressed(Input.Keys.O)) rainSoundtrack.stop();
        if(Gdx.input.isKeyPressed(Input.Keys.P)) rainSoundtrack.play();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) gm.pause();
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
            gm.dispose();
        }
    }

}
