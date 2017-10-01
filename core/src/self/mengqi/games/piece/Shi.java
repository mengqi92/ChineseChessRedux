package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Mengqi on 2017/9/29.
 */
public class Shi extends AbstractPiece {

    public Shi(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Shi, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        int[][] candidates = new int[][]{
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        this.eatableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> board.hasEnemyPieceOn(this.faction, coord))
                .collect(Collectors.toSet());
    }

    @Override
    public void updateMovableArea(Board board) {
        int[][] candidates = new int[][]{
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        movableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> !board.hasPieceOn(coord))
                .collect(Collectors.toSet());
    }
}
