package self.mengqi.games.piece;

import self.mengqi.games.common.HumanFriendly;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;

import java.util.Set;

import static self.mengqi.games.enums.PieceEnums.*;

/**
 * Created by Mengqi on 2017/9/27.
 * The interface of pieces
 */
// FIXME the extending here is just to make logging magic work, a better solution is needed.
public interface Piece extends HumanFriendly {
    /**
     * is the destination reachable
     * @param destination
     * @return true if the destination can be reached
     */
    boolean isReachable(Coordinate destination);

    /**
     * move the piece to the destination
     * @param destination
     * @return
     */
    void moveTo(Coordinate destination);

    /**
     * is the piece on the destination eatable
     * @param destination
     * @return true if the piece on the destination is eatable
     */
    boolean isEatable(Coordinate destination);

    /**
     * trigger updating movable and eatable areas
     */
    void updateAreas(Board board);

    /**
     * toggle status between activated and idle
     */
    void toggleActivated();

    /**
     * whether both pieces are on same side
     */
    boolean sameSide(Piece piece);

    /**
     * 更新攻击范围
     */
    void updateEatableArea(final Board board);

    /**
     * 更新移动范围
     */
    void updateMovableArea(final Board board);

    Status getStatus();
    void setStatus(Status status);
    Type getType();
    Faction getFaction();
    int getId();
    Coordinate getCoordinate();

    Set<Coordinate> getMovableArea();

    Set<Coordinate> getEatableArea();
}
