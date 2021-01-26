package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90; //90
    public static final int HEIGHT = 45; //45
    private Random random;

    private static WorldGenerator generator = new WorldGenerator();
    private Point avatarloc;
    private boolean colonpress = false;
    private String tosave;
    private boolean ended;
    private boolean render = false;

    /**
     * Method used for exploring a fresh world. This method should handle all
     * inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        EngineUtils eu = new EngineUtils(WIDTH, HEIGHT);
        eu.beginGame(true, new KeyboardInput());
    }
        /*ter.initialize(WIDTH, HEIGHT);
        tosave = "";
        boolean started = false;
        long seed = 0;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ended = false;
        render = true;

        StdDraw.setPenColor(Color.red);
        StdDraw.setFont(new Font("Sans Serif", Font.BOLD, 64));
        StdDraw.text(WIDTH / 2, 25, "insert catchy title");
        StdDraw.setFont(new Font("Sans Serif", Font.BOLD, 32));
        StdDraw.text(WIDTH / 2, 18, "New Game (N)");
        StdDraw.text(WIDTH / 2, 13, "Load Game (L)");
        StdDraw.text(WIDTH / 2, 8, "Quit (Q)");
        StdDraw.show();


        //menu screen: New World, Load, Quit
        while (!started) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextinput = Character.toString(
                        Character.toUpperCase(StdDraw.nextKeyTyped()));
                if (nextinput.equals("N")) {
                    tosave += "N";
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please input seed, followed by \"S\"");
                    StdDraw.show();
                    break;
                } else if (nextinput.equals("L")) {
                    try {
                        File text = new File("save.txt");
                        Scanner scan = new Scanner(text);
                        String lastsave = scan.nextLine();
                        lastsave = lastsave.substring(0, lastsave.length() - 2);
                        world = interactWithInputString(lastsave);
                    } catch (Exception e) { }
                    started = true;
                } else if (nextinput.equals("Q")) {
                    //terminate program
                    started = true;
                    ended = true;
                }
            }
        }
        //ask for seed
        while (!started) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextinput = Character.toString(
                        Character.toUpperCase(StdDraw.nextKeyTyped()));
                if (!nextinput.equals("S")) {
                    seed  = 10 * seed + Long.parseLong(nextinput);
                } else {
                    tosave += Long.toString(seed) + "S";
                    world = generator.generateWorld(world, seed);
                    started = true;
                    random = new Random(seed);
                    StdDraw.clear(Color.black);

                    ter.initialize(WIDTH, HEIGHT);
                    world = spawn(world);
                    ter.renderFrame(world);
                }
            }
        }

        while (!ended) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextinput = Character.toString(
                        Character.toUpperCase(StdDraw.nextKeyTyped()));
                if (nextinput.equals("W") || nextinput.equals("A")
                        || nextinput.equals("S") || nextinput.equals("D")
                        || nextinput.equals(":") || nextinput.equals("Q")) {
                    world = interacthelper(nextinput, world);
                }
            }
        }*/


    /**
     * Method used for autograding and testing your code. The input string
     * will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q",
     * "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine
     * using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save
     * . For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to
     * run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        EngineUtils eu = new EngineUtils(WIDTH, HEIGHT);
        TETile[][] world = eu.beginGame(false, new StringInput(input));
        return world;
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice
        // clean interface
        // that works for many different input types.
        /*TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        long seed = 0;
        input = input.toUpperCase();
        if (input.substring(0, 1).equals("N")) {
            tosave += "N";
            int i = 1;
            while (!input.substring(i, i + 1).equals("S")) {
                seed = 10 * seed + Long.parseLong(input.substring(i, i + 1));
                i++;
            }
            tosave += input.substring(1, i + 1);
            input = input.substring(i + 1);
            finalWorldFrame = generator.generateWorld(finalWorldFrame, seed);
            random = new Random(seed);
            finalWorldFrame = spawn(finalWorldFrame);

            if (render) {
                ter.initialize(WIDTH, HEIGHT);
            }
            for (int j = 0; j < input.length(); j++) {
                finalWorldFrame = interacthelper(input.substring(j, j + 1), finalWorldFrame);
                if (render) {
                    StdDraw.pause(100);
                }
            }
        } else if (input.substring(0, 1).equals("L")) {
            try {
                File text = new File("save.txt");
                Scanner scan = new Scanner(text);
                String lastsave = scan.nextLine();
                lastsave = lastsave.substring(0, lastsave.length() - 2);
                finalWorldFrame = interactWithInputString(lastsave);
            } catch (Exception e) { };
        }

        return finalWorldFrame;*/

        /*input = input.substring(1);
        input = input.substring(0, input.length() - 1);
        WorldGenerator wg = new WorldGenerator();
        wg.generateWorld(finalWorldFrame, Long.parseLong(input));
        System.out.println(input);
        return finalWorldFrame;*/
        // (hasn't changed yet)
    }

    /*private TETile[][] interacthelper(String s, TETile[][] world) {
        if (s.equals("W")) {
            if (world[avatarloc.x][avatarloc.y + 1].equals(Tileset.FLOOR)) {
                world[avatarloc.x][avatarloc.y + 1] = Tileset.AVATAR;
                world[avatarloc.x][avatarloc.y] = Tileset.FLOOR;
                avatarloc = new Point(avatarloc.x, avatarloc.y + 1);
                tosave += "W";
            }
        } else if (s.equals("A")) {
            if (world[avatarloc.x - 1][avatarloc.y].equals(Tileset.FLOOR)) {
                world[avatarloc.x - 1][avatarloc.y] = Tileset.AVATAR;
                world[avatarloc.x][avatarloc.y] = Tileset.FLOOR;
                avatarloc = new Point(avatarloc.x - 1, avatarloc.y);
                tosave += "A";
            }
        } else if (s.equals("S")) {
            if (world[avatarloc.x][avatarloc.y - 1].equals(Tileset.FLOOR)) {
                world[avatarloc.x][avatarloc.y - 1] = Tileset.AVATAR;
                world[avatarloc.x][avatarloc.y] = Tileset.FLOOR;
                avatarloc = new Point(avatarloc.x, avatarloc.y - 1);
                tosave += "S";
            }
        } else if (s.equals("D")) {
            if (world[avatarloc.x + 1][avatarloc.y].equals(Tileset.FLOOR)) {
                world[avatarloc.x + 1][avatarloc.y] = Tileset.AVATAR;
                world[avatarloc.x][avatarloc.y] = Tileset.FLOOR;
                avatarloc = new Point(avatarloc.x + 1, avatarloc.y);
                tosave += "D";
            }
        } else if (s.equals(":")) {
            colonpress = true;
            return world;
        } else if (s.equals("Q") && colonpress) {
            tosave += ":Q";
            try {
                System.out.println(tosave);
                BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));
                writer.write(tosave);
                writer.close();
            } catch (Exception e) {
                System.out.println("idk put something here");
            }
            ended = true;
        }
        colonpress = false;
        if (render) {
            ter.renderFrame(world);
            StdDraw.pause(100);
        }
        return world;
        //w a s d :q stuffs
    }

    private TETile[][] spawn(TETile[][] world) {
        while (true) {
            avatarloc = new Point(1 + random.nextInt(WIDTH - 2), 1 + random.nextInt(HEIGHT - 2));
            if (world[avatarloc.x][avatarloc.y].equals(Tileset.FLOOR)) {
                world[avatarloc.x][avatarloc.y] = Tileset.AVATAR;
                return world;
            }
        }
    }*/
}
