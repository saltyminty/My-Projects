package byow.Core;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashMap;
import java.util.List;

public class UnionFindRooms {
    private WeightedQuickUnionUF grid;
    private HashMap<Integer, Room> indexToRoom;
    private HashMap<Room, Integer> roomToIndex;
    private int size;

    /**
    @param rooms the list of rooms we have already generated
     */
    public UnionFindRooms(List<Room> rooms) {
        grid = new WeightedQuickUnionUF(rooms.size());
        size = rooms.size();
        indexToRoom = new HashMap<Integer, Room>();
        roomToIndex = new HashMap<Room, Integer>();
        for (int i = 0; i < rooms.size(); i++) {
            indexToRoom.put(i, rooms.get(i));
            roomToIndex.put(rooms.get(i), i);
        }
    }

    /**
     * OVERLOAD: union1 pasing in indices, union2 passing in Rooms
     * primarily using union2, probably
     * @param a: Index 1
     * @param b: Index 2
     */
    public void union(int a, int b) {
        grid.union(a, b);
    }
    public void union(Room a, Room b) {
        grid.union(roomToIndex.get(a), roomToIndex.get(b));
    }

    /**
     * Overload but not really
     *      findint: find, pass index, return index
     *      find: find, pass room, return Room
     */
    public int findint(int a) {
        return grid.find(a);
    }
    public Room find(Room a) {
        return indexToRoom.get(grid.find(roomToIndex.get(a)));
    }

    /**
     * OVERLOAD: isConnected1 passing in indices, isConnected2 passing in Rooms
     */
    public boolean isConnected(int a, int b) {
        return grid.connected(a, b);
    }
    public boolean isConnected(Room a, Room b) {
        return grid.connected(roomToIndex.get(a), roomToIndex.get(b));
    }

    /**
     * @return true if all Connected, false otherwise
     */
    public boolean allConnected() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (!isConnected(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
