package com.kshall;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.kshall.model.Note;
import com.kshall.model.deserialize.NoteDeserializer;
import com.kshall.model.serialize.NoteSerializer;
import com.kshall.piano.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        validateArgs(args);

        String inputResourceName = args[0];
        int semitonesNumber = Integer.parseInt(args[1]);

        Gson gson = buildGson();

        try (JsonReader jsonReader = getJsonReader(inputResourceName)) {
            List<Note> input = gson.fromJson(jsonReader, new TypeToken<ArrayList<Note>>(){}.getType());

            LOGGER.debug("Input: ");
            input.forEach(note -> LOGGER.debug(note.toString()));
            List<Note> output = Keyboard.buildDefault().transpose(input, semitonesNumber);
            LOGGER.debug("Output: ");
            output.forEach(note -> LOGGER.debug(note.toString()));

            LOGGER.debug("Comparison (transposed by " + semitonesNumber + "): ");
            for (int i = 0; i < input.size(); i++) {
                Note initialNote = input.get(i);
                Note transposedNote = output.get(i);
                LOGGER.debug("initial = " + initialNote + " / " + "transposed = " + transposedNote);
            }

            gson.toJson(output, new FileWriter("./output.json"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Couldn't create JsonReader for resource: " + inputResourceName);
        }
    }

    private static void validateArgs(String[] args) {
        if (args == null || args.length < 2) {
            throw new RuntimeException("Invalid number of arguments");
        }
    }

    private static JsonReader getJsonReader(String file) throws IOException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource(file);
        Path inputPath;
        if (resource == null) {
            inputPath = Paths.get(file);
        } else {
            inputPath = Paths.get(resource.toURI());
        }

        return new JsonReader(Files.newBufferedReader(inputPath));
    }

    private static Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Note.class, new NoteDeserializer());
        builder.registerTypeAdapter(Note.class, new NoteSerializer());
        return builder.create();
    }
}