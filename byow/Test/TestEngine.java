package byow.Test;

import byow.Core.Engine;
import org.junit.Test;

public class TestEngine {
    Engine temp = new Engine();
    @Test
    public void testEngine() {
        temp.interactWithKeyboard();
    }

    @Test
    public void testEngine2() {
        temp.interactWithInputString("n7313251667695476404sasdw");
    }
}
