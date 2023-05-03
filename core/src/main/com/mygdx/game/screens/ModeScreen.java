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

public class ModeScreen implements Screen {

    Sprite endlessButton;
    Sprite scenarioButton;

    private Stage stage;

    private Skin skin;
    SpriteBatch batch;

    ScreenViewport viewport;

    private PiazzaPanic piazzaPanic;



    public ModeScreen(PiazzaPanic piazzaPanic) {
        this.piazzaPanic = piazzaPanic;
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        //Buttons
        scenarioButton = new Sprite(new Texture("ModeScreen/ScenarioButton.png")); //todo put actual texture
        endlessButton = new Sprite(new Texture("ModeScreen/EndlessButton.png")); //todo put actual texture
        //playButton = new Sprite(new Texture("ModeScreen/MenuScreenPlay.png"));

        //Centers

        scenarioButton.setCenter(0, 0);
        endlessButton.setCenter(0, -100);
        viewport = new ScreenViewport();

        TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(Gdx.graphics.getWidth() - 100, 10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("The back button was pressed");

                piazzaPanic.goToMenu();

            }
        });
        stage.addActor(backButton);
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
        scenarioButton.draw(batch);
        endlessButton.draw(batch);


        // Check if the left mouse button is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Get the coordinates of the mouse click
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            // show viewport coordinate system
            Vector3 position = viewport.unproject(new Vector3(x, y, 0));

            // Check if mouse pressed
            if (scenarioButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The scenario button was pressed");

                // Switch to Scenario Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                piazzaPanic.ScenarioScreen1();
            }

            if (endlessButton.getBoundingRectangle().contains(position.x, position.y)) {
                // Code to be executed when the button is pressed
                System.out.println("The endless button was pressed");

                // Switch to endless Screen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                piazzaPanic.EndlessScreen1();
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



