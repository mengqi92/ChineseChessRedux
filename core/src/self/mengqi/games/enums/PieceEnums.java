package self.mengqi.games.enums;

import self.mengqi.games.common.HumanFriendly;

import java.util.HashMap;

/**
 * Created by Mengqi on 2017/9/27.
 */
public class PieceEnums {

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
        Red("red", "红方", 1), Black("black", "黑方", -1);

        private String code;  // machine-friendly name
        private String chineseName; // human-friendly name
        private int forwardDirection; // 1 if the faction is facing up, or -1 if facing down

        Faction(String code, String chineseName, int forwardDirection) {
            this.code = code;
            this.chineseName = chineseName;
            this.forwardDirection = forwardDirection;
        }

        @Override
        public String toString() {
            return code;
        }

        public String getCode() {
            return code;
        }

        public int getForwardDirection() {
            return forwardDirection;
        }

        @Override
        public String toReadableString() {
            return chineseName;
        }

        public Faction enemy() {
            if (Red == this) return Black;
            else return Red;
        }
    }

    static class PieceNotFoundException extends Exception {
        PieceNotFoundException(String message) { super(message); }
    }
}
