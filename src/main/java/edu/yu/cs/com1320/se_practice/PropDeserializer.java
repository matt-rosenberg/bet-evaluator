package edu.yu.cs.com1320.se_practice;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PropDeserializer implements JsonDeserializer<Prop> {
    @Override
    public Prop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString().toLowerCase();
        switch (type) {
            case "moneyline":
                return new Moneyline();
            case "spread":
                return new Spread(obj.get("spread").getAsDouble());
            case "overunder":
                return new OverUnder(obj.get("number").getAsDouble(), obj.get("over").getAsBoolean());
            default:
                throw new JsonParseException("Unknown bet type: " + type);
        }
    }
}
