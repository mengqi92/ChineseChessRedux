package self.mengqi.games.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import static self.mengqi.games.models.Coordinate.*;

/**
 * Created by Mengqi on 2017/9/24.
 * Factory of tiles
 */
public enum Tiles {
    INSTANCE;

    static Map<Integer, Map<Integer, Tile>> indexedTiles = new HashMap<>();
    static LinkedList<Tile> tileList = new LinkedList<>();

    static {
        for (int x = LEFT; x <= RIGHT; x++) {
            Map<Integer, Tile> rowToTile = new HashMap<>();
            for (int y = BOTTOM; y <= TOP; y++) {
                Tile tile = new Tile(x, y);
                rowToTile.put(y, tile);
                tileList.add(tile);
            }
            indexedTiles.put(x, rowToTile);
        }
    }

    public static Optional<Tile> of(int x, int y) {
        if (null == indexedTiles.get(x) || null == indexedTiles.get(x).get(y)) {
            return Optional.empty();
        }
        return Optional.of(indexedTiles.get(x).get(y));
    }

    public static Optional<Tile> of(Coordinate coordinate) {
        return of(coordinate.x, coordinate.y);
    }
}
