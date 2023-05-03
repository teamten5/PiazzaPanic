package com.mygdx.game.screens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PiazzaPanic;

import static javax.swing.UIManager.getColor;

public class PauseScreen implements Screen {
// Resume
    // Menu
    Sprite SaveSlot1Button;
    Sprite SaveSlot2Button;
    Sprite SaveSlot3Button;
    Sprite ResumeButton;

    Sprite BackToMenuButton;

    private Stage stage;

    private Skin skin;
    SpriteBatch batch;

    ScreenViewport viewport;

    private PiazzaPanic piazzaPanic;



    public PauseScreen(PiazzaPanic game) {
        super();
        this.piazzaPanic = game;
        batch = new SpriteBatch();
        stage = new Stage();

        //Buttons
        SaveSlot1Button = new Sprite(new Texture("PauseScreen/SaveSlot1Button.png")); //todo put actual texture
        SaveSlot2Button = new Sprite(new Texture("PauseScreen/SaveSlot2Button.png")); //todo put actual texture
        SaveSlot3Button = new Sprite(new Texture("PauseScreen/SaveSlot3Button.png")); //todo put actual texture
        ResumeButton = new Sprite(new Texture("PauseScreen/ResumeButton.png")); //todo put actual texture
        BackToMenuButton = new Sprite(new Texture("PauseScreen/BacktoMenuButton.png")); //todo put actual texture


        //Centers

        SaveSlot1Button.setCenter(-300, 0);
        SaveSlot2Button.setCenter(0, 0);
        SaveSlot3Button.setCenter(300,0);
        ResumeButton.setCenter(-250,-100);
        BackToMenuButton.setCenter(250,-100);

        viewport = new ScreenViewport();


    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // todo choose colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.begin();
        SaveSlot1Button.draw(batch);
        SaveSlot2Button.draw(batch);
        SaveSlot3Button.draw(batch);
        ResumeButton.draw(batch);
        BackToMenuButton.draw(batch);


        // Check if the left mouse button is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Get the coordinates of the mouse click
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            // show viewport coordinate system
            Vector3 position = viewport.unproject(new Vector3(x, y, 0));


            if (SaveSlot1Button.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                piazzaPanic.saveGame(1);
                SaveSlot1Button.setColor(1, 0, 0, 1);

            }
            // Check if mouse pressed
            if (SaveSlot2Button.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                piazzaPanic.saveGame(2);
                SaveSlot2Button.setColor(1, 0, 0, 1);

            }
            if (SaveSlot3Button.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                piazzaPanic.saveGame(3);
                SaveSlot3Button.setColor(1, 0, 0, 1);
            }
            if (ResumeButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                piazzaPanic.gameScreen.togglePause();
            }
            if (BackToMenuButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed

            }


        }

        batch.end();
    }
//


    @Override
    public void resize ( int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void pause () {}

    @Override
    public void resume () {}

    @Override
    public void hide () {}

    @Override
    public void dispose () {}
}



