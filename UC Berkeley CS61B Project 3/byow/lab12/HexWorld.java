package byow.lab12;

import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

    public static void addHexagon(int length, TETile tile, TETile[][] world,
                                  int x, int y) {
        for (int ycoord = y, i = 0; ycoord < y + length; ycoord++, i++) {
            for (int xcoord = x - i; xcoord < x + length + i; xcoord++) {
                world[xcoord][ycoord] = tile;
            }
        }
        for (int ycoord = y + length, i = length - 1; ycoord < y + (2 * length); ycoord++, i--) {
            for (int xcoord = x - i; xcoord < x + length + i; xcoord++) {
                world[xcoord][ycoord] = tile;
            }
        }
    }

    public static void tesselate(int length, TETile tile, TETile[][] world,
                                 int x, int y) {
        int xcoord = x, ycoord = y;
        Random r = new Random(89752462);
        addHexagon(length, tile, world, xcoord, ycoord);

        ycoord += length;
        xcoord = x - (2 * length) + 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (2 * length) - 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x - (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x - (2 * length) + 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (2 * length) - 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x - (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x - (2 * length) + 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (2 * length) - 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x - (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (4 * length - 2);
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x - (2 * length) + 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);
        xcoord = x + (2 * length) - 1;
        addHexagon(length, TETile.colorVariant(tile, 200, 200, 200, r), world
                , xcoord, ycoord);

        ycoord += length;
        xcoord = x;
        addHexagon(length, tile, world, xcoord, ycoord);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        tesselate(5, Tileset.AVATAR, world, 40, 0);

        ter.renderFrame(world);
    }
}

