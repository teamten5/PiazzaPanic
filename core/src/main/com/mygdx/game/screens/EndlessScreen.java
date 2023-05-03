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

public class EndlessScreen implements Screen {

    int difficulty = 1;
    //BUTTONS
    Sprite easyButton;
    Color easyButtonDefault;
    Sprite mediumButton;

    Color mediumButtonDefault;
    Sprite hardButton;
    Color hardButtonDefault;
    Sprite playButton;
    Sprite backButton;



    private Stage stage;

    private Skin skin;
    SpriteBatch batch;

    ScreenViewport viewport;

    private PiazzaPanic piazzaPanic;



    public EndlessScreen(PiazzaPanic piazzaPanic) {
        this.piazzaPanic = piazzaPanic;
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        //Buttons

        easyButton = new Sprite(new Texture("assets/EndlessScreen/EasyButton.png")); //todo put actual texture
        mediumButton = new Sprite(new Texture("assets/EndlessScreen/MediumButton.png")); //todo put actual texture
        hardButton = new Sprite(new Texture("assets/EndlessScreen/HardButton.png")); //todo put actual texture
        playButton = new Sprite(new Texture("assets/EndlessScreen/StartButton.png")); //todo put actual texture
        backButton = new Sprite(new Texture("assets/EndlessScreen/BackButton.png")); //todo put actual texture

        //DEFAULT COLOURS
        easyButtonDefault = new Sprite(new Texture("assets/EndlessScreen/EasyButton.png")).getColor();getColor(easyButtonDefault);
        mediumButtonDefault = new Sprite(new Texture("assets/EndlessScreen/MediumButton.png")).getColor();getColor(mediumButtonDefault);
        hardButtonDefault = new Sprite(new Texture("assets/EndlessScreen/HardButton.png")).getColor();getColor(hardButtonDefault);

        //Centers

        easyButton.setCenter(-300, 0);
        mediumButton.setCenter(0, 0);
        hardButton.setCenter(300, 0);
        playButton.setCenter(0,-500);
        backButton.setCenter(0,-600);


        viewport = new ScreenViewport();

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




        batch.begin();

        easyButton.draw(batch);
        mediumButton.draw(batch);
        hardButton.draw(batch);
        playButton.draw(batch);
        backButton.draw(batch);


        // Check if the left mouse button is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Get the coordinates of the mouse click
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            // show viewport coordinate system
            Vector3 position = viewport.unproject(new Vector3(x, y, 0));

            // Check if mouse pressed
            if (easyButton.getBoundingRectangle().contains(position.x, position.y)) {

                //LEVEL FOR START
                difficulty = 0;

                //BUTTON COLOURS
                easyButton.setColor(1, 0, 0, 1);
                mediumButton.setColor(mediumButtonDefault.r, mediumButtonDefault.g, mediumButtonDefault.b, mediumButtonDefault.a);
                hardButton.setColor(hardButtonDefault.r, hardButtonDefault.g, hardButtonDefault.b, hardButtonDefault.a);
            }

            if (mediumButton.getBoundingRectangle().contains(position.x, position.y)) {

                //LEVEL FOR START
                difficulty = 1;

                //BUTTON COLOURS
                mediumButton.setColor(1, 0, 0, 1);
                easyButton.setColor(easyButtonDefault.r, easyButtonDefault.g, easyButtonDefault.b, easyButtonDefault.a);
                hardButton.setColor(hardButtonDefault.r, hardButtonDefault.g, hardButtonDefault.b, hardButtonDefault.a);

            }

            if (hardButton.getBoundingRectangle().contains(position.x, position.y)) {

                //LEVEL FOR START
                difficulty = 2;

                //BUTTON COLOURS
                hardButton.setColor(1, 0, 0, 1);
                easyButton.setColor(easyButtonDefault.r, easyButtonDefault.g, easyButtonDefault.b, easyButtonDefault.a);
                mediumButton.setColor(mediumButtonDefault.r, mediumButtonDefault.g, mediumButtonDefault.b, mediumButtonDefault.a);

            }

            if (playButton.getBoundingRectangle().contains(position.x, position.y)) {

            piazzaPanic.startGame("arcade-salad", difficulty);// TODO REPLACE WHEN ENDLESS AVAILABLE,

            }

            if (backButton.getBoundingRectangle().contains(position.x, position.y)) {

                piazzaPanic.goToMenu();
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