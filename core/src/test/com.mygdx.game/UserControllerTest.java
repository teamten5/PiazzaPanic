/**
package com.mygdx.game;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.backends.headless.mock.audio.MockSound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.PlayerType;
import com.mygdx.game.actors.Profile;
import com.mygdx.game.actors.Spot;
import com.mygdx.game.actors.controllers.UserController;
import com.mygdx.game.Config;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.levels.Difficulty;
import com.mygdx.game.levels.LevelType;
import com.mygdx.game.util.TestingController;
import org.junit.jupiter.api.BeforeEach;
import com.mygdx.game.levels.Level;
import com.mygdx.game.actors.Player;
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

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Array;
import java.util.*;

import static org.mockito.Mockito.mock;
import com.mygdx.game.actors.controllers.Controller;

@RunWith(TestingApplicationListener.class)

public class UserControllerTest {


    @Test
    public void inputTest() throws AWTException{
        UserController testingcontroller = new UserController();
        Robot robot = new Robot();
        float oldY = testingcontroller.y;
        robot.keyPress(KeyEvent.VK_DOWN);
        testingcontroller.update(0.2f);
        assertNotEquals(testingcontroller.y, oldY);
    }
}
*/
