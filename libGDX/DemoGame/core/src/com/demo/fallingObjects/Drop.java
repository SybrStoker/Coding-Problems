package com.demo.fallingObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Drop extends FallingObject{


    public Drop(int width, int height, float startPoint, float endPoint, int fallingSpeed, Sound sound, Array<Texture> textures) {
        super(width, height, startPoint, endPoint, fallingSpeed, sound, textures.get(MathUtils.random(0, textures.size - 1)));

    }
}
