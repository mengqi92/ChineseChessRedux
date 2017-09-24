package self.mengqi.games.models;

/**
 * Created by Mengqi on 2017/9/23.
 * 管理规则相关
 */
public class Rules {
    private static Board board = Board.create();

    /**
     * 判断棋子 piece 是否可以移动过去
     * @param piece
     * @param destination
     * @return
     */
    public static boolean canMoveTo(Piece piece, Coordinate destination) {
        if (piece.getCoordinate().equals(destination)) return false;
        if (!Coordinate.withinWholeField(destination)) return false;
        switch (piece.getType()) {
            case Ju:
                return movableThroughLine(piece, destination);
            case Ma:
                return movableByMaStep(piece, destination);
            case Xiang:
                return movableByXiangStep(piece, destination);
            case Shi:
                return movableByShiStepAndWithinCamp(piece, destination);
            case Jiang:
                return movableByOneStepAndWithinCamp(piece, destination);
            case Pao:
                return movableThroughLine(piece, destination);
            case Zu:
                if (piece.crossedRiver()) {
                    return movableByOneStepExceptBackward(piece, destination);
                } else {
                    return movableByOneStepForward(piece, destination);
                }
        }
        return false;
    }

    public static boolean canEat(Piece piece, Coordinate destination) {
        if (piece.getCoordinate().equals(destination)) return false;
        if (!Coordinate.withinWholeField(destination)) return false;
        switch (piece.getType()) {
            case Ju:
                return movableThroughLine(piece, destination);
            case Ma:
                return movableByMaStep(piece, destination);
            case Xiang:
                return movableByXiangStep(piece, destination);
            case Shi:
                return movableByShiStepAndWithinCamp(piece, destination);
            case Jiang:
                return movableByOneStepAndWithinCamp(piece, destination);
            case Pao:
                return movableThroughLineByOnePiece(piece, destination);
            case Zu:
                if (piece.crossedRiver()) {
                    return movableByOneStepExceptBackward(piece, destination);
                } else {
                    return movableByOneStepForward(piece, destination);
                }
        }
        return false;
    }

    /**
     * 判断是否可以按士的走法在宫内斜线走
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByShiStepAndWithinCamp(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();

        if (Coordinate.isOneByOneStepAway(curCoordinate, destination)) {
            return Coordinate.withinCamp(piece.getFaction(), destination) && !board.hasPieceOn(destination);
        } else {
            return false;
        }
    }

    /**
     * 判断是否可以走一步过去（只能前进）
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByOneStepForward(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();
        return Coordinate.isOneStepAway(curCoordinate, destination)
                && Coordinate.isOneStepForward(piece.getFaction(), curCoordinate, destination);
    }

    /**
     * 判断是否可以走一步过去（不能后退）
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByOneStepExceptBackward(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();
        return Coordinate.isOneStepAway(curCoordinate, destination)
                && !Coordinate.isOneStepBackward(piece.getFaction(), curCoordinate, destination);
    }

    /**
     * 判断是否可以走一步过去，而且走之后还在宫里
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByOneStepAndWithinCamp(Piece piece, Coordinate destination) {
        if (!Coordinate.withinCamp(piece.getFaction(), destination)) {
            return false;
        }

        Coordinate curCoordinate = piece.getCoordinate();
        return Coordinate.isOneStepAway(curCoordinate, destination) && !board.hasPieceOn(destination);
    }

    /**
     * 判断是否可以飞象过去
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByXiangStep(Piece piece, Coordinate destination) {
        if(!Coordinate.withinField(piece.getFaction(), destination)) return false;

        Coordinate curCoordinate = piece.getCoordinate();
        if (Coordinate.isTwoByTwoUpLeft(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x - 1, curCoordinate.y + 1);
        } else if (Coordinate.isTwoByTwoUpRight(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x + 1, curCoordinate.y + 1);
        } else if (Coordinate.isTwoByTwoDownLeft(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x - 1, curCoordinate.y - 1);
        } else if (Coordinate.isTwoByTwoDownRight(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x + 1, curCoordinate.y - 1);
        } else {
            return false;
        }
    }

    /**
     * 判断是否可以走马步过去
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableByMaStep(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();
        if (Coordinate.isOneByTwoLeft(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x - 1, curCoordinate.y);
        } else if (Coordinate.isOneByTwoRight(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x + 1, curCoordinate.y);
        } else if (Coordinate.isTwoByOneUp(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x, curCoordinate.y + 1);
        } else if (Coordinate.isTwoByOneDown(curCoordinate, destination)) {
            return !board.hasPieceOn(curCoordinate.x, curCoordinate.y - 1);
        } else {
            return false;
        }
    }

    /**
     * 判断是否可以直线走过去
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableThroughLine(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();
        // 同在一列
        if (curCoordinate.x == destination.x) {
            return 0 == board.piecesOnTheColumnBetween(curCoordinate.x, curCoordinate.y, destination.y);
        } else if (curCoordinate.y == destination.y){  // 同在一行
            return 0 == board.piecesOnTheRowBetween(curCoordinate.y, curCoordinate.x, destination.x);
        } else {
            return false;
        }
    }

    /**
     * 判断是否可以按直线跳过一枚其他棋子走过去
     * @param piece
     * @param destination
     * @return
     */
    private static boolean movableThroughLineByOnePiece(Piece piece, Coordinate destination) {
        Coordinate curCoordinate = piece.getCoordinate();
        // 同在一列
        if (curCoordinate.x == destination.x) {
            return 1 == board.piecesOnTheColumnBetween(curCoordinate.x, curCoordinate.y, destination.y);
        } else if (curCoordinate.y == destination.y){  // 同在一行
            return 1 == board.piecesOnTheRowBetween(curCoordinate.y, curCoordinate.x, destination.x);
        } else {
            return false;
        }
    }
}
