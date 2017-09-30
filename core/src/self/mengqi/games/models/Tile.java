package self.mengqi.games.models;

/**
 * Created by Mengqi on 2017/9/24.
 * 棋盘上的方格
 */
public class Tile {
    private Coordinate coord;
    private TileStatus status;

    public Tile(Coordinate coord) {
        this.coord = coord;
        status = TileStatus.Idle;
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

    public enum TileStatus {
        Idle, Movable, Eatable
    }
}