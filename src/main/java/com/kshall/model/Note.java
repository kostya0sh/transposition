package com.kshall.model;

import java.util.Objects;

public class Note {
    private int octaveNumber;
    private int noteNumber;

    public Note(int octaveNumber, int noteNumber) {
        this.octaveNumber = octaveNumber;
        this.noteNumber = noteNumber;
    }

    public int getOctaveNumber() {
        return octaveNumber;
    }

    public void setOctaveNumber(int octaveNumber) {
        this.octaveNumber = octaveNumber;
    }

    public int getNoteNumber() {
        return noteNumber;
    }

    public void setNoteNumber(int noteNumber) {
        this.noteNumber = noteNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return octaveNumber == note.octaveNumber && noteNumber == note.noteNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(octaveNumber, noteNumber);
    }

    @Override
    public String toString() {
        return "Note{" +
                "octaveNumber=" + octaveNumber +
                ", noteNumber=" + noteNumber +
                '}';
    }
}
