package self.mengqi.games.models;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

import static self.mengqi.games.models.Coordinate.*;

/**
 * Created by Mengqi on 2017/9/24.
 * Coordinate 的工厂类
 */
public enum Coordinates {
    INSTANCE;

    static HashMap<Integer, HashMap<Integer, Coordinate>> indexedCoords = new HashMap<>();
    static LinkedList<Coordinate> coordList = new LinkedList<>();

    static {
        for (int x = LEFT; x <= RIGHT; x++) {
            HashMap<Integer, Coordinate> rowToCoord = new HashMap<>();
            for (int y = BOTTOM; y <= TOP; y++) {
                Coordinate coord = new Coordinate(x, y);
                rowToCoord.put(y, coord);
                coordList.add(coord);
            }
            indexedCoords.put(x, rowToCoord);
        }
    }

    // TODO 改写为返回 Optional<Coordinate>
    @Nullable
    public static Coordinate of(int x, int y) {
        if (null == indexedCoords.get(x) || null == indexedCoords.get(x).get(y)) {
            return null;
        }
        return indexedCoords.get(x).get(y);
    }

    public static LinkedList<Coordinate> getCoordList() {
        return coordList;
    }
}
