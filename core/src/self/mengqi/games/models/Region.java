package self.mengqi.games.models;

/**
 * Created by Mengqi on 2017/9/24.
 * 表示一个坐标区域
 */
public class Region {
    private int left;
    private int top;
    private int right;
    private int bottom;

    Region(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean within(Coordinate coordinate) {
        return (coordinate.x >= left && coordinate.x <= right && coordinate.y >= bottom && coordinate.y <= top);
    }
}
