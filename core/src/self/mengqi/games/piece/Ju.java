package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Mengqi on 2017/9/27.
 */
public class Ju extends AbstractPiece {
    private boolean moved = false;

    public Ju(PieceEnums.Faction faction, Coordinate coordinate) {
        super(PieceEnums.Type.Ju, faction, coordinate);
    }

    @Override
    public void updateEatableArea(Board board) {
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

        this.eatableArea = Stream.concat(xStream, yStream)
                .filter(Coordinate::withinWholeField)
                .filter(coord -> !board.hasFriendPieceOn(this.faction, coord))
                .collect(Collectors.toSet());
    }

    @Override
    public void updateMovableArea(final Board board) {
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

        movableArea = Stream.concat(xStream, yStream)
                .filter(Coordinate::withinWholeField)
                .filter(coord -> !board.hasPieceOn(coord))
                .collect(Collectors.toSet());
    }
}
