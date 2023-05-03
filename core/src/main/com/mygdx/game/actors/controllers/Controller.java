package com.mygdx.game.actors.controllers;

import com.badlogic.gdx.utils.JsonValue;

public abstract class Controller {

    public float x = 0;
    public float y = 0;

    public float facingX = 0;
    public float facingY = 0;
    public boolean doAction = false;
    public boolean doCombination = false;

    public boolean swapPlayers = false;

    abstract public void update(float delta);

    abstract public JsonValue saveGame();

    public static Controller loadGame(JsonValue controllerSaveData) {
        return switch (controllerSaveData.getString("type")) {
            case "null" -> new NullController(
                  controllerSaveData.getFloat("x"),
                  controllerSaveData.getFloat("y"),
                  controllerSaveData.getFloat("facing-x"),
                  controllerSaveData.getFloat("facing-y"),
                  controllerSaveData.getBoolean("doAction")
            );
            case "user" -> new UserController();
            default -> null;
        };
    }
}