package self.mengqi.games.piece;

import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.utils.LogUtils;

import java.util.HashSet;
import java.util.Set;

import static self.mengqi.games.enums.PieceEnums.*;
import static self.mengqi.games.enums.PieceEnums.Status.Activated;
import static self.mengqi.games.enums.PieceEnums.Status.Idle;

/**
 * Created by Mengqi on 2017/9/29.
 * skeleton implementation of the interface Piece
 */
public abstract class AbstractPiece implements Piece {
    private int id;
    protected Type type;
    protected Faction faction;
    protected Status status;
    protected Coordinate coordinate;

    protected Set<Coordinate> movableArea = new HashSet<>();  // 移动范围
    protected Set<Coordinate> eatableArea = new HashSet<>();  // 攻击范围

    AbstractPiece(Type type, Faction faction, Coordinate coordinate) {
        this.type = type;
        this.faction = faction;
        this.coordinate = coordinate;
        this.status = Idle;
        this.id = generateId(type, faction, status, coordinate);
    }

    @Override
    public abstract void updateEatableArea(final Board board);

    @Override
    public abstract void updateMovableArea(final Board board);

    @Override
    public boolean tryToMove(final Board board, final Coordinate destination) {
        if (isReachable(destination)) {
            this.moveTo(board, destination);
            return true;
        } else {
            LogUtils.debugging("piece", this, "cant move to", destination);
            return false;
        }
    }

    private void moveTo(final Board board, final Coordinate destination) {
        coordinate = destination;
        updateMovableArea(board);
        updateEatableArea(board);
        LogUtils.debugging("piece", this, " -> ", destination);
    }

    @Override
    public boolean tryToEat(final Board board, final Coordinate destination) {
        if (isEatable(destination)) {
            this.moveTo(board, destination);
            return true;
        } else {
            LogUtils.debugging("piece", this, "cannot eat the piece at", destination);
            return false;
        }
    }

    /**
     * if the destination is reachable
     * @param destination
     * @return true if the destination is reachable
     */
    protected boolean isReachable(Coordinate destination) {
        return movableArea.contains(destination);
    }

    /**
     * if there is an enemy piece on destination, is it eatable
     * @param destination
     * @return true if the enemy piece is eatable
     */
    protected boolean isEatable(Coordinate destination) {
        return eatableArea.contains(destination);
    }

    @Override
    public void toggleActivated() {
        if (Idle == status) status = Activated;
        else if (Activated == status) status = Idle;
        LogUtils.debugging("piece", this, "toggle activated");
    }

    @Override
    public boolean sameSide(Piece targetPiece) {
        return faction == targetPiece.getFaction();
    }

    @Override
    public Status getStatus() { return this.status; }

    @Override
    public void setStatus(Status status) { this.status = status; }

    @Override
    public Type getType() { return this.type; }

    @Override
    public Faction getFaction() { return this.faction; }

    @Override
    public String toReadableString() {
        return String.format("[%s] - %s: (%d, %d)", faction.toReadableString(), this.getType().toReadableString(), coordinate.x, coordinate.y);
    }

    @Override
    public String toString() {
        return String.format("%s-%s: (%d, %d)", faction, type, coordinate.x, coordinate.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;
        return this.id == piece.getId();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Set<Coordinate> getMovableArea() {
        return movableArea;
    }

    @Override
    public Set<Coordinate> getEatableArea() {
        return eatableArea;
    }

    private int generateId(Type type, Faction faction, Status status, Coordinate coordinate) {
        int result = type.hashCode();
        result = 31 * result + faction.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + coordinate.hashCode();
        return result;
    }
}
