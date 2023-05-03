package com.mygdx.game.player;

public interface Controller {

    public InputData handleInput(float delta);
}class InputData{

    final float x;
    final float y;
    final boolean doInteract;

    public InputData(float x, float y, boolean interactNow) {
        this.x = x;
        this.y = y;
        this.doInteract = interactNow;
    }



}
