package byow.Core;

public class Point {
    private int x;
    private int y;

    /*
    @source: http://compoasso.free.fr/primelistweb/page/prime/liste_online_en
    .php
    got prime numbers from that website
     */
    private final int prime1 = 727;
    private final int prime2 = 521;
    private final int prime3 = 5007589;

    public Point(int x1, int y1) {
        x = x1;
        y = y1;
    }

    public int x() {
        return x;
    }
    public int y() {
        return y;
    }
    public void setX(int x2) {
        x = x2;
    }
    public void setY(int y2) {
        y = y2;
    }
    @Override
    public boolean equals(Object other) {
        Point pt = (Point) other;
        return pt.x == x && pt.y == y;
    }
    @Override
    public int hashCode() {
        return ((prime1 * (x * x) % prime3) + (prime2 * (y * y * y) % prime3)) % prime3;
    }

}
