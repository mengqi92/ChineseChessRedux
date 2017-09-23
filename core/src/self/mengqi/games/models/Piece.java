package self.mengqi.games.models;

import self.mengqi.games.common.HumanFriendly;

import java.util.HashMap;

import static self.mengqi.games.models.Piece.Status.Activated;
import static self.mengqi.games.models.Piece.Status.Idle;
import static self.mengqi.games.utils.LogUtils.logging;

/**
 * Created by Mengqi on 2017/9/16.
 * 棋子模型
 */
public class Piece implements HumanFriendly {
    private int id;
    private Type type;
    private Faction faction;
    private Status status;
    private Coordinate coordinate;

    Piece(Type type, Faction faction, Coordinate coordinate) {
        this.type = type;
        this.faction = faction;
        this.coordinate = coordinate;
        this.status = Status.Idle;
        this.id = generateId(type, faction, status, coordinate);
    }

    /**
     * whether both pieces are on same side
     */
    public boolean sameSide(Piece targetPiece) {
        return faction == targetPiece.getFaction();
    }

    /**
     * move to destination
     * @param destination
     */
    // TODO 通过 AOP 打 log
    public void move(Coordinate destination) {
        logging("piece", this, destination);
        coordinate = destination;
    }

    /**
     * try to attack another piece
     * @param targetPiece 目标子
     */
    public void attack(Piece targetPiece) {
        // TODO 判断走子是否合法，如果合法则吃子
        logging("piece", this, targetPiece);
    }

    /**
     * toggle status between activated and idle
     */
    public void toggleActivated() {
        switch (status) {
            case Idle:
                status = Activated;
                break;
            case Activated:
                status = Idle;
                break;
        }
        logging("piece", this, "toglle activated");
    }

    static class PieceNotFoundException extends Exception {
        PieceNotFoundException(String message) { super(message); }
    }

    @Override
    public String toReadableString() {
        return String.format("[%s] - %s: (%d, %d)", faction.toReadableString(), this.getType().toReadableString(), coordinate.x, coordinate.y);
    }

    @Override
    public String toString() {
        return String.format("%s-%s: (%d, %d)", faction, type, coordinate.x, coordinate.y);
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getDisplayName() { return type.name(); }

    public Faction getFaction() {
        return faction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;
        return this.id == piece.getId();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    private int generateId(Type type, Faction faction, Status status, Coordinate coordinate) {
        int result = type.hashCode();
        result = 31 * result + faction.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + coordinate.hashCode();
        return result;
    }

/**************************************************************************************************
*  内部类
*************************************************************************************************/

    /**
     * 棋子状态枚举
     */
    public enum Status {
        Idle,  // 静止状态
        Activated,  // 激活状态（准备走子）
        Died,  // 死亡状态（被吃）
    }

    public enum Type implements HumanFriendly {
        Zu("zu", "卒"), Pao("pao", "炮"),
        Ju("ju", "车"), Ma("ma", "马"), Xiang("xiang", "相"), Shi("shi", "士"), Jiang("jiang", "将");

        private String code;
        private String chineseName;

        Type(String code, String chineseName) {
            this.code = code;
            this.chineseName = chineseName;
        }

        @Override
        public String toString() {
            return code;
        }

        private static HashMap<String, Type> pieceNameMap = new HashMap<>();

        static {
            for(Type type : Type.values()) {
                pieceNameMap.put(type.code, type);
            }
        }

        public static Type of(String code) throws PieceNotFoundException {
            if(pieceNameMap.containsKey(code)) {
                return pieceNameMap.get(code);
            } else {
                throw new PieceNotFoundException(String.format("%s is not a valid piece", code));
            }
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toReadableString() {
            return chineseName;
        }
    }

    /**
     * 阵营枚举
     */
    public enum Faction implements HumanFriendly {
        Red("red", "红方"), Black("black", "黑方");

        private String code;  // machine-friendly name
        private String chineseName; // human-friendly name

        Faction(String code, String chineseName) {
            this.code = code;
            this.chineseName = chineseName;
        }

        @Override
        public String toString() {
            return code;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toReadableString() {
            return chineseName;
        }
    }
}
