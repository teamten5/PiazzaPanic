package com.mygdx.game.interact.ingredient_stations;

import com.mygdx.game.interact.InteractableBase;

/**
 * @author Thomas McCarthy
 *
 * An ingredient station that gives a patty to the player.
 */
public class PattyStation extends InteractableBase {

    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public PattyStation(float xPos, float yPos) {
        super(xPos, yPos, "textures/station_patty.png", ingredientHashMap.get("patty-raw"));
    }
}
