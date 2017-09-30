package self.mengqi.games.piece;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Coordinate;

/**
 * Created by Mengqi on 2017/9/29.
 * Factory of pieces
 */
public enum Pieces {
    INSTANCE;

    public static Piece of(PieceEnums.Type type, PieceEnums.Faction faction, Coordinate coord) {
        switch (type) {
            case Ju:
                return new Ju(faction, coord);
            case Ma:
                return new Ma(faction, coord);
            case Xiang:
                return new Xiang(faction, coord);
            case Shi:
                return new Shi(faction, coord);
            case Jiang:
                return new Jiang(faction, coord);
            case Pao:
                return new Pao(faction, coord);
            case Zu:
                return new Zu(faction, coord);
        }
        return null;
    }
}
