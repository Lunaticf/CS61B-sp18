import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('b', 'd'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertFalse(offByOne.equalChars('a', 'B'));
    }


}
