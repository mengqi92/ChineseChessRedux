package self.mengqi.games.models;

import com.sun.istack.internal.Nullable;
import self.mengqi.games.common.HumanFriendly;

import static self.mengqi.games.enums.PieceEnums.*;
import static self.mengqi.games.enums.PieceEnums.Faction.Black;
import static self.mengqi.games.enums.PieceEnums.Faction.Red;

/**
* Created by Mengqi on 2017/9/16.
* 这个类表示棋盘坐标，示意如下
*
*
  (1, 10)                            (9, 10)   // Coordinate
  (47, 50)                           (527, 50) // Position
  |===+===+===O===O===O===+===+===|  -          +---->
  |   |   |   |   |   |   |   |   | (60)        |
  |---|---|---O---|---O---|---|---|  -          V
  |   |   |   |   |   |   |   |   |
  |---|---|---O---O---O---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |===|===|===|===|===|===|===|===| // Black Coordinate: (1-9, 6)
  |        楚河       汉界         |
  |===|===|===|===|===|===|===|===| // Red Coordinate: (1-9, 5)
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---O---O---O---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---O---|---O---|---|---|
  |   |   |   |   |   |   |   |   |
  |===+===+===O===O===O===+===+===|
  (47, 530)                        (527, 530)   // Position
  (1, 1)      (4, 1)  (6, 1)       (9, 1)       // Coordinate
*/
public class Coordinate implements HumanFriendly {
    public int x;
    public int y;

    public static final int LEFT = 1;
    public static final int BOTTOM = 1;
    public static final int RIGHT = 9;
    public static final int TOP = 10;

    public static final int WIDTH = RIGHT - LEFT + 1;
    public static final int HEIGHT = TOP - BOTTOM + 1;

    static final int RED_TOP = 5;       // 红方最顶行
    static final int BLACK_BOTTOM = 6;  // 黑方最底行

    public static final Region blackField = new Region(LEFT, TOP, RIGHT, BLACK_BOTTOM);
    public static final Region redField = new Region(LEFT, RED_TOP, RIGHT, BOTTOM);
    public static final Region wholeField = new Region(LEFT, TOP, RIGHT, BOTTOM);

    // 宫的坐标
    static final int CAMP_LEFT = 4;
    static final int CAMP_RIGHT = 6;

    static final int RED_CAMP_TOP = 3;
    static final int RED_CAMP_BOTTOM = 1;

    static final int BLACK_CAMP_TOP = 10;
    static final int BLACK_CAMP_BOTTOM = 8;

