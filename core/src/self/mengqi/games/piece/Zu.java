package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static self.mengqi.games.enums.PieceEnums.Faction.Black;

/**
 * Created by Mengqi on 2017/9/27.
 */
public class Zu extends AbstractPiece {
    public Zu(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Zu, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        if (!crossedRiver()) {
            this.eatableArea = Stream.of(Coordinate.forward(faction, coordinate, 1))
                    .filter(Objects::nonNull)
                    .filter(Coordinate::withinWholeField)
                    .filter(coord -> board.hasEnemyPieceOn(this.faction, coord))
                    .collect(Collectors.toSet());
        } else {
            this.eatableArea = Stream.of(Coordinate.forward(faction, coordinate, 1),
                    Coordinates.of(coordinate.x-1, coordinate.y),
                    Coordinates.of(coordinate.x+1, coordinate.y))
                    .filter(Objects::nonNull)
                    .filter(Coordinate::withinWholeField)
                    .filter(coord -> !board.hasEnemyPieceOn(this.faction, coord))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public void updateMovableArea(final Board board) {
        if (!crossedRiver()) {
            this.movableArea = Stream.of(Coordinate.forward(faction, coordinate, 1))
                    .filter(Objects::nonNull)
                    .filter(Coordinate::withinWholeField)
                    .filter(coord -> !board.hasPieceOn(coord))
                    .collect(Collectors.toSet());
        } else {
            this.movableArea = Stream.of(Coordinate.forward(faction, coordinate, 1),
                            Coordinates.of(coordinate.x-1, coordinate.y),
                            Coordinates.of(coordinate.x+1, coordinate.y))
                    .filter(Objects::nonNull)
                    .filter(Coordinate::withinWholeField)
                    .filter(coord -> !board.hasPieceOn(coord))
                    .collect(Collectors.toSet());
        }
    }

    /**
     * whether if the piece has crossed river
     * @return true if the piece is on the other side
     */
    public boolean crossedRiver() {
        if (faction == Black) {
            return Coordinate.redField.within(coordinate);
        } else {
            return Coordinate.blackField.within(coordinate);
        }
    }
}
