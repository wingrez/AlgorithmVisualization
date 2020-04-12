import java.awt.*;

public class Circle {

    private int x, y;
    private int r;

    public Circle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getR() {
        return r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean contain(Point p) {
        return Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) <= r * r;
    }
}