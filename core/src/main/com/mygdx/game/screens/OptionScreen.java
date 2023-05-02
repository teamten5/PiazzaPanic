package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Config;
import com.mygdx.game.PiazzaPanic;


public class OptionScreen implements Screen {

    private final PiazzaPanic game;
    private Stage stage;
    private Skin skin;
    private Slider volumeMasterSlider;
    private Slider volumeMusicSlider;
    private Slider volumeEffectSlider;
    ScreenViewport viewport;

    //Sprite back;

    public OptionScreen(final PiazzaPanic game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        viewport = new ScreenViewport();
        //back = new Sprite(new Texture("textures/MenuScreenQuit.png"));

        TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(Gdx.graphics.getWidth() - 100, 10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("The back button was pressed");

                game.goToMenu();

            }
        });
        stage.addActor(backButton);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();


        volumeMasterSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeMasterSlider.setValue(Config.volumeMaster);

        volumeMusicSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeMusicSlider.setValue(Config.volumeMusic);

        volumeEffectSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeEffectSlider.setValue(Config.volumeEffects);

        skin = new Skin(Gdx.files.internal("assets/gdx-skins-master/gdx-holo/skin/uiskin.json"));
        table.setSkin(skin);
        table.row();
        table.add("Master Sound Level:").left().padRight(10);
        table.add(volumeMasterSlider).width(300);
        table.row();
        table.add("Music Sound Level:").left().padRight(10);
        table.add(volumeMusicSlider).width(300);
        table.row();
        table.add("Effect Sound Level:").left().padRight(10);
        table.add(volumeEffectSlider).width(300);
        table.row();
//            table.add("Back").colspan(2).center().padTop(50);


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    private void saveConfig() {
        Config.volumeMaster = volumeMasterSlider.getValue();
        Config.volumeMusic = volumeMusicSlider.getValue();
        Config.volumeEffects = volumeEffectSlider.getValue();
        Config.saveConfig();
    }
}