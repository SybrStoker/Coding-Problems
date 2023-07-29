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

    public FallingObject(int width, int height, float startPoint, float endPoint, int fallingSpeed, Sound sound, Texture texture){
        this.fallingSpeed = fallingSpeed;
        this.sound = sound;
        this.texture = texture;

        fallingObject = new Rectangle();
        fallingObject.width = width;
        fallingObject.height = height;

        if(endPoint == -1.0f){
            endPoint = generateEndPoint(startPoint);
        }

        setPosition(startPoint, endPoint);
    }

    private void setPosition(float startPoint, float endPoint){
        fallingObject.setPosition(MathUtils.random(startPoint, endPoint), DemoGame.SCREEN_HEIGHT);
    }
    protected float generateEndPoint(float vaseXCoordinate){
        float endPosition;
        float side;
        float rightSide = vaseXCoordinate + (DemoGame.SCREEN_WIDTH - fallingObject.width) / 4;
        float leftSide = vaseXCoordinate - (DemoGame.SCREEN_WIDTH - fallingObject.width) / 4;

        if(rightSide > DemoGame.SCREEN_WIDTH)rightSide = DemoGame.SCREEN_WIDTH;
        if(leftSide < 0) leftSide = 0;

        //choose side
        side = MathUtils.random(0, 1);

        if(side == 0){
            endPosition = leftSide;
        } else{
            endPosition = rightSide;
        }

        return endPosition;
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
