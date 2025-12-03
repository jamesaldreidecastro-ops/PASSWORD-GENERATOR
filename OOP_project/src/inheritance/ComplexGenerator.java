package inheritance;

import abstraction.AbstractPasswordGenerator;
import java.util.*;

public class ComplexGenerator extends AbstractPasswordGenerator {

    @Override
    public String generate(int length, int uppercaseCount, int numberCount, int symbolCount) {

        List<Character> passwordList = new ArrayList<>();

        // Add required uppercase letters
        for (int i = 0; i < uppercaseCount; i++) {
            passwordList.add(getRandomChar(getCharacterPool("uppercase")));
        }

        // Add required numbers
        for (int i = 0; i < numberCount; i++) {
            passwordList.add(getRandomChar(getCharacterPool("numbers")));
        }

        // Add required symbols
        for (int i = 0; i < symbolCount; i++) {
            passwordList.add(getRandomChar(getCharacterPool("symbols")));
        }

        int remaining = length - passwordList.size();

        
        // This ensures complete control over character types
        List<Character> remainingPool = getCharacterPool("lowercase");

        // Fill remaining spots with only lowercase letters
        for (int i = 0; i < remaining; i++) {
            passwordList.add(getRandomChar(remainingPool));
        }

        Collections.shuffle(passwordList);

        StringBuilder sb = new StringBuilder();
        for (char c : passwordList) sb.append(c);

        return sb.toString();
    }
}