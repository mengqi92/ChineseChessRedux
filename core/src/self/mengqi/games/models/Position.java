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
    static final int GAP = 60;  // GAP between two adjacent line
    static final int HALF_GAP = GAP /2;

    static final int TOP = 50;
    static final int LEFT = 47;
    static final int BOTTOM = TOP + GRIDS * GAP;
    public static final int RIGHT = LEFT + GRIDS * GAP;

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
            String.format("@(%f.2, %f.2, %f.2)", x, y, z) : String.format("@(%f.2, %f.2)", x, y);
        // FIXME .2 precision format not working
    }
}
