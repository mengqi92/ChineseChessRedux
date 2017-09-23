package self.mengqi.games.models;

import self.mengqi.games.common.HumanFriendly;

/**
* Created by Mengqi on 2017/9/16.
* 这个类表示棋盘坐标，示意如下
*
*
  (1, 10)                            (9, 10)   // Coordinate
  (47, 50)                           (527, 50) // Position
  |===+===+===+===+===+===+===+===|  -          +---->
  |   |   |   |   |   |   |   |   | (60)        |
  |---|---|---|---|---|---|---|---|  -          V
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |===|===|===|===|===|===|===|===| // Black Coordinate: (1-9, 6)
  |        楚河       汉界         |
  |===|===|===|===|===|===|===|===| // Red Coordinate: (1-9, 5)
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |---|---|---|---|---|---|---|---|
  |   |   |   |   |   |   |   |   |
  |===+===+===+===+===+===+===+===|
  (47, 530)                        (527, 530)   // Position
  (1, 1)                           (9, 1)       // Coordinate
*/
public class Coordinate implements HumanFriendly {
    // TODO 工厂方法创建 Coordinate 实例，提高创建效率
    public int x;
    public int y;

    static final int LEFT = 1;
    static final int BOTTOM = 1;
    static final int RIGHT = 9;
    static final int TOP = 10;

    static final int RED_TOP = 5;       // 红方最顶行
    static final int BLACK_BOTTOM = 6;  // 黑方最底行

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Position position) {
        x = Math.round((position.x - Position.LEFT)/Position.GAP) + 1;
        y = Math.round((position.y - Position.TOP)/Position.GAP) + 1;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    /**
     * horizontally flip the Coordinate which will be on the other faction side
     */
    public Coordinate horizMirroredCoord() {
        return new Coordinate(x, BLACK_BOTTOM + RED_TOP - y);
    }

    /**
     * vertically flip the Coordinate which will be on the same faction side
     */
    public Coordinate vertMirroredCoord() {
        return new Coordinate(LEFT + RIGHT - x, y);
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
}
