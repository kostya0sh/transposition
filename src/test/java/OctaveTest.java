import com.kshall.exception.OctaveException;
import com.kshall.piano.Octave;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OctaveTest {

    @Test
    public void testIndexToSemitone_success() {
        Octave octave = new Octave(1, 0);
        int semitone = octave.indexToSemitone(5);
        assertEquals(6, semitone);
    }

    @Test
    public void testIndexToSemitone_outOfRange() {
        Octave octave = new Octave(1, 0);
        assertThrows(OctaveException.class, () -> octave.indexToSemitone(12));
    }

    @Test
    public void testSemitoneToIndex_success() {
        Octave octave = new Octave(1, 0);
        int index = octave.semitoneToIndex(5);
        assertEquals(4, index);
    }

    @Test
    public void testSemitoneToIndex_outOfRange() {
        Octave octave = new Octave(1, 11);
        assertThrows(OctaveException.class, () -> octave.semitoneToIndex(13));
    }

    @Test
    public void testIsValidIndex_valid() {
        Octave octave = new Octave(1, 11);
        assertTrue(octave.isValidIndex(12));
    }

    @Test
    public void testIsValidIndex_invalid() {
        Octave octave = new Octave(1, 11);
        assertFalse(octave.isValidIndex(10));
    }

    @Test
    public void testIsValidSemitone_valid() {
        Octave octave = new Octave(1, 11);
        assertTrue(octave.isValidSemitone(12));
    }

    @Test
    public void testIsValidSemitone_invalid() {
        Octave octave = new Octave(1, 11);
        assertFalse(octave.isValidSemitone(13));
    }
}
