package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import self.mengqi.games.models.Position;
import self.mengqi.games.models.Tile;

/**
 * Created by Mengqi on 2017/9/24.
 */
public class TileActor extends Actor {
    private Tile tile;
    private Sprite sprite;

    private Position position;

    public TileActor(Tile tile) {
        sprite = new Sprite(new Texture(Gdx.files.internal("tile.png")));
        this.tile = tile;

        position = new Position(tile.getCoordinate());
        sprite.setCenter(position.x, position.y);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void act(float delta) {
        switch (tile.getStatus()) {
            case Idle:
                this.setVisible(false);
                break;
            case Movable:
                this.setVisible(true);
//                sprite.setColor(0.793f, 0.849f, 0.383f, 0.7f);
//                sprite.setColor(0.899f, 0.835f, 0.405f, 0.7f);
                sprite.setColor(1f, 1f, 1f, 0.3f);
                break;
            case Eatable:
                this.setVisible(true);
                sprite.setColor(0.849f, 0.439f, 0.383f, 0.9f);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible())
            return;
        sprite.draw(batch, parentAlpha);
    }
}
