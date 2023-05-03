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
    List<com.badlogic.gdx.math.Rectangle> validArea = new ArrayList<Rectangle>(Arrays.asList(new Rectangle(-10, -10, 100, 100)));
    Texture testchef = new Texture("textures/temp_chef_1.png");
    PlayerType testingplayer = new PlayerType(testchef, new Spot(1, 1, 1, 1, "testing"), "testing");
    LevelType leveltype = new LevelType(
            new ArrayList<>(),
            validArea,
            10,
            10,
            new ArrayList<>(),
            new ArrayList<>(),
            List.of(testingplayer),
            "potato"
    );
    Profile testingprofile = new Profile(testchef, new ArrayList<>(), 1, 1, 1, 1, 1, 1, 1, 1, new Spot(1, 1, 1, 1, "testing"), List.of(new Spot(1, 1, 1, 1, "testing1")), new ArrayList<>(), new MockSound(), "testing");
    Group testingGroup = new Group(List.of(testingprofile));
    Customer testingCustomer = new Customer(testingprofile, testingGroup);
    Difficulty testingdiff = new Difficulty("test", List.of(testingprofile), 1, 10, 10, 5, 5);
    Level level = new Level(leveltype, testingdiff);


    @BeforeEach
    public void setup() {

    }

    @Test
    public void testSpot(){
        Spot spotTest = new Spot(1, 1, 1, 1, "testing");
        testingCustomer.setSpot(spotTest);
        assertEquals(spotTest, testingCustomer.spot);
    }

    public void testSave1Customer(){
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

