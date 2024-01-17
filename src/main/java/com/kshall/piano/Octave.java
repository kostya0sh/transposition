package com.kshall.piano;

import com.kshall.exception.OctaveException;

public class Octave {
    private final int number;
    private final int minSemitone;
    private final int maxSemitone;
    private final int startIndex;
    private final int endIndex;

    /**
     * Octave
     *
     * @param minSemitone min semitone index included
     * @param maxSemitone max semitone index included
     */
    public Octave(int number, int minSemitone, int maxSemitone, int startIndex) {
        this.number = number;
        this.minSemitone = minSemitone;
        this.maxSemitone = maxSemitone;
        this.startIndex = startIndex;
        this.endIndex = startIndex + maxSemitone - minSemitone;
    }

    public Octave(int number, int startIndex) {
        this(number, 1, 12, startIndex);
    }

    public int getMinSemitone() {
        return minSemitone;
    }

    public int getMaxSemitone() {
        return maxSemitone;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getNumber() {
        return number;
    }

    public boolean isValidSemitone(int semitone) {
        return semitone >= minSemitone && semitone <= maxSemitone;
    }

    public boolean isValidIndex(int index) {
        return index >= startIndex && index <= endIndex;
    }

    public int semitoneToIndex(int semitone) {
        if (!isValidSemitone(semitone)) {
            throw new OctaveException("Semitone out of range");
        }

        return startIndex + semitone - 1;
    }

    public int indexToSemitone(int index) {
        if (!isValidIndex(index)) {
            throw new OctaveException("Index out of range");
        }

        return minSemitone + index - startIndex;
    }

    @Override
    public String toString() {
        return "Octave{" +
                "number=" + number +
                ", minSemitone=" + minSemitone +
                ", maxSemitone=" + maxSemitone +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
