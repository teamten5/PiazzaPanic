package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Config;
import com.mygdx.game.GameViewport;
import com.mygdx.game.Ingredient;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableType;
import com.mygdx.game.levels.Level;
import java.util.ArrayList;
import java.util.HashMap;




public class GameScreen extends InputAdapter implements Screen {
	private PiazzaPanic game;
	private boolean paused = false;
	private PauseScreen pauseScreen;
	private final PolygonSpriteBatch batch;
	private final ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	GameViewport viewport;

	// A reference to the main game file
	private final PiazzaPanic main;

	private Level currentLevel;

	public GameScreen(
			PiazzaPanic main,
			HashMap<String, Ingredient> ingredientHashMap,
			HashMap<String, InteractableType> interactableTypeHashMap,
			HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
			HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
			Level level
	) {
		this.currentLevel = level;
		this.main = main;

		// Set up camera
		camera = new OrthographicCamera();
		viewport = new GameViewport(
				15,
				15,
				camera,
				Config.unitWidthInPixels,
				Config.unitHeightInPixels,
				Config.scaling
		);

		// Create processor to handle user input
		batch = new PolygonSpriteBatch();

		shapeRenderer = new ShapeRenderer();
	}


	//==========================================================\\
	//                         START                            \\
	//==========================================================\\
	@Override
	public void show() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseScreen(game.getGame()));
		}

	}


	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\
	@Override
	public void render(float delta) {
		// Update the level
		currentLevel.update(delta);


		// Clear the screen and begin drawing process
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		batch.begin();

		currentLevel.render(batch);

		// End the process
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		currentLevel.renderShapes(shapeRenderer);
		shapeRenderer.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			togglePause();

		}
	}


	//==========================================================\\
	//                 OTHER REQUIRED METHODS                   \\
	//==========================================================\\

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		batch.setProjectionMatrix(camera.combined);
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

	void togglePause() {
		paused = !paused;
		if (paused) {
			// Pause game logic or animation here
			System.out.println("PAuse");
			pauseScreen = new PauseScreen(this.main);
			this.main.setScreen(pauseScreen);
		} else {
			// Resume game logic or animation here
			this.main.setScreen(this);
		}
	}

}