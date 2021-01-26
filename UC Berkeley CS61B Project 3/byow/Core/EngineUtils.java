package byow.Core;

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdDraw;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.Random;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class EngineUtils {

    private TERenderer ter;
    private int WIDTH;
    private int HEIGHT;
    private Point loc;
    private String toSave;
    private HashMap<Character, Integer> dx;
    private HashMap<Character, Integer> dy;
    private WorldGenerator wg;
    private Random random;
    private boolean colonpressed = false;

    public EngineUtils(int w, int h) {
        dx = new HashMap<>();
        dy = new HashMap<>();
        toSave = "";
        loc = new Point(0, 0);
        dx.put('W', 0);
        dy.put('W', 1);
        dx.put('A', -1);
        dy.put('A', 0);
        dx.put('S', 0);
        dy.put('S', -1);
        dx.put('D', 1);
        dy.put('D', 0);
        WIDTH = w;
        HEIGHT = h;
        wg = new WorldGenerator();
    }

    private void drawMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.red);
        StdDraw.setFont(new Font("Sans Serif", Font.BOLD, 64));
        StdDraw.text(WIDTH / 2, 25, "Built Our Own World");
        StdDraw.setFont(new Font("Sans Serif", Font.BOLD, 32));
        StdDraw.text(WIDTH / 2, 18, "New Game (N)");
        StdDraw.text(WIDTH / 2, 13, "Load Game (L)");
        StdDraw.text(WIDTH / 2, 8, "Quit (Q)");
        StdDraw.show();
        resetRenderer();
    }

    private void drawInput(String soFar) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.red);
        StdDraw.setFont(new Font("Sans Serif", Font.BOLD, 32));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please input seed, followed by \"S\"");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 5, soFar);
        StdDraw.show();
        resetRenderer();
    }

    private void resetRenderer() {
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
    }

    private TETile[][] spawn(TETile[][] world) {
        while (true) {
            loc = new Point(1 + random.nextInt(WIDTH - 2), 1 + random.nextInt(HEIGHT - 2));
            if (world[loc.x()][loc.y()].equals(Tileset.FLOOR)) {
                world[loc.x()][loc.y()] = Tileset.AVATAR;
                return world;
            }
        }
    }

    public TETile[][] beginGame(boolean simulate, InputSource in) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        if (simulate) {
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT + 3);
            drawMenu();
        }
        char nextinput = in.getNextKey();
        while (nextinput == 0) {
            nextinput = in.getNextKey();
        }
        if (nextinput == 'N') {
            processSeed(nextinput, simulate, in, world);
        } else if (nextinput == 'L') {
            world = loadGame(simulate, world, in);
        } else if (nextinput == 'Q') {
            world = null;
        }
        if (world != null) {
            gameLoop(simulate, world, in);
        }
        return world;
    }

    private void processSeed(char nextinput, boolean simulate, InputSource in, TETile[][] world) {
        while (nextinput != 'S') {
            toSave += Character.toString(nextinput);
            if (simulate) {
                drawInput(toSave.substring(1));
            }
            nextinput = in.getNextKey();
            while (nextinput == 0) {
                nextinput = in.getNextKey();
            }
        }
        toSave += Character.toString(nextinput);
        long seed = Long.parseLong(toSave.substring(1, toSave.length() - 1));
        random = new Random(seed);
        wg.generateWorld(world, seed);
        spawn(world);
        if (simulate) {
            ter.renderFrame(world);
        }
    }

    /*
    @source: https://www.javatpoint.com/java-get-current-date
    for getting and formatting the date and time
     */
    private void gameLoop(boolean simulate, TETile[][] world, InputSource in) {
        boolean updateWorld = true;
        String oldType = "";
        String oldTime = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now;
        while (in.possibleNextInput()) {
            char curr = in.getNextKey();
            if (curr != 0) {
                updateWorld = true;
                toSave += curr;
                boolean ended = interact(world, curr, simulate, false);
                if (simulate) {
                    ter.renderFrame(world);
                }
                if (ended) {
                    return;
                }
            }
            if (simulate) {
                int mx = (int) StdDraw.mouseX();
                int my = (int) StdDraw.mouseY();
                String tiletype = gettype(world, mx, my);
                now = LocalDateTime.now();
                String currTime = dtf.format(now);
                if (!(tiletype.equals(oldType)) || !(oldTime.equals(currTime))) {
                    updateWorld = true;
                    oldType = tiletype;
                    oldTime = currTime;
                }
                if (updateWorld) {
                    ter.renderFrame(world);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.text(5, HEIGHT + 2, tiletype);
                    StdDraw.text(WIDTH - 10, HEIGHT + 2, dtf.format(now));
                    StdDraw.show();
                    updateWorld = false;
                }
            }
        }
    }

    private String gettype(TETile[][] world, int x, int y) {
        if (y >= HEIGHT) {
            return "nothing";
        }
        if (world[x][y] == Tileset.FLOOR) {
            return "floor";
        }
        if (world[x][y] == Tileset.AVATAR) {
            return "avatar";
        }
        if (world[x][y] == Tileset.WALL) {
            return "wall";
        }
        if (world[x][y] == Tileset.NOTHING) {
            return "nothing";
        }
        return "not sure";
    }

    private TETile[][] loadGame(boolean simulate, TETile[][] world, InputSource in) {
        File text = new File("save.txt");
        In scan = new In(text);
        String lastsave = scan.readLine();
        InputSource seedProcessor = new StringInput(lastsave);
        char nextInput = seedProcessor.getNextKey();
        processSeed(nextInput, false, seedProcessor, world);
        if (simulate) {
            ter.renderFrame(world);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(WIDTH / 2, HEIGHT + 2,
                    "You are replaying the moves from your last saved session");
            StdDraw.text(WIDTH / 2, HEIGHT + 1,
                    "Press SPACEBAR to play the next move, or K to skip to where you ended");
            StdDraw.show();
            resetRenderer();
        }

        while (seedProcessor.possibleNextInput()) {
            nextInput = seedProcessor.getNextKey();
            toSave += nextInput;
            interact(world, nextInput, simulate, simulate);
            if (simulate) {
                char keyboardinput = in.getNextKey();
                while (keyboardinput != ' ' && keyboardinput != 'K') {
                    keyboardinput = in.getNextKey();
                }
                if (keyboardinput == ' ') {
                    ter.renderFrame(world);
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.text(WIDTH / 2, HEIGHT + 2,
                            "You are replaying the moves from your last saved session");
                    StdDraw.text(WIDTH / 2, HEIGHT + 1,
                            "Press SPACEBAR to play the next move,"
                                    + " or K to skip to where you ended");
                    StdDraw.show();
                    resetRenderer();
                } else if (keyboardinput == 'K') {
                    simulate = false;
                }
            }
        }
        return world;
    }

    private void saveGame() {
        Out writer = new Out("save.txt");
        writer.print(toSave.substring(0, toSave.length() - 2));
        writer.close();
    }

    private boolean interact(TETile[][] world, char curr, boolean simulate, boolean pause) {
        if (curr == ':') {
            colonpressed = true;
        } else if (curr == 'Q' && colonpressed) {
            saveGame();
            return true;
        } else if (curr == 'W' || curr == 'A' || curr == 'S' || curr == 'D') {
            if (inbounds(world, curr)) {
                int ddx = dx.get(curr);
                int ddy = dy.get(curr);
                int x = loc.x();
                int y = loc.y();
                world[x][y] = Tileset.FLOOR;
                world[x + ddx][y + ddy] = Tileset.AVATAR;
                loc.setX(x + ddx);
                loc.setY(y + ddy);
            }
            colonpressed = false;
        }
        return false;
    }

    private boolean inbounds(TETile[][] world, char curr) {
        int ddx = dx.get(curr);
        int ddy = dy.get(curr);
        int x = loc.x();
        int y = loc.y();
        if (x + ddx < 0 || x + ddx >= WIDTH) {
            return false;
        }
        if (y + ddy < 0 || y + ddy >= HEIGHT) {
            return false;
        }
        return (world[x + ddx][y + ddy] == Tileset.FLOOR);
    }

}
