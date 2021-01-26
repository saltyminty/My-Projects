package byow.Test;

import byow.Core.Room;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    @Test
    public void basicTestRoom() {
        Room a = new Room(5, 10, 8, 14);
        Room b = new Room(4, 1, 6, 5);
        Room c = new Room(3, 3, 9, 6);
        Room d = new Room(3, 7, 12, 15);
        Room e = new Room(4, 10, 4, 11);
        Room f = new Room(50, 50, 51, 51);

        assertTrue(a.isAbove(b));
        assertFalse(b.isAbove(a));
        assertFalse(a.intersects(b));
        assertFalse(b.intersects(a));
        assertFalse(a.isRight(b));
        assertFalse(b.isRight(a));

        assertTrue(c.intersects(b));
        assertTrue(b.intersects(c));
        assertFalse(c.isAbove(b));
        assertFalse(b.isAbove(c));
        assertFalse(c.isRight(b));
        assertFalse(b.isRight(c));

        assertTrue(a.intersects(e));
        assertTrue(e.intersects(a));

        assertTrue(f.isAbove(a));
        assertTrue(f.isAbove(b));
        assertTrue(f.isAbove(c));
        assertTrue(f.isAbove(d));
        assertTrue(f.isAbove(e));

        assertFalse(a.isAbove(f));
        assertFalse(b.isAbove(f));
        assertFalse(c.isAbove(f));
        assertFalse(d.isAbove(f));
        assertFalse(e.isAbove(f));
    }
}
