package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import self.mengqi.games.models.Position;
import self.mengqi.games.piece.Piece;

/**
 * Created by Mengqi on 2017/9/14.
 * 棋子类 Actor，主要用于展示
 */
public class PieceActor extends Actor {
    private Piece piece;
    private Sprite sprite;
    private Position position;
    private Sprite activeSprite;
    private Sprite idleSprite;

    public PieceActor(Piece piece) {
        this.setName(piece.toReadableString());
        this.piece = piece;
        this.position = new Position(piece.getCoordinate());

        activeSprite = new Sprite(new Texture(Gdx.files.internal(
                String.format("pieces/activated/%s_%s.gif",
                        this.piece.getFaction().getCode(),
                        this.piece.getType().getCode()))));

        idleSprite = new Sprite(new Texture(Gdx.files.internal(
                String.format("pieces/idle/%s_%s.gif",
                        this.piece.getFaction().getCode(),
                        this.piece.getType().getCode()))));
    }

    @Override
    public void act(float delta) {
        switch (piece.getStatus()) {
            case Died:
                this.setVisible(false);
                return;
            case Activated:
                sprite = activeSprite;
                break;
            case Idle:
                sprite = idleSprite;
                break;
        }

        this.position = new Position(piece.getCoordinate());
        sprite.setCenter(position.x, position.y);

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public String toString() {
        return this.piece.toString();
    }
}
