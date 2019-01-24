public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if(!cc.equalChars(word.charAt(i), word.charAt(j))) {
                return false;
            }
            ++i;--j;
        }
        return true;
    }


    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        if (deque.size() <= 1) {
            return true;
        }
        return isPalindromeHelper(deque);
    }

    private boolean isPalindromeHelper(Deque<Character> deque) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        }
        return (deque.removeFirst().equals(deque.removeLast())) && isPalindromeHelper(deque);
    }

}
