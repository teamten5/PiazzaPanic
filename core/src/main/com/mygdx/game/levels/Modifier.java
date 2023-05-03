package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Config;
import com.mygdx.game.actors.Profile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modifier {

    public String name;

    public Texture texture;

    public int changeChefNumber = 0; // todo
    public float chefWalkSpeedMultiplier = 1;
    public float customerWaitForSeatMultiplier = 1;
    public float customerPickSpeedMultiplier = 1;
    public float customerWaitForOrderTakenMultiplier = 1;
    public float customerOrderSpeedMultiplier = 1;
    public float customerWaitForFoodMultiplier = 1;
    public float customerWaitForGroupFoodMultiplier = 1;
    public float customerEatSpeedMultiplier = 1;

    public List<String> addsModifiers = new ArrayList<>();

    public List<Profile> newProfiles = new ArrayList<>();

    public float dayLengthMultiplier = 1;
    public int customersNumberChange = 0;
    public float customersNumberMultiplier = 1;
    public int minGroupSizeChange = 0;
    public int maxGroupSizeChange = 0;
    public float actionSpeedMultiplier = 1;  //TODO

    public int recoverReputation = 0;

    public int changeMaxReputation = 0;

    public Modifier(Texture texture, String name) {
        this.texture = texture;
        this.name = name;
    }

    public static HashMap<String, Modifier> loadFromJson(
          JsonValue jsonModifiers
    ) {
        HashMap<String, Modifier> modifierHashMap = new HashMap<>();
        for(JsonValue jsonModifier: jsonModifiers) {
            Modifier modifier = new Modifier(
                  new Texture(Gdx.files.internal("textures/" + jsonModifier.getString("texture"))),
                  jsonModifier.name
            );
            modifierHashMap.put(jsonModifier.name, modifier);
            for (JsonValue jsonModifierAttribute: jsonModifier) {
                switch (jsonModifierAttribute.name) {
                    case "chef-walk-speed-multiplier" -> modifier.chefWalkSpeedMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-wait-for-seat-multiplier" -> modifier.customerWaitForSeatMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-pickup-speed-multiplier" -> modifier.customerPickSpeedMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-wait-for-order-taken-multiplier" -> modifier.customerWaitForOrderTakenMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-order-speed-multiplier" -> modifier.customerOrderSpeedMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-wait-for-food-multiplier" -> modifier.customerWaitForFoodMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-wait-for-group-food-multiplier" -> modifier.customerWaitForGroupFoodMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-eat-speed-multiplier" -> modifier.customerEatSpeedMultiplier = jsonModifierAttribute.asFloat();
                    case "day-length-multiplier" -> modifier.dayLengthMultiplier = jsonModifierAttribute.asFloat();
                    case "customer-number-change" -> modifier.customersNumberChange = jsonModifierAttribute.asInt();
                    case "customer-number-multiplier" -> modifier.customersNumberMultiplier = jsonModifierAttribute.asFloat();
                    case "change-min-group-size" -> modifier.minGroupSizeChange = jsonModifierAttribute.asInt();
                    case "change-max-group-size" -> modifier.maxGroupSizeChange = jsonModifierAttribute.asInt();
                    case "recover-reputation" -> modifier.recoverReputation = jsonModifierAttribute.asInt();
                    case "change-max-reputation" -> modifier.changeMaxReputation = jsonModifierAttribute.asInt();
                    case "add-modifiers" -> modifier.addsModifiers = List.of(jsonModifierAttribute.asStringArray());
                }
            }
        }
        return modifierHashMap;
    }
}
