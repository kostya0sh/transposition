package com.kshall.piano;

import com.kshall.exception.TransposeException;
import com.kshall.model.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Keyboard {

    public static Keyboard buildDefault() {
        Map<Integer, Octave> defaultMap = new HashMap<>();
        int index = 0;
        for (int i = -3; i < 6; i++) {
            Octave octave;
            if (i == -3) {
                octave = new Octave(i, 10, 12, index);
            } else if (i == 5) {
                octave = new Octave(i, 1, 1, index);
            } else {
                octave = new Octave(i, index);
            }
            index = octave.getEndIndex() + 1;
            defaultMap.put(octave.getNumber(), octave);
        }
        return new Keyboard(defaultMap);
    }

    private final Map<Integer, Octave> octaveMap;

    public Keyboard(Map<Integer, Octave> octaveMap) {
        this.octaveMap = octaveMap;
    }

    private Note transpose(Note note, int semitonesNumber, List<Octave> octaves) {
        Octave noteOctave = octaveMap.get(note.getOctaveNumber());
        if (noteOctave == null) {
            throw new TransposeException("Invalid octave number: " + note.getOctaveNumber());
        }

        int noteIndex = noteOctave.semitoneToIndex(note.getNoteNumber());
        int transposedIndex = noteIndex + semitonesNumber;
        Octave transposedOctave = octaves.stream().filter(o -> o.isValidIndex(transposedIndex)).findFirst()
                .orElseThrow(() -> new TransposeException("Couldn't transpose note: " + note));
        return new Note(transposedOctave.getNumber(), transposedOctave.indexToSemitone(transposedIndex));
    }

    public List<Note> transpose(List<Note> notes, int semitonesNumber) {
        List<Octave> octaves = octaveMap.keySet().stream().map(octaveMap::get).toList();
        List<Note> output = new ArrayList<>(notes.size());
        for (Note note : notes) {
            Note transposed = transpose(note, semitonesNumber, octaves);
            output.add(transposed);
        }
        return output;
    }
}
