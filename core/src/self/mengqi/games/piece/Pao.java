package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Mengqi on 2017/9/29.
 */
public class Pao extends AbstractPiece {
    Pao(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Pao, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
        this.eatableArea = new HashSet<>();
        // TODO needs to be refactored
        for (int xi = this.coordinate.x, obstacleMet = 0; xi <= Coordinate.RIGHT && obstacleMet <= 1; xi++) {
            if (board.hasPieceOn(xi, this.coordinate.y)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasFriendPieceOn(this.faction, Coordinates.of(xi, this.coordinate.y))) {
                        break;
                    } else {
                        this.eatableArea.add(Coordinates.of(xi, this.coordinate.y));
                    }
                }
            }
        }
        for (int xi = this.coordinate.x, obstacleMet = 0; xi >= Coordinate.LEFT && obstacleMet <= 1; xi--) {
            if (board.hasPieceOn(xi, this.coordinate.y)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasFriendPieceOn(this.faction, Coordinates.of(xi, this.coordinate.y))) {
                        break;
                    } else {
                        this.eatableArea.add(Coordinates.of(xi, this.coordinate.y));
                    }
                }
            }
        }
        for (int yi = this.coordinate.y, obstacleMet = 0; yi <= Coordinate.RIGHT && obstacleMet <= 1; yi++) {
            if (board.hasPieceOn(this.coordinate.x, yi)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasFriendPieceOn(this.faction, Coordinates.of(this.coordinate.x, yi))) {
                        break;
                    } else {
                        this.eatableArea.add(Coordinates.of(this.coordinate.x, yi));
                    }
                }
            }
        }
        for (int yi = this.coordinate.y, obstacleMet = 0; yi >= Coordinate.LEFT && obstacleMet <= 1; yi--) {
            if (board.hasPieceOn(this.coordinate.x, yi)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasFriendPieceOn(this.faction, Coordinates.of(this.coordinate.x, yi))) {
                        break;
                    } else {
                        this.eatableArea.add(Coordinates.of(this.coordinate.x, yi));
                    }
                }
            }
        }
    }

    @Override
    public void updateMovableArea(Board board) {
        IntStream xRightward = IntStream.rangeClosed(coordinate.x, Coordinate.RIGHT);
        IntStream xLeftward = IntStream.rangeClosed(Coordinate.LEFT, coordinate.x);
        IntStream yUpward = IntStream.rangeClosed(coordinate.y, Coordinate.TOP);
        IntStream yDownward = IntStream.rangeClosed(Coordinate.BOTTOM, coordinate.y);

        Stream<Coordinate> xStream = IntStream.concat(xLeftward, xRightward).boxed()
                .filter(Objects::nonNull)
                .map(xi -> Coordinates.of(xi, coordinate.y));

        Stream<Coordinate> yStream = IntStream.concat(yUpward, yDownward).boxed()
                .filter(Objects::nonNull)
                .map(yi -> Coordinates.of(coordinate.x, yi));

        this.movableArea = Stream.concat(xStream, yStream)
                .filter(Coordinate::withinWholeField)
                .filter(coord -> !board.hasPieceOn(coord))
                .collect(Collectors.toSet());
    }
}
