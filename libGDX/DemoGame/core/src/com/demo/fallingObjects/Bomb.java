package com.demo.fallingObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Bomb extends FallingObject{
    public Bomb(int width, int height, int fallingSpeed, Sound sound, Texture texture) {
        super(width, height, fallingSpeed, sound, texture);
    }

    @Override
    public int effectGame(int score) {return score -= 5;}
}
