package com.mygdx.game.actors;

import com.mygdx.game.interact.Interactable;

/**

The Spot class represents a spot on the game map where a customer is located. It contains the position and direction of the spot, as well as an optional attached table that the customer can interact with.
*/


public class Spot {
    public float posX;
    public float posY;
    public float facingX;
    public float facingY;
    public Interactable attached_table;

    public Customer occupiedBy;

    final String name;

    public Spot(float posX, float posY, float facingX, float facingY,
          String name, Interactable attached_table) {
        this.posX = posX;
        this.posY = posY;
        this.facingX = facingX;
        this.facingY = facingY;
        this.name = name;
        attachTable(attached_table);

    }
    public Spot(float posX, float posY, float facingX, float facingY, String name) {
        this(posX, posY, facingX, facingY, name, null);
    }

    public void attachTable(Interactable attached_table) {
        this.attached_table = attached_table;
        if (attached_table != null) {
            attached_table.attachedSpots.add(this);
        }
    }



}

