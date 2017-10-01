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
                {2, -2}, {2, 2}, {-2, -2}, {-2, 2},
        };
        this.eatableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinField(faction, coord))
                .filter(coord -> board.hasEnemyPieceOn(this.faction, coord))
                .filter(coord -> !board.hasPieceOn(xiangLeg(coord)))
                .collect(Collectors.toSet());
    }

    @Override
    public void updateMovableArea(final Board board) {
        int[][] candidates = {
                {2, -2}, {2, 2}, {-2, -2}, {-2, 2},
        };
        this.movableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinField(faction, coord))
                .filter(coord -> !board.hasPieceOn(coord))
                .filter(coord -> !board.hasPieceOn(xiangLeg(coord)))
                .collect(Collectors.toSet());
    }

    /**
     * 根据当前相的位置和目标地点，计算出相腿的位置
     * @param destination
     * @return
     */
    Coordinate xiangLeg(final Coordinate destination) {
        if (Math.abs(coordinate.x - destination.x) == 2 && Math.abs(coordinate.y - destination.y) == 2) {
            return Coordinates.of((coordinate.x + destination.x) >> 1, (coordinate.y + destination.y) >> 1);
        } else{
            throw new IllegalStateException("相吃不到这个子" + destination.toString());
        }
    }
}
