package byow.Test;

import static org.junit.Assert.*;

import byow.Core.Room;
import byow.Core.UnionFindRooms;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UnionFindRoomsTest {

    @Test
    public void test1() {
        List<Room> rooms = new ArrayList<Room>();
        for (int i = 0; i < 10; i++) {
            rooms.add(new Room((int) (Math.random() * 10), (int) (Math.random() * 10),
                    (int) (Math.random() * 10), (int) (Math.random() * 10)));
        }

        UnionFindRooms test = new UnionFindRooms(rooms);

        test.union(1, 2);
        assertTrue(test.isConnected(rooms.get(1), rooms.get(2)));

        test.union(rooms.get(3), rooms.get(4));
        assertTrue(test.isConnected(3, 4));

        test.union(rooms.get(5), rooms.get(1));
        assertTrue(test.isConnected(rooms.get(2), rooms.get(5)));

        for (int i = 0; i < 9; i++) {
            test.union(i, i + 1);
        }
        assertTrue(test.allConnected());
    }
}
