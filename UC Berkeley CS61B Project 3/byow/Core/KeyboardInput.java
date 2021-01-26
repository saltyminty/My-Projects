package byow.Core;

import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardInput implements InputSource {
    public KeyboardInput() {

    }

    /**
     * @source InputDemo
     * @return: uppercased character
     */
    public char getNextKey() {
        if (StdDraw.hasNextKeyTyped()) {
            char c = Character.toUpperCase(StdDraw.nextKeyTyped());
            return c;
        }
        return 0;
    }


    public boolean possibleNextInput() {
        return true;
    }
}
