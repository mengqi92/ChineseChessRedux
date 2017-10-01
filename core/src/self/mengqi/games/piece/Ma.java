package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Mengqi on 2017/9/28.
 */
public class Ma extends AbstractPiece {

    public Ma(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Ma, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        int[][] candidates = {
                {2, -1}, {2, 1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
        };
        eatableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(Coordinate::withinWholeField)
                .filter(coord -> board.hasEnemyPieceOn(this.faction, coord))
                .filter(coord -> !board.hasPieceOn(maLeg(coord)))  // 判断是否马腿上有子
                .collect(Collectors.toSet());
    }

    @Override
    public void updateMovableArea(final Board board) {
        int[][] candidates = {
                {2, -1}, {2, 1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
        };
        movableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(Coordinate::withinWholeField)
                .filter(coord -> !board.hasPieceOn(coord))
                .filter(coord -> !board.hasPieceOn(maLeg(coord)))  // 判断是否马腿上有子
                .collect(Collectors.toSet());
    }

    /**
     * 根据当前马的位置和目标地点，计算出马腿的位置
     * @param destination
     * @return
     */
    private Coordinate maLeg(final Coordinate destination) {
        if (Math.abs(coordinate.x - destination.x) == 1 && Math.abs(coordinate.y - destination.y) == 2) {
            return Coordinates.of(coordinate.x, (coordinate.y + destination.y) >> 1);
        } else if (Math.abs(coordinate.x - destination.x) == 2 &&
                Math.abs(coordinate.y - destination.y) == 1) {
            return Coordinates.of((coordinate.x + destination.x) >> 1, coordinate.y);
        } else {
            throw new IllegalStateException("马吃不到这个子" + destination.toString());
        }
    }

}
