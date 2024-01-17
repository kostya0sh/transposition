import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.kshall.Main;
import com.kshall.model.Note;
import com.kshall.model.deserialize.NoteDeserializer;
import com.kshall.piano.Keyboard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class KeyboardTest {

    public static JsonReader resourceJsonReader(String resourceName) throws IOException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource(resourceName);
        Path inputPath = Paths.get(resource.toURI());
        BufferedReader inputReader = Files.newBufferedReader(inputPath);
        return new JsonReader(inputReader);
    }

    public static Stream<Arguments> notesExample() throws IOException, URISyntaxException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Note.class, new NoteDeserializer()).create();
        Type noteListType = new TypeToken<ArrayList<Note>>(){}.getType();
        List<Note> input = gson.fromJson(resourceJsonReader("./input.json"), noteListType);
        List<Note> output = gson.fromJson(resourceJsonReader("./output.json"), noteListType);
        return Stream.of(Arguments.of(input, output));
    }

    @ParameterizedTest
    @MethodSource("notesExample")
    public void testExample(List<Note> inputNotes, List<Note> outputNotes) {
        List<Note> transposed = Keyboard.buildDefault().transpose(inputNotes, -3);
        assertIterableEquals(transposed, outputNotes);
    }
}
