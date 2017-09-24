package self.mengqi.games.models;

import self.mengqi.games.utils.LogUtils;

import java.util.*;

import static self.mengqi.games.models.Piece.Faction.Black;
import static self.mengqi.games.models.Piece.Faction.Red;
import static self.mengqi.games.models.Piece.Type.*;

/**
 * Created by Mengqi on 2017/9/16.
 * 棋盘类，管理棋盘上各棋子的情况
 * 单例
 */
public class Board {
    private static Board board;
    private static HashMap<Piece.Type, List<Coordinate>> initialPieceCoord = new HashMap<>();

    static {
        initialPieceCoord.put(Zu, Arrays.asList(
                Coordinates.of(1, 4),
                Coordinates.of(3, 4),
                Coordinates.of(5, 4),
                Coordinates.of(7, 4),
                Coordinates.of(9, 4)
        ));
        initialPieceCoord.put(Pao, Arrays.asList(Coordinates.of(2, 3), Coordinates.of(8, 3)));

        initialPieceCoord.put(Ju, Arrays.asList(Coordinates.of(1, 1), Coordinates.of(9, 1)));
        initialPieceCoord.put(Ma, Arrays.asList(Coordinates.of(2, 1), Coordinates.of(8, 1)));
        initialPieceCoord.put(Xiang, Arrays.asList(Coordinates.of(3, 1), Coordinates.of(7, 1)));
        initialPieceCoord.put(Shi, Arrays.asList(Coordinates.of(4, 1), Coordinates.of(6, 1)));
        initialPieceCoord.put(Jiang, Arrays.asList(Coordinates.of(5, 1)));
    }

    private List<Piece> pieces = new ArrayList<>();
    // 指向当前棋盘上已激活的棋子
    private Piece activatedPiece;
    // 记录 坐标->棋子 的映射关系，需要在棋子走动时更新
    private HashMap<Coordinate, Piece> coordToPiece = new HashMap<>();

    private Board() {
    }

    public static Board create() {
        if (null != board) {
            return board;
        } else {
            board = new Board();
            board.initPieces();
        }
        return board;
    }

    private void initPieces() {
        for (Map.Entry<Piece.Type, List<Coordinate>> entrySet : initialPieceCoord.entrySet()) {
            for (Coordinate coordinate : entrySet.getValue()) {
                Piece redPiece = new Piece(entrySet.getKey(), Red, coordinate);
                pieces.add(redPiece);
                coordToPiece.put(redPiece.getCoordinate(), redPiece);

                Piece blackPiece = new Piece(entrySet.getKey(), Black, coordinate.horizMirroredCoord());
                pieces.add(blackPiece);
                coordToPiece.put(blackPiece.getCoordinate(), blackPiece);
            }
        }
    }

    /**
     * make decision by checking status of activated piece and target coordinate
     *
     * @param targetCoord
     */
    public void makeDecision(Coordinate targetCoord) {
        Piece targetPiece = coordToPiece.get(targetCoord);
        // 点击的位置有棋子
        if (null != targetPiece) {
            // 当前棋盘已有棋子被激活
            if (this.hasPieceActivated()) {
                this.takeAction(targetPiece);
            } else {  // 当前棋盘没有子激活
                this.toggleActivatePiece(targetPiece);
            }
        } else {  // 点击的位置没有棋子
            if (this.hasPieceActivated()) {
                if (Coordinate.wholeField.within(targetCoord)) {
                    this.tryToMove(targetCoord);
                }
            }
        }
    }

    /**
     * order the activated piece to take takeAction on another piece
     *
     * @param targetPiece
     */
    public void takeAction(Piece targetPiece) {
        // 如果目标棋子是自己，那么就切换激活状态
        if (targetPiece.equals(activatedPiece)) {
            toggleActivatePiece(targetPiece);
        } else {  // 否则判断是否是己方棋子
            if (activatedPiece.sameSide(targetPiece)) {
                toggleActivatePiece(targetPiece);
            } else {
                tryToEat(targetPiece);
            }
        }
    }

    /**
     * try to eat another piece
     *
     * @param targetPiece the target piece on another faction
     */
    private void tryToEat(Piece targetPiece) {
        Coordinate destination = targetPiece.getCoordinate();

        if (Rules.canEat(activatedPiece, destination)) {
            LogUtils.debugging("board", activatedPiece, "can eat: ", targetPiece);

            letItDie(targetPiece);
            letItMove(activatedPiece, destination);
        } else {
            LogUtils.debugging("board", activatedPiece, "cannot eat to: ", targetPiece);
        }
    }

    /**
     * tryToMove the activated piece to destination
     *
     * @param destination
     */
    public void tryToMove(Coordinate destination) {
        if (Rules.canMoveTo(activatedPiece, destination)) {
            LogUtils.debugging("board", activatedPiece, "can move to: ", destination);

            letItMove(activatedPiece, destination);
        } else {
            LogUtils.debugging("board", activatedPiece, "cannot move to: ", destination);
        }
    }

    public void toggleActivatePiece(Piece targetPiece) {
        if (activatedPiece != null && activatedPiece != targetPiece) {
            activatedPiece.toggleActivated();
        }
        if (targetPiece.getStatus() == Piece.Status.Activated) {
            targetPiece.toggleActivated();
            activatedPiece = null;
        } else if (targetPiece.getStatus() == Piece.Status.Idle) {
            targetPiece.toggleActivated();
            activatedPiece = targetPiece;
        }
    }

    public boolean hasPieceActivated() {
        return activatedPiece != null;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public boolean hasPieceOn(int x, int y) {
        return coordToPiece.get(Coordinates.of(x, y)) != null;
    }

    public boolean hasPieceOn(Coordinate destination) {
        return hasPieceOn(destination.x, destination.y);
    }

    /**
     * if there are pieces on the board column x, and y is between from and to, then return true
     *
     * @param x    column of the board (1-indexed)
     * @param from
     * @param to
     * @return
     */
    public int piecesOnTheColumnBetween(int x, int from, int to) {
        if (from == to) return 0;

        int smaller = Math.min(from, to);
        int bigger = smaller == to ? from : to;

        int pieceCount = 0;
        for (int yi = smaller + 1; yi < bigger; yi++) {
            if (board.hasPieceOn(x, yi)) pieceCount++;
        }

        return pieceCount;
    }

    /**
     * if there are pieces on the board row y, and x is between from and to, then return true
     *
     * @param y    row of the board (1-indexed)
     * @param from
     * @param to
     * @return number of pieces between from and to
     */
    public int piecesOnTheRowBetween(int y, int from, int to) {
        if (from == to) return 0;

        int smaller = Math.min(from, to);
        int bigger = smaller == to ? from : to;

        int pieceCount = 0;
        for (int xi = smaller + 1; xi < bigger; xi++) {
            if (board.hasPieceOn(xi, y)) pieceCount++;
        }
        return pieceCount;
    }

    private void letItDie(Piece targetPiece) {
        targetPiece.setStatus(Piece.Status.Died);
        coordToPiece.remove(targetPiece.getCoordinate());
    }

    private void letItMove(Piece piece, Coordinate destination) {
        coordToPiece.remove(piece.getCoordinate());
        coordToPiece.put(destination, piece);
        piece.move(destination);
    }
}
