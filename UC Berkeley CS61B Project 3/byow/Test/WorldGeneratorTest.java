package byow.Test;

import byow.Core.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class WorldGeneratorTest {
    private static WorldGenerator testGenerator = new WorldGenerator();

    public static void main(String[] args) throws InterruptedException {
        int height = 45, width = 90;
        for (long seed = 100000; seed < 100000000; seed++) {
            TETile[][] testworld = new TETile[width][height];
            testworld = testGenerator.generateWorld(testworld, seed);
            TERenderer ter = new TERenderer();
            ter.initialize(width, height);
            ter.renderFrame(testworld);
            Thread.sleep(1000);
        }
    }
}