    static final Region redCamp = new Region(CAMP_LEFT, RED_CAMP_TOP, CAMP_RIGHT, RED_CAMP_BOTTOM);
    static final Region blackCamp = new Region(CAMP_LEFT, BLACK_CAMP_TOP, CAMP_RIGHT, BLACK_CAMP_BOTTOM);

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Position position) {
        x = Math.round((position.x - Position.LEFT) / Position.GAP) + 1;
        y = Math.round((position.y - Position.TOP) / Position.GAP) + 1;
    }

    /**
     * 判断该阵营的朝向是否向上走
     * @param faction
     * @return 当该阵营是朝上走时返回 1，否则返回 -1
     */
    public static int facingUp(Faction faction) {
        return faction == Red ? 1 : -1;
    }

    /**
     * is the given coordinate in this faction's camp
     * @param faction
     * @param coord
     * @return
     */
    public static boolean withinCamp(Faction faction, Coordinate coord) {
        return (faction == Red && Coordinate.redCamp.within(coord)) ||
               (faction == Black && Coordinate.blackCamp.within(coord));
    }

    /**
     * is the given coord in this faction's field
     * @param faction
     * @param coord
     * @return
     */
    public static boolean withinField(Faction faction, Coordinate coord) {
        return (faction == Red && Coordinate.redField.within(coord)) ||
                (faction == Black && Coordinate.blackField.within(coord));
    }

    /**
     * is the coordinate in the valid area of the board
     * @param coord
     * @return
     */
    public static boolean withinWholeField(Coordinate coord) {
        return wholeField.within(coord);
    }

    /**
     * is the given destination is one-by-one step away from current coordination,
     * @param curCoord
     * @param destination
     * @return
     */
    public static boolean isOneByOneStepAway(Coordinate curCoord, Coordinate destination) {
        return Math.abs(curCoord.x - destination.x) == 1 && Math.abs(curCoord.y - destination.y) == 1;
    }

    /**
     * is the given destination is one step further of current coordination,
     * @param curCoord
     * @param destination
     * @return
     */
    public static boolean isOneStepAway(Coordinate curCoord, Coordinate destination) {
        return Math.abs(curCoord.x - destination.x) == 1 && curCoord.y == destination.y ||
                (curCoord.x == destination.x && Math.abs(curCoord.y - destination.y) == 1);
    }

    /**
     * is the given destination is one step backward to current coordination
     * @param faction
     * @param curCoord
     * @param destination
     * @return
     */
    public static boolean isOneStepBackward(Faction faction, Coordinate curCoord, Coordinate destination) {
        return isOneStepForward(faction, destination, curCoord);
    }

    /**
     * is the given destination is one step forward to current coordination
     * @param faction
     * @param curCoord
     * @param destination
     * @return
     */
    public static boolean isOneStepForward(Faction faction, Coordinate curCoord, Coordinate destination) {
        return curCoord.x == destination.x && (facingUp(faction) * (destination.y - curCoord.y) == 1);
    }

    /**
     * is the given destination two-by-two left forward step away from current coordination
     * a two-by-two left forward step is just like a 田 step
     * ↑ ↑ ← ←
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByTwoUpLeft(Coordinate curCoordinate, Coordinate destination) {
        return curCoordinate.x - destination.x == 2 && destination.y - curCoordinate.y == 2;
    }

    /**
     * is the given destination two-by-two right forward step away from current coordination
     * a two-by-two right forward step is just like a 田 step
     * ↑ ↑ → →
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByTwoUpRight(Coordinate curCoordinate, Coordinate destination) {
        return destination.x - curCoordinate.x == 2 && destination.y - curCoordinate.y == 2;
    }

    /**
     * is the given destination two-by-two right backward step away from current coordination
     * a two-by-two right backward step is just like a 田 step
     * ↓ ↓ → →
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByTwoDownRight(Coordinate curCoordinate, Coordinate destination) {
        return destination.x - curCoordinate.x == 2 &&  curCoordinate.y - destination.y == 2;
    }

    /**
     * is the given destination two-by-two left backward step away from current coordination
     * a two-by-two left backward step is just like a 田 step
     * ↓ ↓ ← ←
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByTwoDownLeft(Coordinate curCoordinate, Coordinate destination) {
        return curCoordinate.x - destination.x == 2 &&  curCoordinate.y - destination.y == 2;
    }

    /**
     * is the given destination two-by-one step away from current coordination
     * a two-by-one backward step is just like a 日 step: two step backward and one step left/right
     *
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByOneDown(Coordinate curCoordinate, Coordinate destination) {
        return curCoordinate.y - destination.y == 2 && Math.abs(curCoordinate.x - destination.x) == 1;
    }

    /**
     * is the given destination two-by-one step away from current coordination
     * a two-by-one forward step is just like a 日 step: two step forward and one step left/right
     *
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isTwoByOneUp(Coordinate curCoordinate, Coordinate destination) {
        return destination.y - curCoordinate.y == 2 && Math.abs(curCoordinate.x - destination.x) == 1;
    }

    /**
     * is the given destination one-by-two left away from current coordination
     * a one-by-two left step is just like a laid downed 日 step: one step forward/backward and two step left
     * ⬆ ⬅ ⬅, or ⬇ ⬅ ⬅
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isOneByTwoLeft(Coordinate curCoordinate, Coordinate destination) {
        return curCoordinate.x - destination.x == 2 && Math.abs(destination.y - curCoordinate.y) == 1;
    }

    /**
     * is the given destination one-by-two right away from current coordination
     * a one-by-two right step is just like a laid downed 日 step: one step forward/backward and two step right
     * ⬆ ➡ ➡, or ⬇ ➡ ➡
     * @param curCoordinate
     * @param destination
     * @return
     */
    public static boolean isOneByTwoRight(Coordinate curCoordinate, Coordinate destination) {
        return destination.x - curCoordinate.x == 2 && Math.abs(destination.y - curCoordinate.y) == 1;
    }

    /**
     * horizontally flip the Coordinate which will be on the other faction side
     */
    public Coordinate horizMirroredCoord() {
        return new Coordinate(x, BLACK_BOTTOM + RED_TOP - y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public String toReadableString() {
        return String.format("@(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Nullable
    public static Coordinate forward(Faction faction, Coordinate coordinate, int step) {
        int yShift = faction.getForwardDirection() * step;
        return Coordinates.of(coordinate.x, coordinate.y + yShift);
    }

    /**
     * 用于表示横、纵坐标轴
     */
    public enum Axis {
        X, Y;
    }

    /**
     * 用于表示方向，可表示 横5纵2: x=5, y=2
     */
    public class Direction {
        int x;
        int y;
    }

    private enum DirectionXAxis {
        Right(1), Left(-1);
        private int factor;

        DirectionXAxis(int factor) {
            this.factor = factor;
        }
    }

    private enum DirectionYAxis {
        Up(1), Down(-1);
        private int factor;

        DirectionYAxis(int factor) {
            this.factor = factor;
        }
    }
}
