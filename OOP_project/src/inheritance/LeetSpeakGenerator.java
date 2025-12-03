package inheritance;

import abstraction.AbstractPasswordGenerator;
import java.util.*;

public class LeetSpeakGenerator extends AbstractPasswordGenerator {

    private static final Map<Character, Character> LEET_MAP = new HashMap<>();

    static {
        // Common leet speak substitutions
        LEET_MAP.put('a', '@');
        LEET_MAP.put('A', '@');
        LEET_MAP.put('e', '3');
        LEET_MAP.put('E', '3');
        LEET_MAP.put('i', '1');
        LEET_MAP.put('I', '1');
        LEET_MAP.put('o', '0');
        LEET_MAP.put('O', '0');
        LEET_MAP.put('s', '$');
        LEET_MAP.put('S', '$');
        LEET_MAP.put('t', '7');
        LEET_MAP.put('T', '7');
        LEET_MAP.put('l', '1');
        LEET_MAP.put('L', '1');
        LEET_MAP.put('g', '9');
        LEET_MAP.put('G', '9');
        LEET_MAP.put('b', '8');
        LEET_MAP.put('B', '8');
    }

    @Override
    public String generate(int length, int uppercaseCount, int numberCount, int symbolCount) {
        List<Character> passwordList = new ArrayList<>();

        // Start with a base of letters
        int baseLength = length - symbolCount;

        // Generate base password with letters
        for (int i = 0; i < baseLength; i++) {
            if (i < uppercaseCount) {
                passwordList.add(getRandomChar(getCharacterPool("uppercase")));
            } else {
                passwordList.add(getRandomChar(getCharacterPool("lowercase")));
            }
        }

        // Convert some letters to leet speak (this naturally adds numbers)
        for (int i = 0; i < passwordList.size() && numberCount > 0; i++) {
            char c = passwordList.get(i);
            if (LEET_MAP.containsKey(c)) {
                passwordList.set(i, LEET_MAP.get(c));
                numberCount--;
            }
        }

        // Add remaining symbols if needed
        for (int i = 0; i < symbolCount; i++) {
            passwordList.add(getRandomChar(getCharacterPool("symbols")));
        }

        // Shuffle to randomize positions
        Collections.shuffle(passwordList);

        // Build final password
        StringBuilder sb = new StringBuilder();
        for (char c : passwordList) {
            sb.append(c);
        }

        return sb.toString();
    }
}