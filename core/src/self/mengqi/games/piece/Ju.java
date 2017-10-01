package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.HashSet;

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
        this.eatableArea = new HashSet<>();

        for (int xi = coordinate.x+1; xi <= Coordinate.RIGHT; xi++) {
            Coordinate coord = Coordinates.of(xi, coordinate.y);
            if (board.hasPieceOn(coord)) {
                if (board.hasEnemyPieceOn(faction, coord)) {
                    this.eatableArea.add(coord);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int xi = coordinate.x-1; xi >= Coordinate.LEFT; xi--) {
            Coordinate coord = Coordinates.of(xi, coordinate.y);
            if (board.hasPieceOn(coord)) {
                if (board.hasEnemyPieceOn(faction, coord)) {
                    this.eatableArea.add(coord);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int yi = coordinate.y+1; yi <= Coordinate.TOP; yi++) {
            Coordinate coord = Coordinates.of(this.coordinate.x, yi);
            if (board.hasPieceOn(coord)) {
                if (board.hasEnemyPieceOn(faction, coord)) {
                    this.eatableArea.add(coord);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int yi = coordinate.y-1; yi >= Coordinate.BOTTOM; yi--) {
            Coordinate coord = Coordinates.of(this.coordinate.x, yi);
            if (board.hasPieceOn(coord)) {
                if (board.hasEnemyPieceOn(faction, coord)) {
                    this.eatableArea.add(coord);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void updateMovableArea(final Board board) {
        this.movableArea = new HashSet<>();

        for (int xi = coordinate.x+1; xi <= Coordinate.RIGHT; xi++) {
            Coordinate coord = Coordinates.of(xi, coordinate.y);
            if (board.hasPieceOn(coord)) {
                break;
            } else {
                this.movableArea.add(coord);
            }
        }

        for (int xi = coordinate.x-1; xi >= Coordinate.LEFT; xi--) {
            Coordinate coord = Coordinates.of(xi, coordinate.y);
            if (board.hasPieceOn(coord)) {
                break;
            } else {
                this.movableArea.add(coord);
            }
        }

        for (int yi = coordinate.y+1; yi <= Coordinate.TOP; yi++) {
            Coordinate coord = Coordinates.of(this.coordinate.x, yi);
            if (board.hasPieceOn(coord)) {
                break;
            } else {
                this.movableArea.add(coord);
            }
        }

        for (int yi = coordinate.y-1; yi >= Coordinate.BOTTOM; yi--) {
            Coordinate coord = Coordinates.of(this.coordinate.x, yi);
            if (board.hasPieceOn(coord)) {
                break;
            } else {
                this.movableArea.add(coord);
            }
        }
    }
}
