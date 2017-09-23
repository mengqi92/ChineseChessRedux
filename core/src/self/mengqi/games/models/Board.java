package self.mengqi.games.models;

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
    private Board() {}

    public static Board create() {
        if (null != board) {
            return board;
        } else {
            board = new Board();
            board.initPieces();
        }
        return board;
    }

    private List<Piece> pieces = new ArrayList<>();
    // 指向当前棋盘上已激活的棋子
    private Piece activatedPiece;
    // 记录 坐标->棋子 的映射关系，需要在棋子走动时更新 TODO
    private HashMap<Coordinate, Piece> coordToPiece = new HashMap<>();

    private static HashMap<Piece.Type, List<Coordinate>> initialPieceCoord = new HashMap<>();
    static {
        initialPieceCoord.put(Zu, Arrays.asList(
                Coordinate.of(1, 4),
                Coordinate.of(3, 4),
                Coordinate.of(5, 4),
                Coordinate.of(7, 4),
                Coordinate.of(9, 4)
        ));
        initialPieceCoord.put(Pao, Arrays.asList(Coordinate.of(2, 3), Coordinate.of(8, 3)));

        initialPieceCoord.put(Ju, Arrays.asList(Coordinate.of(1, 1), Coordinate.of(9, 1)));
        initialPieceCoord.put(Ma, Arrays.asList(Coordinate.of(2, 1), Coordinate.of(8, 1)));
        initialPieceCoord.put(Xiang, Arrays.asList(Coordinate.of(3, 1), Coordinate.of(7, 1)));
        initialPieceCoord.put(Shi, Arrays.asList(Coordinate.of(4, 1), Coordinate.of(6, 1)));
        initialPieceCoord.put(Jiang, Arrays.asList(Coordinate.of(5, 1)));
    }

    private void initPieces() {
        for (Map.Entry<Piece.Type, List<Coordinate>> entrySet : initialPieceCoord.entrySet()) {
            for (Coordinate coordinate:entrySet.getValue()) {
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
     * @param targetCoord
     */
    public void makeDecision(Coordinate targetCoord) {
        Piece targetPiece = this.getCoordToPiece().get(targetCoord);
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
                this.move(targetCoord);
            }
        }
    }

    /**
     * order the activated piece to take takeAction on another piece
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
                activatedPiece.attack(targetPiece);
            }
        }
    }

    public boolean hasPieceActivated() {
        return activatedPiece != null;
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

    public List<Piece> getPieces() {
        return pieces;
    }

    public HashMap<Coordinate, Piece> getCoordToPiece() {
        return coordToPiece;
    }

    public void move(Coordinate targetCoord) {
        coordToPiece.remove(activatedPiece.getCoordinate());
        coordToPiece.put(targetCoord, activatedPiece);

        activatedPiece.move(targetCoord);
    }

}
