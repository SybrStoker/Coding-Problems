package com.demo.fallingObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.demo.mechanics.Health;

public class Heart extends FallingObject{
    public Heart(int width, int height, float startPoint, float endPoint, int fallingSpeed, Sound sound, Texture texture) {
        super(width, height, startPoint, endPoint, fallingSpeed, sound, texture);
    }

    @Override
    public int effectGame(int score){return score += 0;}

    public void heal(Health hp){ hp.gain();}
}
