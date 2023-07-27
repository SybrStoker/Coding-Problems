package com.demo.mechanics;

public class Health {
    private int hp;

    public Health(){
        this.hp = 3;
    }

    public void gain(){
        if(hp <= 5){
            hp++;
        }
    }

    public void loose(){
        hp--;
    }

    public int getAmountOfHealth(){
        return hp;
    }

    public boolean isDead(){
        return hp <= 0;
    }
}
