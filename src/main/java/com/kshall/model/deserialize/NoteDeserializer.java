package com.kshall.model.deserialize;

import com.google.gson.*;
import com.kshall.model.Note;

import java.lang.reflect.Type;

public class NoteDeserializer implements JsonDeserializer<Note> {
    private static final int NOTE_SIZE = 2;

    @Override
    public Note deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.isJsonArray()) {
            throw new JsonParseException("Note element is not an array");
        }

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray.size() != NOTE_SIZE) {
            throw new JsonParseException("Invalid Note element values number " + jsonArray.size() +
                    ", expected " + NOTE_SIZE);
        }

        JsonElement octaveNumberElement = jsonArray.get(0);
        JsonElement noteNumberElement = jsonArray.get(1);

        return new Note(octaveNumberElement.getAsInt(), noteNumberElement.getAsInt());
    }
}
