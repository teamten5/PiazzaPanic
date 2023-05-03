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

public class ScenarioScreen implements Screen {
    String level;
    int difficulty = 1;
    Sprite easyButton;
    Color easyButtonDefault;
    Sprite mediumButton;

    Color mediumButtonDefault;
    Sprite hardButton;
    Color hardButtonDefault;
    Sprite playButton;
    Sprite backButton;
    Sprite pizzasButton;
    Color pizzasButtonDefault;
    Sprite jacketPotatoesButton;
    Color jacketPotatoesButtonDefault;
    Sprite burgersButton;
    Color burgersButtonDefault;

    Sprite saladsButton;
    Color saladsButtonDefault;






    private Stage stage;

    private Skin skin;
    SpriteBatch batch;

    ScreenViewport viewport;

    private PiazzaPanic piazzaPanic;


    public ScenarioScreen(PiazzaPanic piazzaPanic) {
        this.piazzaPanic = piazzaPanic;
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        //Burger Buttons
        burgersButton = new Sprite(new Texture("ScenarioScreen/BurgersButton.png")); //todo put actual texture
        jacketPotatoesButton = new Sprite(new Texture("ScenarioScreen/JacketPotatoesButton.png")); //todo put actual texture
        pizzasButton = new Sprite(new Texture("ScenarioScreen/PizzasButton.png")); //todo put actual texture
        saladsButton = new Sprite(new Texture("ScenarioScreen/SaladsButton.png")); //todo put actual texture

        //Difficulty Buttons
        easyButton = new Sprite(new Texture("ScenarioScreen/EasyButton.png")); //todo put actual texture
        mediumButton = new Sprite(new Texture("ScenarioScreen/MediumButton.png")); //todo put actual texture
        hardButton = new Sprite(new Texture("ScenarioScreen/HardButton.png")); //todo put actual texture

        //OTHER BUTTONS
        playButton = new Sprite(new Texture("ScenarioScreen/StartButton.png")); //todo put actual texture
        backButton = new Sprite(new Texture("ScenarioScreen/BackButton.png")); //todo put actual texture

        //DEFAULT COLOURS
        burgersButtonDefault = new Sprite(new Texture("ScenarioScreen/BurgersButton.png")).getColor();getColor(burgersButtonDefault);
        jacketPotatoesButtonDefault = new Sprite(new Texture("ScenarioScreen/JacketPotatoesButton.png")).getColor();getColor(jacketPotatoesButtonDefault);
        pizzasButtonDefault = new Sprite(new Texture("ScenarioScreen/PizzasButton.png")).getColor();getColor(pizzasButtonDefault);
        saladsButtonDefault = new Sprite(new Texture("ScenarioScreen/SaladsButton.png")).getColor();getColor(saladsButtonDefault);

        easyButtonDefault = new Sprite(new Texture("ScenarioScreen/EasyButton.png")).getColor();getColor(easyButtonDefault);
        mediumButtonDefault = new Sprite(new Texture("ScenarioScreen/MediumButton.png")).getColor();getColor(mediumButtonDefault);
        hardButtonDefault = new Sprite(new Texture("ScenarioScreen/HardButton.png")).getColor();getColor(hardButtonDefault);

        //Centers
        burgersButton.setCenter(-450, 0);
        jacketPotatoesButton.setCenter(-150, -0);
        pizzasButton.setCenter(150, -0);
        saladsButton.setCenter(450, -0);


        easyButton.setCenter(-300, -100);
        mediumButton.setCenter(0, -100);
        hardButton.setCenter(300, -100);

        backButton.setCenter(0,-500);
        playButton.setCenter(0,-300);


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
            burgersButton.draw(batch);
            jacketPotatoesButton.draw(batch);
            pizzasButton.draw(batch);
            saladsButton.draw(batch);
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


                if (burgersButton.getBoundingRectangle().contains(position.x, position.y)) {

                    //LEVEL FOR START todo How to start level for burgers
                    level = "arcade-salad";

                    //BUTTON COLOURS
                    burgersButton.setColor(1, 0, 0, 1);
                    jacketPotatoesButton.setColor(jacketPotatoesButtonDefault.r, jacketPotatoesButtonDefault.g, jacketPotatoesButtonDefault.b, jacketPotatoesButtonDefault.a);
                    pizzasButton.setColor(pizzasButtonDefault.r, pizzasButtonDefault.g, pizzasButtonDefault.b, pizzasButtonDefault.a);
                    saladsButton.setColor(saladsButtonDefault.r, saladsButtonDefault.g, saladsButtonDefault.b, saladsButtonDefault.a);
                }

                if (jacketPotatoesButton.getBoundingRectangle().contains(position.x, position.y)) {

                    //LEVEL FOR START todo How to start level for jacket potatoes
                    level = "arcade-salad";

                    //BUTTON COLOURS
                    jacketPotatoesButton.setColor(1, 0, 0, 1);
                    burgersButton.setColor(burgersButtonDefault.r, burgersButtonDefault.g, burgersButtonDefault.b, burgersButtonDefault.a);
                    pizzasButton.setColor(pizzasButtonDefault.r, pizzasButtonDefault.g, pizzasButtonDefault.b, pizzasButtonDefault.a);
                    saladsButton.setColor(saladsButtonDefault.r, saladsButtonDefault.g, saladsButtonDefault.b, saladsButtonDefault.a);
                }

                if (pizzasButton.getBoundingRectangle().contains(position.x, position.y)) {

                    //LEVEL FOR START todo How to start level for pizzas
                    level = "arcade-salad";

                    //BUTTON COLOURS
                    pizzasButton.setColor(1, 0, 0, 1);
                    burgersButton.setColor(burgersButtonDefault.r, burgersButtonDefault.g, burgersButtonDefault.b, burgersButtonDefault.a);
                    jacketPotatoesButton.setColor(jacketPotatoesButtonDefault.r, jacketPotatoesButtonDefault.g, jacketPotatoesButtonDefault.b, jacketPotatoesButtonDefault.a);
                    saladsButton.setColor(saladsButtonDefault.r, saladsButtonDefault.g, saladsButtonDefault.b, saladsButtonDefault.a);
                }

                if (saladsButton.getBoundingRectangle().contains(position.x, position.y)) {

                    //LEVEL FOR START todo How to start level for salads
                    level = "arcade-salad";

                    //BUTTON COLOURS
                    saladsButton.setColor(1, 0, 0, 1);
                    burgersButton.setColor(burgersButtonDefault.r, burgersButtonDefault.g, burgersButtonDefault.b, burgersButtonDefault.a);
                    jacketPotatoesButton.setColor(jacketPotatoesButtonDefault.r, jacketPotatoesButtonDefault.g, jacketPotatoesButtonDefault.b, jacketPotatoesButtonDefault.a);
                    pizzasButton.setColor(pizzasButtonDefault.r, pizzasButtonDefault.g, pizzasButtonDefault.b, pizzasButtonDefault.a);
                }


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





                if (backButton.getBoundingRectangle().contains(position.x, position.y)) {

                    piazzaPanic.goToMenu();
                }
                // Check if mouse pressed
                if (playButton.getBoundingRectangle().contains(position.x, position.y)) {

                    piazzaPanic.startGame(level, difficulty);

                }

            }

            batch.end();

        }












            @Override
            public void resize(int width, int height) {
                viewport.update(width,height);
            }

            @Override
            public void pause() {}

            @Override
            public void resume() {}

            @Override
            public void hide() {}

            @Override
            public void dispose() {}
        }




































