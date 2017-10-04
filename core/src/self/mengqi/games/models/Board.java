package self.mengqi.games.models;

import com.badlogic.gdx.Gdx;
import com.sun.istack.internal.Nullable;
import self.mengqi.games.piece.Piece;
import self.mengqi.games.piece.Pieces;
import self.mengqi.games.utils.LogUtils;

import java.util.*;

import static self.mengqi.games.enums.PieceEnums.*;
import static self.mengqi.games.enums.PieceEnums.Faction.Black;
import static self.mengqi.games.enums.PieceEnums.Faction.Red;
import static self.mengqi.games.enums.PieceEnums.Type;
import static self.mengqi.games.enums.PieceEnums.Type.*;
import static self.mengqi.games.models.Tile.TileStatus.*;

/**
 * Created by Mengqi on 2017/9/16.
 * 棋盘类，管理棋盘上各棋子的情况
 * 单例
 */
public class Board {
    private static Board board;
    private static HashMap<Type, List<Coordinate>> initialPieceCoord = new HashMap<>();

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
    private List<Tile> tiles = new ArrayList<>();
    // 指向当前棋盘上已激活的棋子
    private Piece activatedPiece = null;
    // 记录 坐标->棋子 的映射关系，需要在棋子走动时更新
    private HashMap<Coordinate, Piece> coordToPiece = new HashMap<>();
    // 记录 坐标->Tile 的映射关系
    private HashMap<Coordinate, Tile> coordToTile = new HashMap<>();

    private Piece blackJiang;// 黑将棋子的引用
    private Piece redJiang;  // 红将棋子的引用
    private Faction initialFaction = Red;
    private Faction currentFaction = initialFaction;  // 当前走子阵营
    private Piece jiangJunPiece;

    private Board() {}

    public static Board create() {
        if (null != board) {
            return board;
        } else {
            board = new Board();
            board.initTiles();
            board.initPieces();
            board.updateAllPieces();
        }
        return board;
    }

    /**
     * 初始化所有 tile 状态
     */
    private void initTiles() {
        for (Coordinate coord : Coordinates.getCoordList()) {
            Optional<Tile> tile = Tiles.of(coord);
            if (tile.isPresent()) {
                tiles.add(tile.get());
                coordToTile.put(coord, tile.get());
            }
        }
    }

    /**
     * 初始化所有棋子，并将其订阅至该棋盘
     */
    private void initPieces() {
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

                if (Jiang == type) {
                    redJiang = redPiece;
                    blackJiang = blackPiece;
                }
            }
        }
    }

    /**
     * 向所有棋子发布更新通知
     */
    private void updateAllPieces() {
        Piece anyJiangJunPiece = null;  // 棋盘上是否有棋子正在将军
        for (Piece piece : this.pieces) {
            piece.updateAreas(this);
            if (piece.getEatableArea().contains(board.getJiangCoord(piece.getFaction().enemy()))) {
                anyJiangJunPiece = piece;
            }
        }
        jiangJunPiece = anyJiangJunPiece;
        Gdx.app.debug("board", "将军！");
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
                // 选择的棋子是当前的阵营时才激活该棋子
                if (targetPiece.getFaction() == currentFaction) {
                    this.toggleActivatePiece(targetPiece);
                }
            }
        } else {  // 点击的位置没有棋子
            if (this.hasPieceActivated()) {
                if (Coordinate.wholeField.within(targetCoord)) {
                    tryToMove(targetCoord);
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
            if (targetPiece.getFaction() == currentFaction) {
                toggleActivatePiece(targetPiece);
            } else {
                tryToEat(targetPiece);
            }
        }
    }

    /**
     * 切换当前走子阵营
     */
    private void switchFaction() {
        updateAllPieces();
        updateAreas(activatedPiece);
        activatedPiece.setStatus(Status.Idle);
        activatedPiece = null;
        currentFaction = this.currentFaction.enemy();
    }

    /**
     * try to move to destination
     * @param destination
     */
    private void tryToMove(Coordinate destination) {
        Coordinate origin = activatedPiece.getCoordinate();
        if (this.activatedPiece.isReachable(destination)) {
            this.activatedPiece.moveTo(destination);
            this.coordToPiece.remove(origin);
            this.coordToPiece.put(destination, activatedPiece);
            LogUtils.debugging("board", activatedPiece, "move to", destination);
            this.switchFaction();
        }
    }

    /**
     * try to eat another piece
     *
     * @param targetPiece the target piece on another faction
     */
    private void tryToEat(Piece targetPiece) {
        Coordinate origin = activatedPiece.getCoordinate();
        Coordinate destination = targetPiece.getCoordinate();
        if (activatedPiece.isEatable(destination)) {
            this.activatedPiece.moveTo(destination);
            letItDie(targetPiece);
            this.coordToPiece.remove(origin);
            this.coordToPiece.put(destination, activatedPiece);
            LogUtils.debugging("board", activatedPiece, "ate", targetPiece);
            this.switchFaction();
        } else {
            LogUtils.debugging("board", activatedPiece, "cannot eat", targetPiece);
        }
    }

    /**
     * update tiles' status based on movable and eatable area of the piece
     * @param piece
     */
    private void updateAreas(Piece piece) {
        if (piece == null) {
            for (Tile tile : tiles) {
                tile.setStatus(Idle);
            }
        } else {
            tiles.forEach(tile -> tile.setStatus(Idle));
            for (Coordinate coord : piece.getMovableArea()) {
                Tile tile = coordToTile.get(coord);
                tile.setStatus(Movable);
            }
            for (Coordinate coord : piece.getEatableArea()) {
                Tile tile = coordToTile.get(coord);
                tile.setStatus(Eatable);
            }
        }
    }

    /**
     * get the faction of activated piece
     * @return
     */
    public Faction getCurrentFaction() {
        return currentFaction;
    }

    public Faction getEnemyFaction() { return currentFaction.enemy(); }

    /**
     * get the type of activated piece
     * @return
     */
    public Type currentPieceType() {
        if (null != activatedPiece) {
            return activatedPiece.getType();
        } else {
            return Jiang;
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
        this.updateAreas(activatedPiece);
    }

    @Nullable
    public Coordinate getJiangCoord(Faction faction) {
        switch (faction) {
            case Black:
                return (null != blackJiang) ? blackJiang.getCoordinate() : null;
            case Red:
                return (null != redJiang) ? redJiang.getCoordinate() : null;
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
        return coordToPiece.containsKey(Coordinates.of(x, y));
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

    /**
     * is the piece on the coord in same side
     * @param faction
     * @param coord
     * @return true if the piece is our friend
     */
    public boolean hasFriendPieceOn(Faction faction, Coordinate coord) {
        return coordToPiece.containsKey(coord) &&
                faction == coordToPiece.get(coord).getFaction();
    }

    /**
     * is the piece on the coord is enemy
     * @param faction
     * @param coord
     * @return true if the piece is hostile
     */
    public boolean hasEnemyPieceOn(Faction faction, Coordinate coord) {
        return coordToPiece.containsKey(coord) &&
                faction == coordToPiece.get(coord).getFaction().enemy();
    }

    public boolean isJiangJuning() {
        return jiangJunPiece != null;
    }

    public Piece getJiangJunPiece() {
        return jiangJunPiece;
    }
}
