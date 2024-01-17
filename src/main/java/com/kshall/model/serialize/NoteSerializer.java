package com.kshall.model.serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kshall.model.Note;

import java.lang.reflect.Type;

public class NoteSerializer implements JsonSerializer<Note> {

    @Override
    public JsonElement serialize(Note note, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(note.getOctaveNumber());
        jsonArray.add(note.getNoteNumber());
        return jsonArray;
    }
}
