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
public class Xiang extends AbstractPiece {

    public Xiang(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Xiang, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        int[][] candidates = {
                {2, -2}, {2, 2}, {-2, 2}, {-2, -2},
                {2, 2}, {2, -2}, {-2, 2}, {-2, -2},
        };
        this.eatableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> !board.hasFriendPieceOn(this.faction, coord))
                .collect(Collectors.toSet());
    }

    @Override
    public void updateMovableArea(final Board board) {
        int[][] candidates = {
                {2, -2}, {2, 2}, {-2, 2}, {-2, -2},
                {2, 2}, {2, -2}, {-2, 2}, {-2, -2},
        };
        this.movableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> !board.hasPieceOn(coord))
                .collect(Collectors.toSet());
    }
}
