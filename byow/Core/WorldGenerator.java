package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {
    private List<Room> roomsList;
    //stores a list of rooms that have been added
    private Random random;
    //a java.util.Random object used to generate random numbers
    private static final int LOWERBOUND = 4;
    //minimum width/height of a room
    private static final int UPPERBOUND = 7;
    //maximum width/height of a room
    private static final int MINROOMS = 10;
    private static final int MAXROOMS = 17;

    /**
     * fills the 2d grid with appropriate tiles
     */
    public TETile[][] generateWorld(TETile[][] world, long seed) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        roomsList = new ArrayList<Room>();
        random = new Random(seed);
        int numRooms = MINROOMS + random.nextInt(MAXROOMS - MINROOMS);
        drawRooms(world, numRooms);

        drawHallway(world);
        drawWalls(world);

        return world;
    }

    /**
     * adds tiles to the rectangular section of the world denoted by the points
     * FINISHED
     */
    public void setTile(TETile[][] world, Room room) {
        for (int x = room.left(); x <= room.right(); x++) {
            for (int y = room.bottom(); y <= room.top(); y++) {
                world[x][y] = room.tile();
            }
        }
    }

    public void drawWalls(TETile[][] world) {
        int[] dx = {1, 0, -1, 0, 1, 1, -1, -1};
        int[] dy = {0, 1, 0, -1, 1, -1, 1, -1};
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[x].length; y++) {
                for (int i = 0; i < dx.length; i++) {
                    if (x + dx[i] > 0 && x + dx[i] < world.length
                            && y + dy[i] > 0 && y + dy[i] < world[i].length) {
                        if (world[x + dx[i]][y + dy[i]] == Tileset.FLOOR
                                && world[x][y] == Tileset.NOTHING) {
                            world[x][y] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    /**
     * get random, check for intersections, if no overlap then add room and
     * increment counter + add to roomsList, else don't increase counter
     * draws numRooms number of nonintersecting rooms
     */
    public void drawRooms(TETile[][] world, int numRooms) {
        int n = 0;
        while (n < numRooms) {
            int xsize = LOWERBOUND + random.nextInt(UPPERBOUND - LOWERBOUND);
            int ysize = LOWERBOUND + random.nextInt(UPPERBOUND - LOWERBOUND);
            int x = 1 + random.nextInt(world.length - 2 - xsize);
            int y = 1 + random.nextInt(world[0].length - 2 - ysize);

            boolean legal = true;
            Room newRoom = new Room(x, y, x + xsize, y + ysize);

            for (Room r : roomsList) {
                if (r.isTouching(newRoom)) {
                    legal = false;
                }
            }
            /*for (int i = 0; i < xsize; i++) {
                for (int j = 0; j < ysize; j++) {
                    if (world[x + i][y + j] != Tileset.NOTHING) {
                        legal = false;
                        break;
                        // hits another room -> this room is illegal -> try
                        again
                    }
                }
                if (!legal) {
                    break;
                }
            }*/

            if (legal) {
                roomsList.add(newRoom);
                setTile(world, newRoom);
                n++;
            }
        }
    }

    /**
     * iterates through roomlist, check connect, connect
     */
    public void drawHallway(TETile[][] world) {
        UnionFindRooms uf = new UnionFindRooms(roomsList);
        int r1, r2;
        int numRooms = roomsList.size();
        while (!uf.allConnected()) {
            do {
                r1 = random.nextInt(numRooms);
                r2 = random.nextInt(numRooms);
            } while (uf.isConnected(r1, r2));
            if (drawHallwayhelper(world, r1, r2)) {
                uf.union(r1, r2);
            }
        }
        int remaining =
                random.nextInt((numRooms) * (numRooms - 1) / 5 - (numRooms - 1));
        while (remaining > 0) {
            r1 = random.nextInt(numRooms);
            r2 = random.nextInt(numRooms);
            if (drawHallwayhelper(world, r1, r2)) {
                remaining--;
            }
        }
    }

    /**
     * draws hallway between two rooms
     */
    private boolean drawHallwayhelper(TETile[][] world, int start, int target) {
        boolean successful = false;
        return searchLShapedHallways(world, start, target);
    }

    /*
    naively finds a hallway between two rooms by simply traversing all possible l shaped hallways
     */
    private boolean searchLShapedHallways(TETile[][] world, int start,
                                          int target) {
        boolean success = checkHorizontal(world, start, target);
        if (success) {
            return true;
        }
        success = checkVertical(world, start, target);
        return success;
    }

    private boolean checkVertical(TETile[][] world, int start, int target) {
        Room hallway1, hallway2;
        Room room1 = roomsList.get(start);
        Room room2 = roomsList.get(target);

        // go right then up or down
        for (int y = room1.bottom(); y <= room1.bottom(); y++) {
            int lengthx = 0;
            int lengthy = 0;
            int x = room1.right();
            lengthx = room2.left() - x;

            if (lengthx > 0) {
                hallway1 = new Room(x, y, x + lengthx, y, Tileset.FLOOR);
                if (onlyIntersects(start, target, hallway1, hallway1)) {
                    setTile(world, hallway1);
                    return true;
                }
                lengthy = room2.bottom() - y;
                if (lengthy > 0) {
                    hallway2 = new Room(x + lengthx, y, x + lengthx,
                                        y + lengthy, Tileset.FLOOR);
                } else {
                    hallway2 = new Room(x + lengthx, y - lengthy, x + lengthx, y, Tileset.FLOOR);
                }
                if (onlyIntersects(start, target, hallway1, hallway2)) {
                    setTile(world, hallway1);
                    setTile(world, hallway2);
                    return true;
                }
            }
        }

        // go left then up or down
        for (int y = room1.bottom(); y <= room1.bottom(); y++) {
            int lengthx = 0;
            int lengthy = 0;
            int x = room1.left();
            lengthx = x - room2.right();

            if (lengthx > 0) {
                hallway1 = new Room(x - lengthx, y, x, y, Tileset.FLOOR);
                if (onlyIntersects(start, target, hallway1, hallway1)) {
                    setTile(world, hallway1);
                    return true;
                }

                lengthy = room2.bottom() - y;
                if (lengthy > 0) {
                    hallway2 = new Room(x - lengthx, y, x - lengthx,
                                        y + lengthy, Tileset.FLOOR);
                } else {
                    hallway2 = new Room(x - lengthx, y - lengthy, x - lengthx, y, Tileset.FLOOR);
                }

                if (onlyIntersects(start, target, hallway1, hallway2)) {
                    setTile(world, hallway1);
                    setTile(world, hallway2);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(TETile[][] world, int start, int target) {
        Room hallway1, hallway2;
        Room room1 = roomsList.get(start);
        Room room2 = roomsList.get(target);
        for (int x = room1.left(); x <= room1.right(); x++) {
            int lengthx = 0;
            int lengthy = 0;
            int y = room1.top();
            lengthy = room2.bottom() - y;

            if (lengthy > 0) {
                hallway1 = new Room(x, y, x, y + lengthy, Tileset.FLOOR);
                if (onlyIntersects(start, target, hallway1, hallway1)) {
                    setTile(world, hallway1);
                    return true;
                }
                lengthx = room2.left() - x;
                if (lengthx > 0) {
                    hallway2 = new Room(x, y + lengthy, x + lengthx,
                                        y + lengthy, Tileset.FLOOR);
                } else {
                    hallway2 = new Room(x - lengthx, y + lengthy, x,
                                        y + lengthy, Tileset.FLOOR);
                }
                if (onlyIntersects(start, target, hallway1, hallway2)) {
                    setTile(world, hallway1);
                    setTile(world, hallway2);
                    return true;
                }
            }
        }

        // go down then left or right
        for (int x = room1.left(); x <= room1.right(); x++) {
            int lengthx = 0;
            int lengthy = 0;
            int y = room1.top();
            lengthy = y - room2.top();

            if (lengthy > 0) {
                hallway1 = new Room(x, y - lengthy, x, y, Tileset.FLOOR);
                if (onlyIntersects(start, target, hallway1, hallway1)) {
                    setTile(world, hallway1);
                    return true;
                }
                lengthx = room2.left() - x;
                if (lengthx > 0) {
                    hallway2 = new Room(x, y - lengthy, x + lengthx,
                                        y - lengthy, Tileset.FLOOR);
                } else {
                    hallway2 = new Room(x - lengthx, y - lengthy, x,
                                        y - lengthy, Tileset.FLOOR);
                }
                if (onlyIntersects(start, target, hallway1, hallway2)) {
                    setTile(world, hallway1);
                    setTile(world, hallway2);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onlyIntersects(int start, int target, Room hallway1,
                                   Room hallway2) {
        Room room1 = roomsList.get(start);
        Room room2 = roomsList.get(target);
        if (!(hallway1.intersects(room1) && hallway2.intersects(room2))) {
            return false;
        }
        for (Room r : roomsList) {
            if (!(r.equals(room1) || r.equals(room2))) {
                if (hallway1.isTouching(r) || hallway2.isTouching(r)) {
                    return false;
                }
            }
        }
        return true;
    }
}
