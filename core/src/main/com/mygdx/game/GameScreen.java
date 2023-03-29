package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.customer.CustomerEngine;
import com.mygdx.game.interact.InteractEngine;
import com.mygdx.game.player.PlayerEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The GameScreen class handles the main rendering and updating of the game.
 *
 */

public class GameScreen extends InputAdapter implements Screen {

	SpriteBatch batch;

	OrthographicCamera camera;
	GameViewport viewport;

	// A timer to track how long the screen has been running
	static float masterTimer;
	private Label timerLabel;

	// A reference to the main game file
	private final PiazzaPanic main;


	public GameScreen(PiazzaPanic main)
	{
		this.main = main;

		// Set up camera
		camera = new OrthographicCamera();
		viewport = new GameViewport(15, 15, camera,16, 3);
		// Create processor to handle user input
		batch = new SpriteBatch();

		// Initialise Engine scripts
		PlayerEngine.initialise();
		CustomerEngine.initialise();
		InteractEngine.initialise();

		masterTimer = 0f;

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		BitmapFont font = new BitmapFont();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;

		timerLabel = new Label("0s", labelStyle);
		timerLabel.setPosition(-1, -1);
		timerLabel.setAlignment(Align.left);


	}

	
	//==========================================================\\
	//                         START                            \\
	//==========================================================\\
	@Override
	public void show() {

	}

	
	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\
	@Override
	public void render(float delta) {
		
		// Clear the screen and begin drawing process
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
				
		// Update the render

		PlayerEngine.update(delta);
		InteractEngine.update(delta);
		CustomerEngine.update(delta);

		InteractEngine.render(batch);
		CustomerEngine.render(batch);
		PlayerEngine.render(batch);

		// End the process
		batch.end();

		// Increment the timer and update UI
		masterTimer += Gdx.graphics.getDeltaTime();
		timerLabel.setText((int) masterTimer);

		// Check for game over state
		if(CustomerEngine.getCustomersRemaining() == 0 && main != null)
		{
			main.endGame("SCENARIO COMPLETED IN\n" + (int) masterTimer + " seconds");
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
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
