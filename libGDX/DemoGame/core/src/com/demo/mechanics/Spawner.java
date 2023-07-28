package com.demo.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.demo.fallingObjects.Bomb;
import com.demo.fallingObjects.Drop;
import com.demo.fallingObjects.FallingObject;
import com.demo.fallingObjects.Heart;

public class Spawner {
    //textures
    private final Texture bombTexture;
    private final Texture heartObjectTexture;


    //sounds
    private final Sound dropSound;
    private final Sound explosionSound;
    private final Sound heartSound;

    //drops variations
    private final Texture dropTexture1;
    private final Texture dropTexture2;
    private final Texture dropTexture3;
    private final Texture dropTexture4;
    private final Texture dropTexture5;
    private final Texture dropTexture6;
    private final Array<Texture> dropTextures;

    //data
    private final Array<FallingObject> objects;
    private long lastTimeDropSpawned;
    private int objectNumber;

    public Spawner() {
        //textures
        bombTexture = new Texture(Gdx.files.internal("bomb.png"));//dispose check[*]
        heartObjectTexture = new Texture((Gdx.files.internal("heart32.png")));//dispose check[*]

        //sounds
        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterDrop-sound.wav"));//dispose check[*]
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));//dispose check[*]
        heartSound = Gdx.audio.newSound(Gdx.files.internal("heart.wav"));//dispose check[*]

        //drops variations
        dropTexture1 = new Texture(Gdx.files.internal("drops/regularDrop1.png"));//dispose check[*]
        dropTexture2 = new Texture(Gdx.files.internal("drops/regularDrop2.png"));//dispose check[*]
        dropTexture3 = new Texture(Gdx.files.internal("drops/regularDrop3.png"));//dispose check[*]
        dropTexture4 = new Texture(Gdx.files.internal("drops/roundDrop1.png"));//dispose check[*]
        dropTexture5 = new Texture(Gdx.files.internal("drops/roundDrop2.png"));//dispose check[*]
        dropTexture6 = new Texture(Gdx.files.internal("drops/roundDrop3.png"));//dispose check[*]
        dropTextures = new Array<>();
        dropTextures.add(dropTexture1);
        dropTextures.add(dropTexture2);
        dropTextures.add(dropTexture3);
        dropTextures.add(dropTexture4);
        dropTextures.add(dropTexture5);
        dropTextures.add(dropTexture6);

        //data
        objects = new Array<>();
    }
    
    public Array<FallingObject> getObjects(){ return objects;}
    
    public void spawnObject(){
        objectNumber++;
        lastTimeDropSpawned = TimeUtils.nanoTime();

        switch (objectNumber){
            case 10:
                objects.add(new Bomb(32, 32, 250, explosionSound, bombTexture));
                break;

            case 20:
                objects.add(new Heart(32, 10, 400, heartSound, heartObjectTexture));
                break;

            default: objects.add(new Drop(32, 10, 200, dropSound, dropTextures));
        }
    }
    
    public  long getLastTimeDropSpawned(){ return lastTimeDropSpawned;}

    public void dispose(){
        //textures
        bombTexture.dispose();
        heartObjectTexture.dispose();

        //sounds
        dropSound.dispose();
        explosionSound.dispose();
        heartSound.dispose();

        //drop variations
        dropTexture1.dispose();
        dropTexture2.dispose();
        dropTexture3.dispose();
        dropTexture4.dispose();
        dropTexture5.dispose();
        dropTexture6.dispose();
    }
}
