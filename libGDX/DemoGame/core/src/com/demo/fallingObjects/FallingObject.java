package com.demo.fallingObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.demo.game.DemoGame;

public class FallingObject {
    private final int fallingSpeed;

    private final Rectangle fallingObject;
    private final Sound sound;
    private final Texture texture;

    public FallingObject(int width, int height, int fallingSpeed, Sound sound, Texture texture){
        this.fallingSpeed = fallingSpeed;
        this.sound = sound;
        this.texture = texture;

        fallingObject = new Rectangle();
        fallingObject.width = width;
        fallingObject.height = height;
        fallingObject.x = MathUtils.random(0, DemoGame.SCREEN_WIDTH - fallingObject.width);
        fallingObject.y = DemoGame.SCREEN_HEIGHT;
    }

    public void fallDown(){
        fallingObject.y -= fallingSpeed * Gdx.graphics.getDeltaTime();
    }

    public boolean isHitTheGround(){
        return fallingObject.y < 0;
    }

    public boolean isCaught(Rectangle vase){return fallingObject.overlaps(vase);}

    public void playSound(){
        sound.play();
    }

    public int effectGame(int score){
        return score += 1;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX(){
        return fallingObject.x;
    }

    public float getY(){
        return fallingObject.y;
    }
}
