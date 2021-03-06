package self.mengqi.games.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import self.mengqi.games.common.HumanFriendly;

/**
 * Created by Mengqi on 2017/9/23.
 * 位置坐标，以像素为单位，0-index
 */
public class Position implements HumanFriendly {
    private int dimension;
    public float x;
    public float y;
    public float z;

    static final int GRIDS = 8; // how many GRIDS are on the board
    public static final int GAP = 60;  // GAP between two adjacent line
    public static final int HALF_GAP = GAP /2;

    static final int TOP = 50;
    static final int LEFT = 47;
    static final int BOTTOM = TOP + GRIDS * GAP;
    static final int RIGHT = LEFT + GRIDS * GAP;

    static final int BLACK_BOTTOM = TOP + (GRIDS /2) * GAP;  // 黑方下界
    static final int RED_TOP = BLACK_BOTTOM + GAP;           // 红方上界

    public Position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        dimension = 3;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        dimension = 2;
    }

    public Position(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
        dimension = 3;
    }

    public Position(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = 0;
        dimension = 2;
    }

    public Position(Coordinate coordinate) {
        this.x = LEFT + (coordinate.x - 1) * GAP;
        this.y = TOP + (coordinate.y - 1) * GAP;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    @Override
    public String toReadableString() {
        return dimension == 3 ?
            String.format("@(%.2f, %.2f, %.2f)", x, y, z) : String.format("@(%.2f, %.2f)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (dimension != position.dimension) return false;
        if (Float.compare(position.x, x) != 0) return false;
        if (Float.compare(position.y, y) != 0) return false;
        return Float.compare(position.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}
