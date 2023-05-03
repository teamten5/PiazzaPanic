package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;
import com.mygdx.game.Ingredient;
import com.mygdx.game.actors.controllers.Controller;
import com.mygdx.game.actors.controllers.CustomerController;
import java.util.HashMap;
import java.util.List;

public class Customer {
    public enum State {
        ENTERING,
        PICKING,
        WAITING_FOR_ORDER_TO_BE_TAKEN,
        ORDERING,
        WAITING_FOR_FOOD,
        WAITING_FOR_GROUP_FOOD,
        EATING,
        LEAVING
    }

    public float posX;
    public float posY;
    public float facingX;
    public float facingY;
    public Controller controller = new CustomerController(this);

    public Spot spot;
    public Profile profile;
    public int currentOrder = -1;
    public float progress = 0;
    public State state = State.ENTERING;
    public static Texture customerBlankOrder;
    public static Texture customerOrdering;
    public static Texture customerWaitingToOrder;

    public Group group;

    public int placeInQueue = 0;

    public Customer(Profile profile, Group group) {
        this.profile = profile;
        this.group = group;

        spot = profile.spawnLocation;
        customerBlankOrder = new Texture(Gdx.files.internal("textures/bubble-blank.png"));
        customerOrdering = new Texture(Gdx.files.internal("textures/bubble-dot-dot-dot.png"));
        customerWaitingToOrder = new Texture(Gdx.files.internal("textures/bubble-questionmark.png"));

    }

    public void update(float delta) {
        progress += delta;

        controller.update(delta);
        posX = posX + controller.x;
        posY = posY + controller.y;
        facingX = controller.facingX;
        facingY = controller.facingY;

        switch (state) {

            case ENTERING:
                if (profile.waitForSeatPatience * group.level.customerWaitForSeatMultiplier< progress) {
                    group.angryLeave();
                }
                if (profile.eatingSpots.contains(spot) && posX == spot.posX && posY == spot.posY) {
                    setState(State.PICKING);
                }

                lookingForNewSpot: {
                    eatingSpotsLoop: for (Spot eatingSpot: profile.eatingSpots) {
                        if (eatingSpot.occupiedBy == null) {
                            for (Spot tableSeat: eatingSpot.attached_table.attachedSpots) {
                                if (tableSeat.occupiedBy != null && !group.members.contains(tableSeat.occupiedBy)) {
                                    continue eatingSpotsLoop;
                                }
                            }
                            setSpot(eatingSpot);
                            break lookingForNewSpot;
                        }
                    }
                    for (int i = placeInQueue; i < profile.waitingSpots.size(); i++) {
                        Spot waitingSpot = profile.waitingSpots.get(i);
                        if (waitingSpot.occupiedBy == null) {
                            setSpot(waitingSpot);
                            placeInQueue = i;
                            break lookingForNewSpot;
                        }
                    }
                }

                controller.update(delta);
                posX = posX + controller.x * profile.walkSpeed;
                posY = posY + controller.y * profile.walkSpeed;
                facingX = controller.facingX;
                facingY = controller.facingY;
                break;

            case PICKING:
                if (progress >= profile.pickSpeed * group.level.customerPickSpeedMultiplier) {
                    setState(State.WAITING_FOR_ORDER_TO_BE_TAKEN);
                }
                break;
            case WAITING_FOR_ORDER_TO_BE_TAKEN:
                if (progress >= profile.waitForOrderPatience * group.level.customerWaitForOrderTakenMultiplier) {
                    group.angryLeave();
                }
                break;
            case ORDERING:
                if (progress >= profile.orderSpeed * group.level.customerOrderSpeedMultiplier) {
                    setState(State.WAITING_FOR_FOOD);
                    currentOrder += 1;
                }
                break;
            case WAITING_FOR_FOOD:
                System.out.println(profile.orders.get(currentOrder));
                if (progress >= profile.waitForFoodPatience * group.level.customerWaitForFoodMultiplier) {
                    group.angryLeave();
                }
                if (profile.orders.get(currentOrder) == spot.attached_table.currentIngredient) {
                    spot.attached_table.setIngredient(null);
                    setState(State.WAITING_FOR_GROUP_FOOD);
                }
                break;
            case WAITING_FOR_GROUP_FOOD:
                if (progress >= profile.waitForGroupFoodPatience * group.level.customerWaitForGroupFoodMultiplier) {
                    group.angryLeave();
                }
                if (group.everyoneHasTheFood()) {
                    setState(State.EATING);
                }
                break;
            case EATING:
                if (progress >= profile.eatSpeed * group.level.customerEatSpeedMultiplier) {
                    if (currentOrder + 1 == profile.orders.size() ) {
                        setState(State.LEAVING);
                    } else {
                        setState(State.WAITING_FOR_ORDER_TO_BE_TAKEN);
                    }
                }
                break;
            case LEAVING:
                setSpot(profile.spawnLocation);
                if (group.readyToLeave()) {
                    group.active = false;
                }
                break;
        }
    }

