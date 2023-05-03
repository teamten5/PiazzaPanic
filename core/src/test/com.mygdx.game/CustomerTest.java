package com.mygdx.game;
import static com.mygdx.game.actors.Customer.State.ENTERING;
import static com.mygdx.game.actors.Customer.State.*;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.backends.headless.mock.audio.MockSound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.*;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.levels.Difficulty;
import com.mygdx.game.levels.LevelType;
import com.mygdx.game.util.TestingController;
import org.junit.jupiter.api.BeforeEach;
import com.mygdx.game.levels.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.util.TestingApplicationListener;
import com.mygdx.game.util.TestingLauncher;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.util.*;

import static org.mockito.Mockito.mock;
import com.mygdx.game.actors.controllers.Controller;

@RunWith(TestingApplicationListener.class)

public class CustomerTest {
    FileHandle handle = Gdx.files.internal("assets");

    FileHandle[] files = Gdx.files.internal("./").list();
    TestingController controller1 = new TestingController();
    Texture testchef = new Texture("textures/temp_chef_1.png");
    Profile testingprofile = new Profile(testchef, new ArrayList<>(), 1, 1, 1, 1, 1, 1, 1, 1, new Spot(1, 1, 1, 1, "testing"), List.of(new Spot(1, 1, 1, 1, "testing1")), new ArrayList<>(), new MockSound(), "testing");
    Difficulty testingdiff = new Difficulty("test", List.of(testingprofile), 1, 10, 10, 5, 5);
    List<com.badlogic.gdx.math.Rectangle> validArea = new ArrayList<Rectangle>(Arrays.asList(new Rectangle(-10, -10, 100, 100)));
    PlayerType testingplayer = new PlayerType(testchef, new Spot(1, 1, 1, 1, "testing"), "testing");
    HashMap<String, PlayerType> playerTHashmap = new HashMap<>();
    public HashMap<String, PlayerType> setpthash(){
        playerTHashmap.put("testingplayetype", testingplayer);
        return playerTHashmap;
    }
    LevelType leveltype = new LevelType(
            new ArrayList<>(),
            validArea,
            10,
            10,
            new ArrayList<>(),
            new ArrayList<>(),
            setpthash(),
            new HashMap<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new HashMap<>(),
            "potato"
            );
    Level level = new Level(leveltype, testingdiff);
    Group testingGroup = new Group(List.of(testingprofile), level);
    Customer testingCustomer = new Customer(testingprofile, testingGroup);
    Spot spotTest = new Spot(1, 1, 1, 1, "testing");


    @Before
    public void setup() {

        playerTHashmap.put("testingplayetype", testingplayer);
    }

    @Test
    public void testSpot(){
        testingCustomer.setSpot(spotTest);
        assertEquals(spotTest, testingCustomer.spot);
    }

/* //Method for equality in json values


    public Boolean jsonValueEquals(JsonValue firstvalue, JsonValue secondvalue) {
        int zx = 0;
        for (JsonValue val : firstvalue) {
            if (firstvalue.get(zx) == secondvalue.get(zx)) {
                zx++;
            } else {
                return false;
            }

        }
        return true;
    }
    //Using .toString instead

 */



    @Test
    public void testSave1Customer(){
        testingCustomer.saveGame();
        JsonValue testsavedata = new JsonValue(JsonValue.ValueType.object);
        JsonValue saveData = new JsonValue(JsonValue.ValueType.object);

        testsavedata.addChild("x", new JsonValue(testingCustomer.posX));
        testsavedata.addChild("y", new JsonValue(testingCustomer.posY));
        testsavedata.addChild("controller", testingCustomer.controller.saveGame());
        testsavedata.addChild("spot", new JsonValue("testing"));
        testsavedata.addChild("profile", testingCustomer.profile.saveGame());
        testsavedata.addChild("current-order", new JsonValue(testingCustomer.currentOrder));
        testsavedata.addChild("progress", new JsonValue(testingCustomer.progress));
        testsavedata.addChild("state", new JsonValue(testingCustomer.state.name()));
        testsavedata.addChild("place-in-queue", new JsonValue(testingCustomer.placeInQueue));
        assertEquals(testsavedata.toString(),testingCustomer.saveGame().toString());
    }

    @Test
    public void testState() {
        float delta = 0.2f; // set the time interval
        testingCustomer.setState(ENTERING);
        assertTrue(testingCustomer.state==ENTERING);
        testingCustomer.setState(PICKING);
        assertTrue(testingCustomer.state==PICKING);
        testingCustomer.setState(WAITING_FOR_ORDER_TO_BE_TAKEN);
        assertTrue(testingCustomer.state==WAITING_FOR_ORDER_TO_BE_TAKEN);
        testingCustomer.setState(ORDERING);
        assertTrue(testingCustomer.state==ORDERING);
        testingCustomer.setState(WAITING_FOR_FOOD);
        assertTrue(testingCustomer.state==WAITING_FOR_FOOD);
        testingCustomer.setState(WAITING_FOR_GROUP_FOOD);
        assertTrue(testingCustomer.state==WAITING_FOR_GROUP_FOOD);
        testingCustomer.setState(EATING);
        assertTrue(testingCustomer.state==EATING);
        testingCustomer.setState(LEAVING);
        assertTrue(testingCustomer.state==LEAVING);

    }
}

