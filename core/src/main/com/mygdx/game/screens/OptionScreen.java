package com.mygdx.game.screens;
import com.mygdx.game.PiazzaPanic;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.math.Vector3;


public class OptionScreen implements Screen {

    private final PiazzaPanic game;

    private Stage stage;
    private Skin skin;

    Sprite backButton;
    Sprite keybindsButton;
    SpriteBatch batch;
    ScreenViewport viewport;


    Sprite soundSettingsButton;


    public OptionScreen(final PiazzaPanic game) {
        batch = new SpriteBatch();
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        viewport = new ScreenViewport();

        backButton = new Sprite(new Texture("assets/OptionScreen/BackButton.png")); //todo put actual texture
        //keybindsButton = new Sprite(new Texture("assets/OptionScreen/KeybindButton.png")); //todo put actual texture

        soundSettingsButton = new Sprite(new Texture("assets/OptionScreen/soundSettingsButton.png")); //todo put actual texture

        //CENTERS
        backButton.setCenter(100,0); //todo figure out centering for scaling
        //keybindsButton.setCenter(0,-100); //todo figure out centering for scaling
        soundSettingsButton.setCenter(0, 0); //todo figure out centering for scaling



    }


    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1); // todo choose colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        stage.act(delta);
        stage.draw();

        batch.begin();
        backButton.draw(batch);
        //keybindsButton.draw(batch);

        soundSettingsButton.draw(batch);



        // Check if the left mouse button is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Get the coordinates of the mouse click
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            // show viewport coordinate system
            Vector3 position = viewport.unproject(new Vector3(x, y, 0));

            // Check if mouse pressed
            if (backButton.getBoundingRectangle().contains(position.x, position.y)) {
                game.goToMenu();//todo NOT GOING TO ACTUAL MAIN MENU
            }
            //if (keybindsButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
            //    game.goToMenu();//todo NOT GOING TO ACTUAL MAIN MENU
            //}
            if (soundSettingsButton.getBoundingRectangle().contains(position.x, position.y)) {
                game.SoundSettings1();
            }

        }


        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        saveConfig();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private void saveConfig() {}
}