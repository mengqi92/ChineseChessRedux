package self.mengqi.games.models;

import self.mengqi.games.common.HumanFriendly;

/**
 * Created by Mengqi on 2017/9/24.
 * 棋盘上的方格
 */
public class Tile implements HumanFriendly {
    private Coordinate coord;
    private TileStatus status;

    public Tile(Coordinate coord) {
        this.coord = coord;
        status = TileStatus.Idle;
    }

    public Tile(int x, int y) {
        this.coord = Coordinates.of(x, y);
        status = TileStatus.Idle;
    }

    @Override
    public String toString() {
        return String.format("Tile%s: %s", coord, status);
    }

    public TileStatus getStatus() {
        return status;
    }

    public void setStatus(TileStatus status) {
        this.status = status;
    }

    public Coordinate getCoordinate() {
        return coord;
    }

    @Override
    public String toReadableString() {
        return String.format("Tile@%s: %s", coord, status);
    }

    public enum TileStatus {
        Idle, Movable, Eatable
    }
}
