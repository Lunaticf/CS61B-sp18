import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN;

    @Test
    public void testEqualChars() {
        offByN = new OffByN(5);
        assertTrue(offByN.equalChars('a', 'f'));
        assertFalse(offByN.equalChars('h', 'f'));
    }
}
