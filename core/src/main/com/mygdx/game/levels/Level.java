package com.mygdx.game.levels;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;
import com.mygdx.game.Ingredient;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.actors.Group;
import com.mygdx.game.actors.Profile;
import com.mygdx.game.actors.Spot;
import com.mygdx.game.actors.controllers.Controller;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.controllers.NullController;
import com.mygdx.game.actors.controllers.UserController;
import com.mygdx.game.interact.InteractableType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Level {

    Texture fullReputation;
    Texture emptyReputation;

    public enum State {
        DAY,
        NIGHT
    }

    public State state;

    public List<Modifier> appliedModifiers =  new ArrayList<>();

    public List<Modifier> possibleModifiers;

    public final LevelType type;

    public List<Player> players;
    private Interactable[] interactables;
    private ArrayDeque<Integer> unactivePlayers;


    private int minGroupSize;
    private int maxGroupSize;
    private int dailyCustomers;
    private int day = -1;
    private float dayLength;
    private float timeInDay;
    private List<Profile> profiles;

    private List<Group> futureGroups = new ArrayList<>();

    private List<Group> currentGroups = new ArrayList<>();
    int groupsToday;

    public float chefWalkSpeedMultiplier = 1;
    public float customerWaitForSeatMultiplier = 1;
    public float customerPickSpeedMultiplier = 1;
    public float customerWaitForOrderTakenMultiplier = 1;
    public float customerOrderSpeedMultiplier = 1;
    public float customerWaitForFoodMultiplier = 1;
    public float customerWaitForGroupFoodMultiplier = 1;
    public float customerEatSpeedMultiplier = 1;
    public float dayLengthMultiplier = 1;
    public float customersNumberMultiplier = 1;
    public float actionSpeedMultiplier = 1;

    public int maxReputation = 3;

    public int currentReputation = 3;
    public Modifier leftModifier;
    public Modifier rightModifier;

    private Difficulty difficulty;
    public Level(LevelType type, Difficulty difficulty) {

        fullReputation = new Texture("textures/" + "full_heart.png");
        emptyReputation = new Texture("textures/" + "empty_heart.png");

        this.type = type;
        this.difficulty = difficulty;

        players = type.playerTypes.values().stream().map(x -> x.instantiate(new NullController(), this)).toList();
        players.get(0).controller = new UserController();

        interactables = type.interactables.stream().map(InteractableInLevel::initialise).toArray(Interactable[]::new);
        for (Interactable interactable: interactables) {
            for (Spot attachedSpot: interactable.attachedSpots) {
                attachedSpot.attached_table = interactable;
            }
        }

        this.possibleModifiers = List.copyOf(type.possibleModifiers);

        unactivePlayers = new ArrayDeque<>();
        for (int i = 1; i < players.size(); i++) {
            unactivePlayers.push(i);
        }

        minGroupSize = difficulty.startingGroupMinSize;
        maxGroupSize = difficulty.startingGroupMaxSize;
        dailyCustomers = difficulty.startingCustomers;
        dayLength = difficulty.startingDayLength;
        profiles = List.copyOf(difficulty.startingProfiles);



        startDay();
    }

    public Level(
          JsonValue levelSaveData,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, InteractableType> interactableTypeHashMap,
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
          HashMap<String, LevelType> levelTypeHashMap,
          HashMap<String, Modifier> modifierHashMap,
          JsonValue jsonProfiles
    ) {


        fullReputation = new Texture("textures/" + "full_heart.png");
        emptyReputation = new Texture("textures/" + "empty_heart.png");

        this.state = State.valueOf(levelSaveData.getString("state"));
        this.type = levelTypeHashMap.get(levelSaveData.getString("type"));

        this.players = new ArrayList<>();
        for (JsonValue playerData: levelSaveData.get("players")) {
            this.players.add(new Player(
                  this.type.playerTypes.get(playerData.getString("type")),
                  Controller.loadGame(playerData.get("controller")),
                  this
            ));
        }

        unactivePlayers = new ArrayDeque<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).controller.getClass() == NullController.class) {
                unactivePlayers.add(i);
            }
        }



        minGroupSize = levelSaveData.getInt("min-group-size");
        maxGroupSize = levelSaveData.getInt("max-group-size");
        dailyCustomers = levelSaveData.getInt("daily-customers");
        day = levelSaveData.getInt("day");
        dayLength = levelSaveData.getInt("day-length");
        timeInDay = levelSaveData.getInt("time-in-day");

        profiles = new ArrayList<>();
        for (JsonValue profileSaveData: levelSaveData.get("profiles")) {
            profiles.add(Profile.loadGame(
                  jsonProfiles,
                  profileSaveData,
                  ingredientHashMap,
                  type.spotHashMap,
                  type.eatingSpots
            ));
        }

        futureGroups = new ArrayList<>();
        for (JsonValue futureGroupSaveData: levelSaveData.get("future-groups")) {
            futureGroups.add(
                  Group.loadGame(
                        futureGroupSaveData,
                        jsonProfiles,
                        ingredientHashMap,
                        this.type.spotHashMap,
                        this.type.eatingSpots,
                        this
                  )
            );
        }

        currentGroups = new ArrayList<>();
        for (JsonValue futureGroupSaveData: levelSaveData.get("current-groups")) {
            currentGroups.add(
                  Group.loadGame(
                        futureGroupSaveData,
                        jsonProfiles,
                        ingredientHashMap,
                        this.type.spotHashMap,
                        this.type.eatingSpots,
                        this
                  )
            );
        }

        for (JsonValue jsonModifier: levelSaveData.get("possible-modifiers")) {
            possibleModifiers.add(modifierHashMap.get(jsonModifier));
        }

        for (JsonValue jsonModifier: levelSaveData.get("applied-modifiers")) {
            addModifier(modifierHashMap.get(jsonModifier));
        }

        maxReputation = levelSaveData.getInt("max-reputation");
        currentReputation = levelSaveData.getInt("current-reputation");

        if (levelSaveData.hasChild("left-modifier")) {
            leftModifier = modifierHashMap.get(levelSaveData.getString("left-modifier"));
            rightModifier = modifierHashMap.get(levelSaveData.getString("right-modifier"));
        }

    }

    public void addModifier(Modifier modifier) {
        appliedModifiers.add(modifier);

        for(Profile profile: modifier.newProfiles) {
            profiles.add(profile);
        }

        chefWalkSpeedMultiplier *= modifier.chefWalkSpeedMultiplier;
        customerWaitForSeatMultiplier *= modifier.customerWaitForSeatMultiplier;
        customerPickSpeedMultiplier *= modifier.customerPickSpeedMultiplier;
        customerWaitForOrderTakenMultiplier *= modifier.customerWaitForOrderTakenMultiplier;
        customerOrderSpeedMultiplier *= modifier.customerOrderSpeedMultiplier;
        customerWaitForFoodMultiplier *= modifier.customerWaitForFoodMultiplier;
        customerWaitForGroupFoodMultiplier *= modifier.customerWaitForGroupFoodMultiplier;
        customerEatSpeedMultiplier *= modifier.customerEatSpeedMultiplier;
        dayLength *= modifier.dayLengthMultiplier;

        actionSpeedMultiplier *= modifier.actionSpeedMultiplier;

        dailyCustomers = max(1, dailyCustomers + modifier.customersNumberChange);

        float dailyCustomersTemp = dailyCustomers;
        dailyCustomersTemp /= customersNumberMultiplier;
        customersNumberMultiplier *= modifier.customersNumberMultiplier;
        dailyCustomersTemp *= customersNumberMultiplier;
        dailyCustomers = round(dailyCustomersTemp);


        maxGroupSize = max(1, maxGroupSize + modifier.maxGroupSizeChange);
        minGroupSize = max(1, minGroupSize + modifier.minGroupSizeChange);

        dailyCustomers = max(dailyCustomers, minGroupSize);

        currentReputation = currentReputation + modifier.recoverReputation;

        maxReputation = max(1, maxReputation + modifier.changeMaxReputation);

        currentReputation = min(maxReputation, currentReputation);

        for (String newModifierName: modifier.addsModifiers) {
            possibleModifiers.add(type.modifierHashMap.get(newModifierName));
        }

    }

    public void update(float delta) {
        switch (state) {

            case DAY -> {
                timeInDay += delta;

                if (timeInDay <= dayLength && timeInDay / dayLength > (float)currentGroups.size() / groupsToday) {
                    currentGroups.add(futureGroups.remove(0));
                }

                dayDoneChecks: {
                    if (futureGroups.size() != 0) {
                        break dayDoneChecks;
                    }
                    for (Group group: currentGroups) {
                        if (group.active) {
                            break dayDoneChecks;
                        }
                    }
                    startNight();
                }



                for (Interactable interactable: interactables) {
                    interactable.update(delta);
                }
                for (Player player: players) {
                    player.update(delta);
                }
                for (Group group: currentGroups) {
                    group.update(delta);
                }
            }
            case NIGHT -> {
                if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
                    int x = Gdx.input.getX();
                    int y = Gdx.input.getY();
                    if (
                          Gdx.graphics.getHeight()/2f -(leftModifier.texture.getHeight() * Config.scaling) / 2f < y &&
                                Gdx.graphics.getHeight()/2f +(leftModifier.texture.getHeight() * Config.scaling) / 2f > y &&
                                Gdx.graphics.getWidth()/2f -(leftModifier.texture.getWidth() * Config.scaling) < y &&
                                Gdx.graphics.getWidth()/2f > y
                    ) {
                        addModifier(leftModifier);
                        startDay();
                    } else if (
                          Gdx.graphics.getHeight()/2f -(rightModifier.texture.getHeight() * Config.scaling) / 2f < y &&
                                Gdx.graphics.getHeight()/2f +(rightModifier.texture.getHeight() * Config.scaling) / 2f > y &&
                                Gdx.graphics.getWidth()/2f < y &&
                                Gdx.graphics.getWidth()/2f +(rightModifier.texture.getWidth() * Config.scaling) > y
                    ) {
                        addModifier(rightModifier);
                        startDay();
                    }
                }
            }
        }



    }

    public void swapPlayers(Player oldPlayer, Controller controller) {
        if (unactivePlayers.isEmpty()) {
            return;
        }
        players.get(unactivePlayers.pop()).controller = controller;
        oldPlayer.controller = new NullController(controller.x, controller.y,  controller.facingY, controller.facingX, controller.doAction);
        unactivePlayers.add(players.indexOf(oldPlayer));

    }

    public void loseReputation() {
        currentReputation -= 1;
        if (currentReputation <= 0) {
            //todo LOSE CONDITION
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public void startDay() {
        setState(State.DAY);
        timeInDay = -3;
        day += 1;
        generateNewDayCustomers();
    }

    public void startNight() {
        if (day == difficulty.days) {
            // TODO WIN CONDITION
        }
        setState(State.NIGHT);
        leftModifier = possibleModifiers.get(PiazzaPanic.random.nextInt(possibleModifiers.size()));
        rightModifier = possibleModifiers.get(PiazzaPanic.random.nextInt(possibleModifiers.size()));

    }

    public void render(PolygonSpriteBatch batch) {
        for (Interactable interactable: interactables) {
            interactable.renderBottom(batch);
        }

        for (Group group: currentGroups) {
            group.render(batch);
        }

        for(Player player : players) {
            player.render(batch);
        }

        for (Interactable interactable: interactables) {
            interactable.renderTop(batch);
        }
    }

    public void renderUI(Batch batch) {


        if (state == State.NIGHT) {
            batch.draw(
                  leftModifier.texture,
                  -leftModifier.texture.getWidth() * Config.scaling,
                  -(leftModifier.texture.getHeight() * Config.scaling) / 2f,
                  leftModifier.texture.getWidth() * Config.scaling,
                  leftModifier.texture.getHeight() * Config.scaling
            );
            batch.draw(
                  rightModifier.texture,
                  0,
                  -(leftModifier.texture.getHeight() * Config.scaling) / 2f,
                  leftModifier.texture.getWidth() * Config.scaling,
                  leftModifier.texture.getHeight() * Config.scaling
            );
        }

        for (int i = 0; i < maxReputation; i++) {
            if (currentReputation >= i + 1) {
                batch.draw(
                      fullReputation,
                      16 - Gdx.graphics.getWidth()/2f,
                      i*32 + 16 - Gdx.graphics.getHeight()/2f
                );
            } else {
                batch.draw(emptyReputation,
                      16 - Gdx.graphics.getWidth()/2f,
                      i*32 + 16 - Gdx.graphics.getHeight()/2f
                );
            }
        }
    }
    public void renderShapes(ShapeRenderer shapeRenderer) {
        if (Config.debugRendering) {
            shapeRenderer.setColor(0, 1, 0,1);
            for (Rectangle rect: type.chefValidAreas) {
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }
    public Interactable interactableAt(float x, float y) {
        for (Interactable interactable : interactables) {
            if ((interactable.instanceOf.xPos <= x && x <= interactable.instanceOf.xPos + interactable.instanceOf.type.xSize) && interactable.instanceOf.yPos <= y
                  && y <= interactable.instanceOf.yPos + interactable.instanceOf.type.ySize) {
                return interactable;
            }
        }
        return null;
    }

    private void generateNewDayCustomers() {
        futureGroups.clear();
        currentGroups.clear();
        while (dailyCustomers > 0) {
            int size;
            if (dailyCustomers > minGroupSize) {
                size = PiazzaPanic.random.nextInt(minGroupSize, maxGroupSize + 1);
                dailyCustomers -= size;
            } else {
                size = dailyCustomers;
                dailyCustomers = 0;
            }
            List<Profile> groupProfiles = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                groupProfiles.add(profiles.get(PiazzaPanic.random.nextInt(profiles.size())));
            }
            futureGroups.add(new Group(groupProfiles, this));
        }
        groupsToday = futureGroups.size();
    }

    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("state", new JsonValue(state.toString()));
        saveData.addChild("type", new JsonValue(type.name));
        JsonValue playersSaveData = new JsonValue(ValueType.array);
        for (Player player: players) {
            playersSaveData.addChild(player.saveGame());
        }
        saveData.addChild("players", playersSaveData);

        saveData.addChild("min-group-size", new JsonValue(minGroupSize));
        saveData.addChild("max-group-size", new JsonValue(maxGroupSize));
        saveData.addChild("daily-customers", new JsonValue(dailyCustomers));
        saveData.addChild("day", new JsonValue(day));
        saveData.addChild("day-length", new JsonValue(dayLength));
        saveData.addChild("time-in-day", new JsonValue(timeInDay));

        JsonValue profilesSaveData = new JsonValue(ValueType.array);
        for (Profile profile: profiles) {
            profilesSaveData.addChild(profile.saveGame());
        }
        saveData.addChild("profiles", playersSaveData);

        JsonValue futureGroupsSaveData = new JsonValue(ValueType.array);
        for (Group group: futureGroups) {
            futureGroupsSaveData.addChild(group.saveGame());
        }
        saveData.addChild("future-groups", playersSaveData);

        JsonValue currentGroupsSaveData = new JsonValue(ValueType.array);
        for (Group group: futureGroups) {
            currentGroupsSaveData.addChild(group.saveGame());
        }
        saveData.addChild("current-groups", playersSaveData);

        JsonValue possibleModifiersSaveData = new JsonValue((ValueType.array));
        for (Modifier modifier: possibleModifiers) {
            possibleModifiersSaveData.addChild(new JsonValue(modifier.name));
        }

        saveData.addChild("possible-modifiers", possibleModifiersSaveData);

        JsonValue appliedModifiersSaveData = new JsonValue((ValueType.array));
        for (Modifier modifier: appliedModifiers) {
            appliedModifiersSaveData.addChild(new JsonValue(modifier.name));
        }

        saveData.addChild("applied-modifiers", appliedModifiersSaveData);

        saveData.addChild("max-reputation", new JsonValue(maxReputation));
        saveData.addChild("current-reputation", new JsonValue(currentReputation));
        if (leftModifier != null) {
            saveData.addChild("left-modifier", new JsonValue(leftModifier.name));
            saveData.addChild("right-modifier", new JsonValue(rightModifier.name));
        }

        return saveData;

    }
}
