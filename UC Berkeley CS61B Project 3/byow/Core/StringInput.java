package byow.Core;

import byow.InputDemo.InputSource;
/**
 * Created by hug.
 */
public class StringInput implements InputSource {
    private String input;
    private int index;

    public StringInput(String s) {
        index = 0;
        input = s.toUpperCase();
    }

    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    public boolean possibleNextInput() {
        return index < input.length();
    }
}
