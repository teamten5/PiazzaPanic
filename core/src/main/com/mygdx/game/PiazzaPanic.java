package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableType;
import com.mygdx.game.levels.Level;
import com.mygdx.game.levels.LevelType;
import com.mygdx.game.levels.Modifier;
import com.mygdx.game.screens.EndScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PiazzaPanic extends Game {

	public static Random random = new Random();

	// Screens
	GameScreen gameScreen;
	EndScreen endScreen;
	MenuScreen menuScreen;

	HashMap<String, Ingredient> ingredientHashMap;
	HashMap<String, InteractableType> interactableTypeHashMap;
	HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap;
	HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap;
	HashMap<String, LevelType> levelTypeHashMap;

	HashMap<String, Modifier> modifierHashMap;
	JsonReader jsonReader = new JsonReader();

	Level currentLevel;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Config.loggingLevel);
		loadJson();
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public void startGame()
	{
		System.out.println("GAME STARTED");
		currentLevel = levelTypeHashMap.get("arcade-salad").instantiate(0);
		gameScreen = new GameScreen(this, ingredientHashMap, interactableTypeHashMap, combinationsHashmap, actionHashmap, currentLevel);
		setScreen(gameScreen);
	}

	public void endGame(String displayDetails)
	{
		System.out.println("GAME ENDED");
		endScreen = new EndScreen(this, displayDetails);
		setScreen(endScreen);
	}

	public void goToMenu()
	{
		System.out.println("RETURNED TO MAIN MENU");
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	private void loadJson() {

		JsonValue jsonRoot = jsonReader.parse(Gdx.files.internal("data/base.json"));
		ingredientHashMap = Ingredient.loadFromJson1(
			jsonRoot.get("ingredients")
		);
		interactableTypeHashMap = InteractableType.loadFromJson2(
			jsonRoot.get("interactables")
		);
		Ingredient.loadFromJson3(
			jsonRoot.get("ingredients"),
			ingredientHashMap,
			interactableTypeHashMap
		);
		combinationsHashmap = Combination.loadFromJson(
			jsonRoot.get("combinations"),
			jsonRoot.get("interactables"),
			jsonRoot.get("profiles"),
			ingredientHashMap,
			interactableTypeHashMap
		);
		actionHashmap = Action.	loadFromJson(
			jsonRoot.get("actions"),
			ingredientHashMap,
			interactableTypeHashMap);


		modifierHashMap = Modifier.loadFromJson(
			jsonRoot.get("modifiers")
		);
		// profiles only exist at the json root for convenience.
		// a new Profile is created each time it is used in a level and are therefore generated here.
		levelTypeHashMap = LevelType.loadFromJson(
			jsonRoot.get("levels"),
			interactableTypeHashMap,
			combinationsHashmap,
			actionHashmap,
			jsonRoot.get("profiles"),
			ingredientHashMap,
			modifierHashMap
		);
	}

	public void saveGame(int saveSlot) {
		FileHandle saveFileHandle = Gdx.files.local("save-" + saveSlot);

		JsonValue saveJson = currentLevel.saveGame();
		String saveData = saveJson.toJson(OutputType.json);

		saveFileHandle.writeString(saveData, false);
	}

	public Level loadGame(int saveSlot) {
		JsonValue saveData = jsonReader.parse(Gdx.files.local("save-" + saveSlot));
		return new Level(
			saveData,
			ingredientHashMap,
			interactableTypeHashMap,
			combinationsHashmap,
			actionHashmap,
			levelTypeHashMap,
			modifierHashMap,
			jsonReader.parse(Gdx.files.internal("data/base.json")).get("profiles")
		);
	}
}
