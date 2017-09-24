package self.mengqi.games.models;

import java.util.HashMap;

import static self.mengqi.games.models.Coordinate.BOTTOM;
import static self.mengqi.games.models.Coordinate.LEFT;
import static self.mengqi.games.models.Coordinate.RIGHT;
import static self.mengqi.games.models.Coordinate.TOP;

/**
 * Created by Mengqi on 2017/9/24.
 * Coordinate 的工厂类
 */
public enum Coordinates {
    INSTANCE;

    static HashMap<Integer, HashMap<Integer, Coordinate>> coordinates = new HashMap<>();

    static {
        for (int x = LEFT; x <= RIGHT; x++) {
            HashMap<Integer, Coordinate> rowToCoord = new HashMap<>();
            for (int y = BOTTOM; y <= TOP; y++) {
                rowToCoord.put(y, new Coordinate(x, y));
            }
            coordinates.put(x, rowToCoord);
        }
    }

    static Coordinate of(int x, int y) {
        return coordinates.get(x).get(y);
    }
}
