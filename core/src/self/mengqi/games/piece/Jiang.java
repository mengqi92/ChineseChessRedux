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
public class Jiang extends AbstractPiece {

    Jiang(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Jiang, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        // 普通的吃法
        int[][] candidates = new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        };
        this.eatableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> board.hasEnemyPieceOn(faction, coord))
                .collect(Collectors.toSet());

        // 老将面对面也可吃
        Coordinate enemyCoord = board.getJiangCoord(faction.enemy());
        // 对面的将还没初始化
        if (null == enemyCoord)
            return;
        // 双方老将面对面，也可以吃
        if (enemyCoord.x == coordinate.x) {
            for (int yi = Math.min(enemyCoord.y, coordinate.y) + 1; yi < Math.max(enemyCoord.y, coordinate.y); yi++) {
                if (board.hasPieceOn(coordinate.x, yi)) {
                    return;
                }
            }
            this.eatableArea.add(enemyCoord);
        }
    }

    @Override
    public void updateMovableArea(Board board) {
        int[][] candidates = new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        };
        movableArea = Arrays.stream(candidates)
                .map(xy -> Coordinates.of(coordinate.x+xy[0], coordinate.y+xy[1]))
                .filter(Objects::nonNull)
                .filter(coord -> Coordinate.withinCamp(faction, coord))
                .filter(coord -> !board.hasPieceOn(coord))
                .collect(Collectors.toSet());
    }
}
