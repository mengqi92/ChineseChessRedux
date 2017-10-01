package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import self.mengqi.games.models.Board;

/**
 * Created by Mengqi on 2017/9/16.
 */
public class BoardActor extends Actor {
    private Sprite sprite;
    private Board board;

    public BoardActor(Board board) {
        this.board = board;

        sprite = new Sprite(new Texture(Gdx.files.internal("chessboard.png")));

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
