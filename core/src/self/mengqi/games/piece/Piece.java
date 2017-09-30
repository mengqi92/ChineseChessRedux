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
     * tryToMove to destination
     * @param destination
     * @return is the destination reached
     */
    boolean tryToMove(Board board, Coordinate destination);

    /**
     * try to eat the piece on the destination
     * @param destination
     * @return true if the piece is eated
     */
    boolean tryToEat(Board board, Coordinate destination);

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
