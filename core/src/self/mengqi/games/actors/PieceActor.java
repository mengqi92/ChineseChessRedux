package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import self.mengqi.games.models.Position;
import self.mengqi.games.piece.Piece;

/**
 * Created by Mengqi on 2017/9/14.
 * 棋子类 Actor，主要用于展示
 */
public class PieceActor extends Actor {
    private Piece piece;
    private Texture texture;
    private Position position;
    private Texture activeTexure;
    private Texture idleTexture;
    private static float MOVING_DURATION = 0.45f;

    public PieceActor(Piece piece) {
        this.setName(piece.toReadableString());
        this.piece = piece;
        this.position = new Position(piece.getCoordinate());

        activeTexure = new Texture(Gdx.files.internal(
                String.format("pieces/activated/%s_%s.gif",
                        this.piece.getFaction().getCode(),
                        this.piece.getType().getCode())));

        idleTexture = new Texture(Gdx.files.internal(
                String.format("pieces/idle/%s_%s.gif",
                        this.piece.getFaction().getCode(),
                        this.piece.getType().getCode())));
        texture = idleTexture;

        setWidth(texture.getWidth());
        setHeight(texture.getWidth());
        setPosition(position.x, position.y, Align.center);

        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        switch (piece.getStatus()) {
            case Died:
                this.setVisible(false);
                return;
            case Activated:
                texture = activeTexure;
                break;
            case Idle:
                texture = idleTexture;
                break;
        }
        Position lastPosition = this.position;
        this.position = new Position(piece.getCoordinate());
        if (!this.position.equals(lastPosition)) {
            this.addAction(Actions.moveToAligned(position.x, position.y, Align.center,
                    MOVING_DURATION, Interpolation.sineOut));
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    protected void positionChanged() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return this.piece.toString();
    }
}
