package com.mygdx.game.screens;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PiazzaPanic;

import static java.lang.System.exit;

public class GameOverScreen implements Screen {


    Sprite YouWon;
    Sprite YouLost;
    Sprite GameOver;
    Sprite BackToMenu;


    private Stage stage;

    private Skin skin;
    SpriteBatch batch;

    ScreenViewport viewport;

    private PiazzaPanic piazzaPanic;
    public GameOverScreen (PiazzaPanic piazzaPanic){
        this.piazzaPanic = piazzaPanic;
        YouWon = new Sprite(new Texture("GameOverScreen/YouWon.png")); //todo put actual texture
        YouLost = new Sprite(new Texture("GameOverScreen/YouLost.png")); //todo put actual texture
        GameOver = new Sprite(new Texture("GameOverScreen/GameOver.png")); //todo put actual texture





    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
