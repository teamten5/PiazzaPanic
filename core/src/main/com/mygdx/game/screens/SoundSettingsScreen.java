package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Config;
import com.mygdx.game.PiazzaPanic;
public class SoundSettingsScreen implements Screen {
    private final PiazzaPanic game;
    private Stage stage;
    private Skin skin;
    private Slider volumeMasterSlider;
    private Slider volumeMusicSlider;
    private Slider volumeEffectSlider;
    ScreenViewport viewport;

    Sprite backButton;
    SpriteBatch batch;


    public SoundSettingsScreen(final PiazzaPanic game) {
        batch = new SpriteBatch();
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("gdx-skins-master/gdx-holo/skin/uiskin.json"));
        viewport = new ScreenViewport();

        backButton = new Sprite(new Texture("EndlessScreen/BackButton.png")); //todo put actual texture


        //CENTERS
        backButton.setCenter(0,-600);

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

        skin = new Skin(Gdx.files.internal("gdx-skins-master/gdx-holo/skin/uiskin.json"));
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
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1); // todo choose colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        batch.begin();
        backButton.draw(batch);






        batch.end();
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
