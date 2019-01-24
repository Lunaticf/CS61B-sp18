import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String s = "";
        String s1 = "a";
        String s2 = "abc";
        String s3 = "abba";
        String s4 = "aaaaabaaaaa";

        assertTrue(palindrome.isPalindrome(s));
        assertTrue(palindrome.isPalindrome(s1));
        assertFalse(palindrome.isPalindrome(s2));
        assertTrue(palindrome.isPalindrome(s3));
        assertTrue(palindrome.isPalindrome(s4));
    }

    @Test
    public void testIsPalindrome2() {

        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("abba", offByOne));


    }
}
