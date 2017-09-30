package self.mengqi.games.models;

import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.piece.Piece;
import self.mengqi.games.piece.Pieces;
import self.mengqi.games.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static self.mengqi.games.enums.PieceEnums.Faction.Black;
import static self.mengqi.games.enums.PieceEnums.Faction.Red;
import static self.mengqi.games.enums.PieceEnums.*;

/**
 * Created by Mengqi on 2017/9/16.
 * 棋盘类，管理棋盘上各棋子的情况
 * 单例
 */
public class Board {
    private static Board board;

    private List<Piece> pieces = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    // 指向当前棋盘上已激活的棋子
    private Piece activatedPiece;
    // 记录 坐标->棋子 的映射关系，需要在棋子走动时更新
    private HashMap<Coordinate, Piece> coordToPiece = new HashMap<>();

    private Piece blackJiang;
    private Piece redJiang;

    private Board() {}

    public static Board create() {
        if (null != board) {
            return board;
        } else {
            board = new Board();
            board.initTiles();
            board.initPieces();
        }
        return board;
    }

    private void initTiles() {
        for (Coordinate coord : Coordinates.getCoordList()) {
            tiles.add(new Tile(coord));
        }
    }

    private void initPieces() {
        Map<Type, List<Coordinate>> initialPieceCoord = new HashMap<>();

        for (Map.Entry<Type, List<Coordinate>> entrySet : initialPieceCoord.entrySet()) {
            for (Coordinate coordinate : entrySet.getValue()) {
                Type type = entrySet.getKey();
                Piece redPiece = Pieces.of(entrySet.getKey(), Red, coordinate);
                pieces.add(redPiece);
                if (redPiece != null) {
                    coordToPiece.put(redPiece.getCoordinate(), redPiece);
                }

                Piece blackPiece = Pieces.of(entrySet.getKey(), Black, coordinate.horizMirroredCoord());
                pieces.add(blackPiece);
                if (blackPiece != null) {
                    coordToPiece.put(blackPiece.getCoordinate(), blackPiece);
                }

                if (Type.Jiang == type) {
                    redJiang = redPiece;
                    blackJiang = blackPiece;
                }
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
                    this.activatedPiece.tryToMove(targetCoord);
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

        if (activatedPiece.tryToEat(destination)) {
            LogUtils.debugging("board", activatedPiece, "ate", targetPiece);

            letItDie(targetPiece);
        } else {
            LogUtils.debugging("board", activatedPiece, "cannot eat", targetPiece);
        }
    }

    /**
     * toggle the status of the activated piece
     * @param targetPiece
     */
    public void toggleActivatePiece(Piece targetPiece) {
        if (activatedPiece != null && activatedPiece != targetPiece) {
            activatedPiece.toggleActivated();
        }
        if (targetPiece.getStatus() == Status.Activated) {
            targetPiece.toggleActivated();
            activatedPiece = null;
        } else if (targetPiece.getStatus() == Status.Idle) {
            targetPiece.toggleActivated();
            activatedPiece = targetPiece;
        }
        activatedPiece.updateMovableArea(this);
        activatedPiece.updateEatableArea(this);
    }

    public Coordinate getJiangCoord(PieceEnums.Faction faction) {
        switch (faction) {
            case Black:
                return blackJiang.getCoordinate();
            case Red:
                return redJiang.getCoordinate();
        }
        throw new IllegalStateException("阵营不支持");
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

    private void letItDie(Piece targetPiece) {
        targetPiece.setStatus(Status.Died);
        coordToPiece.remove(targetPiece.getCoordinate());
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
