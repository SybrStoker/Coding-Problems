package com.demo.fallingObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Drop extends FallingObject{


    public Drop(int width, int height, int fallingSpeed, Sound sound, Texture texture) {
        super(width, height, fallingSpeed, sound, texture);

    }
}
