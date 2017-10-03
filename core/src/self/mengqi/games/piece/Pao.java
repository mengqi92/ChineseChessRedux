package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Coordinates;

import java.util.HashSet;

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
        for (int xi = this.coordinate.x+1, obstacleMet = 0; xi <= Coordinate.RIGHT && obstacleMet <= 1; xi++) {
            if (board.hasPieceOn(xi, this.coordinate.y)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasEnemyPieceOn(this.faction, Coordinates.of(xi, this.coordinate.y))) {
                        this.eatableArea.add(Coordinates.of(xi, this.coordinate.y));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int xi = this.coordinate.x-1, obstacleMet = 0; xi >= Coordinate.LEFT && obstacleMet <= 1; xi--) {
            if (board.hasPieceOn(xi, this.coordinate.y)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasEnemyPieceOn(this.faction, Coordinates.of(xi, this.coordinate.y))) {
                        this.eatableArea.add(Coordinates.of(xi, this.coordinate.y));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int yi = this.coordinate.y+1, obstacleMet = 0; yi <= Coordinate.TOP && obstacleMet <= 1; yi++) {
            if (board.hasPieceOn(this.coordinate.x, yi)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasEnemyPieceOn(this.faction, Coordinates.of(this.coordinate.x, yi))) {
                        this.eatableArea.add(Coordinates.of(this.coordinate.x, yi));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int yi = this.coordinate.y-1, obstacleMet = 0; yi >= Coordinate.BOTTOM && obstacleMet <= 1; yi--) {
            if (board.hasPieceOn(this.coordinate.x, yi)) {
                if (obstacleMet == 0) obstacleMet++;
                else if (obstacleMet == 1) {
                    if (board.hasEnemyPieceOn(this.faction, Coordinates.of(this.coordinate.x, yi))) {
                        this.eatableArea.add(Coordinates.of(this.coordinate.x, yi));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void updateMovableArea(Board board) {
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
