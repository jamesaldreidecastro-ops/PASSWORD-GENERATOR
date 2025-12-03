package inheritance;

import abstraction.AbstractPasswordGenerator;

public class PhraseGenerator extends AbstractPasswordGenerator {

    private static final String[] WORD_POOL = {
        "apple", "banana", "cherry", "dragon", "eagle", "forest",
        "garden", "hammer", "island", "jungle", "knight", "lemon",
        "mountain", "ninja", "ocean", "pirate", "queen", "river",
        "storm", "tiger", "unicorn", "valley", "wizard", "yellow",
        "zebra", "arctic", "blaze", "crystal", "diamond", "emerald",
        "falcon", "galaxy", "horizon", "iron", "jasper", "karma",
        "lunar", "mystic", "nebula", "oracle", "phoenix", "quantum",
        "raven", "shadow", "thunder", "urban", "vortex", "winter"
    };

    @Override
    public String generate(int length, int uppercaseCount, int numberCount, int symbolCount) {
        StringBuilder password = new StringBuilder();

        // Calculate how many words we can fit
        int wordCount = Math.max(2, length / 6); // Estimate 6 chars per word & separator

        // Add words with separators
        for (int i = 0; i < wordCount; i++) {
            String word = getRandomWord();

            // Capitalize first letter if uppercase is needed
            if (uppercaseCount > 0) {
                word = capitalizeFirst(word);
                uppercaseCount--;
            }

            password.append(word);

            // Add separator (number or symbol) between words
            if (i < wordCount - 1) {
                if (numberCount > 0) {
                    password.append(getRandomChar(getCharacterPool("numbers")));
                    numberCount--;
                } else if (symbolCount > 0) {
                    password.append(getRandomChar(getCharacterPool("symbols")));
                    symbolCount--;
                }
            }
        }

        // Add remaining numbers at the end
        while (numberCount > 0) {
            password.append(getRandomChar(getCharacterPool("numbers")));
            numberCount--;
        }

        // Add remaining symbols at the end
        while (symbolCount > 0) {
            password.append(getRandomChar(getCharacterPool("symbols")));
            symbolCount--;
        }

        // Trim or pad to exact length
        String result = password.toString();
        if (result.length() > length) {
            return result.substring(0, length);
        } else if (result.length() < length) {
            // Pad with lowercase letters
            while (result.length() < length) {
                result += getRandomChar(getCharacterPool("lowercase"));
            }
        }

        return result;
    }

    private String getRandomWord() {
    return WORD_POOL[(int) (Math.random() * WORD_POOL.length)];
    }

    private String capitalizeFirst(String word) {
        if (word == null || word.isEmpty())
            return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}