package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    private Point bottomLeft;
    private Point topRight;
    private TETile tile = Tileset.FLOOR;
    //KEEP THIS UPDATED
    private final int prime = 5235563;

    public Room(int x1, int y1, int x2, int y2) {
        bottomLeft = new Point(x1, y1);
        topRight = new Point(x2, y2);
    }

    public Room(int x1, int y1, int x2, int y2, TETile tile) {
        bottomLeft = new Point(x1, y1);
        topRight = new Point(x2, y2);
        this.tile = tile;
    }

    /**
     * if either room is above the other or if either room is to the right of
     * the other then they clearly don't intersect
     *
     * @param other another room
     * @return true if the given room intersects with this room
     * @source: https://www.baeldung.com/java-check-if-two-rectangles-overlap
     * looked at their implementation to make sure i was comparing the
     * correct points(ie topRight to bottomLeft or something like that)
     */
    public boolean intersects(Room other) {
        if (this.isAbove(other) || other.isAbove(this)) {
            return false;
        } else if (this.isRight(other) || other.isRight(this)) {
            return false;
        }
        return true;
    }

    public boolean isTouching(Room other) {
        if (this.touchAbove(other) || other.touchAbove(this)) {
            return false;
        } else if (this.touchRight(other) || other.touchRight(this)) {
            return false;
        }
        return true;
    }

    public boolean touchAbove(Room other) {
        return this.bottomLeft.y() > other.topRight.y() + 3;
    }

    /**
     * @param other - another room
     * @return true if the left of this room is to the right of(greater than)
     * the right of other
     */
    public boolean touchRight(Room other) {
        return this.bottomLeft.x() > other.topRight.x() + 3;
    }

    /**
     * @param pt a point in the world
     * @return true if the point is a tile contained by this room
     */
    public boolean contains(Point pt) {
        boolean betweenx =
                (pt.x() >= bottomLeft.x()) && (pt.x() <= topRight.x());
        boolean betweeny =
                (pt.y() >= bottomLeft.y()) && (pt.y() <= topRight.y());
        return betweenx && betweeny;
    }

    /**
     * @param other - another room
     * @return true if the bottom of this room is above(greater than) the top
     * of other
     */
    public boolean isAbove(Room other) {
        return this.bottomLeft.y() > other.topRight.y() + 1;
    }

    /**
     * @param other - another room
     * @return true if the left of this room is to the right of(greater than)
     * the right of other
     */
    public boolean isRight(Room other) {
        return this.bottomLeft.x() > other.topRight.x() + 1;
    }

    public Point bottomLeft() {
        return bottomLeft;
    }

    public Point topRight() {
        return topRight;
    }

    @Override
    public boolean equals(Object other) {
        Room rm = (Room) other;
        return rm.bottomLeft().equals(bottomLeft) && rm.topRight().equals(topRight);
    }

    @Override
    public int hashCode() {
        return (281 * bottomLeft.hashCode()) + (577 * topRight.hashCode()) % prime;
    }

    public int top() {
        return topRight.y();
    }

    public int bottom() {
        return bottomLeft.y();
    }

    public int left() {
        return bottomLeft.x();
    }

    public int right() {
        return topRight.x();
    }

    public TETile tile() {
        return tile;
    }
}
