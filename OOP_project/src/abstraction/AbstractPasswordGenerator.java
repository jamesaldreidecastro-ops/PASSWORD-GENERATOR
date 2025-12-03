package abstraction;

import java.util.*;

public abstract class AbstractPasswordGenerator {

    public abstract String generate(int length, int uppercaseCount, int numberCount, int symbolCount);

    protected List<Character> getCharacterPool(String type) {
        return switch (type.toLowerCase()) {
            case "lowercase" -> List.of('a','b','c','d','e','f','g','h','i','j','k','l','m',
                                       'n','o','p','q','r','s','t','u','v','w','x','y','z');
            case "uppercase" -> List.of('A','B','C','D','E','F','G','H','I','J','K','L','M',
                                       'N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
            case "numbers" -> List.of('0','1','2','3','4','5','6','7','8','9');
            case "symbols" -> List.of('!','@','#','$','%','^','&','*','(',')','-','_','=','+');
            default -> throw new IllegalArgumentException("Invalid character type: " + type);
        };
    }

    protected char getRandomChar(List<Character> pool) {
        return pool.get((int) (Math.random() * pool.size()));
    }
}