    public void setState(State state) {
        System.out.println(state);
        this.state = state;
        progress = 0;
    }

    public void setSpot(Spot spot) {
        this.spot.occupiedBy = null;
        spot.occupiedBy = this;
        this.spot = spot;
    }

    public void render(Batch batch) {
        batch.draw(
              profile.texture,
              posX + 4f / Config.unitHeightInPixels,
              posY,
              (float) profile.texture.getWidth() / Config.unitWidthInPixels,
              (float) profile.texture.getHeight() / Config.unitHeightInPixels
        );

        switch (state) {
            case PICKING -> {
            }
            case WAITING_FOR_ORDER_TO_BE_TAKEN -> {
                batch.draw(
                     customerWaitingToOrder,
                      posX + 15f / Config.unitHeightInPixels,
                      posY + 20f / Config.unitHeightInPixels,
                      (float) customerBlankOrder.getWidth() / Config.unitWidthInPixels,
                      (float) customerBlankOrder.getHeight() / Config.unitHeightInPixels
                );
            }
            case ORDERING -> {
                batch.draw(
                      customerOrdering,
                      posX + 15f / Config.unitHeightInPixels,
                      posY + 20f / Config.unitHeightInPixels,
                      (float) customerBlankOrder.getWidth() / Config.unitWidthInPixels,
                      (float) customerBlankOrder.getHeight() / Config.unitHeightInPixels
                );
            }
            case WAITING_FOR_FOOD -> {
                batch.draw(
                      customerBlankOrder,
                      posX + 15f / Config.unitHeightInPixels,
                      posY + 20f / Config.unitHeightInPixels,
                      (float) customerBlankOrder.getWidth() / Config.unitWidthInPixels,
                      (float) customerBlankOrder.getHeight() / Config.unitHeightInPixels
                );
                batch.draw(
                      profile.orders.get(currentOrder).texture,
                      posX + (15f + 7f) / Config.unitHeightInPixels,
                      posY + (20f + 9f) / Config.unitHeightInPixels,
                      (float) profile.orders.get(currentOrder).texture.getWidth() / Config.unitWidthInPixels,
                      (float) profile.orders.get(currentOrder).texture.getHeight() / Config.unitHeightInPixels
                );
            }
        }
    }

    public void interactWith(Player player) {
        if (state == State.WAITING_FOR_ORDER_TO_BE_TAKEN) {
            System.out.println("exciting!");
            group.takeOrders();
        }
        profile.soundNeutralInteract.play();
    }

    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("x", new JsonValue(posX));
        saveData.addChild("y", new JsonValue(posY));
        saveData.addChild("controller", controller.saveGame());
        saveData.addChild("spot", new JsonValue(spot.name));
        saveData.addChild("profile", profile.saveGame());
        saveData.addChild("current-order", new JsonValue(currentOrder));
        saveData.addChild("progress", new JsonValue(progress));
        saveData.addChild("state", new JsonValue(state.name()));
        saveData.addChild("place-in-queue", new JsonValue(placeInQueue));

        return saveData;
    }

    public static Customer loadGame(
          JsonValue customerSaveData,
          Group group,
          JsonValue jsonProfiles,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, Spot> spotHashMap,
          List<Spot> eatingSpots
    ) {
        Customer customer = new Customer(
              Profile.loadOneFromJson(
                    jsonProfiles,
                    customerSaveData.get("profile"),
                    ingredientHashMap,
                    spotHashMap,
                    eatingSpots
              ),
              group
        );

        customer.posX = customerSaveData.getFloat("x");
        customer.posY = customerSaveData.getFloat("y");
        customer.setSpot(spotHashMap.get(customerSaveData.getString("spot")));  // TODO ?
        customer.currentOrder = customerSaveData.getInt("current-order");
        customer.progress = customerSaveData.getFloat("progress");
        customer.state = State.valueOf(customerSaveData.getString("state"));
        customer.placeInQueue = customerSaveData.getInt("place-in-queue");

        return customer;
    }

}

