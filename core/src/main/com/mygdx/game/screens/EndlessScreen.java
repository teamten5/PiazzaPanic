package com.mygdx.game.screens;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PiazzaPanic;

import static java.lang.System.exit;

public class EndlessScreen implements Screen {

    int level = 1;
    //BUTTONS
    Sprite easyButton;
    Sprite mediumButton;
    Sprite hardButton;
    Sprite startButton;
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

        easyButton = new Sprite(new Texture("assets/EndlessScreen/EasyButton.png"));
        mediumButton = new Sprite(new Texture("assets/EndlessScreen/MediumButton.png"));
        hardButton = new Sprite(new Texture("assets/EndlessScreen/HardButton.png"));
        startButton = new Sprite(new Texture("assets/EndlessScreen/StartButton.png"));
        backButton = new Sprite(new Texture("assets/EndlessScreen/BackButton.png"));
        //Centers

        easyButton.setCenter(-300, 0);
        mediumButton.setCenter(0, 0);
        hardButton.setCenter(300, 0);
        startButton.setCenter(0,-500);
        backButton.setCenter(0,-600);


        viewport = new ScreenViewport();

    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);



        batch.begin();
        easyButton.draw(batch);
        mediumButton.draw(batch);
        hardButton.draw(batch);
        startButton.draw(batch);
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
                // Code to be executed when the button is pressed
                System.out.println("The easy button was pressed");

                level = 1;

                // Switch to Scenario Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                easyButton.setColor(1, 0, 0, 1);
            }

            if (mediumButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The medium button was pressed");

                // Switch to endless Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            }

            if (hardButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The hard button was pressed");

                // Switch to endless Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            }

            if (startButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The start button was pressed");

                // Switch to endless Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            }

            if (backButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The start button was pressed");

                // Switch to endless Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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



