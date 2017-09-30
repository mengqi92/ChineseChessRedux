package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import self.mengqi.games.models.Position;
import self.mengqi.games.models.Tile;
import self.mengqi.games.utils.LogUtils;

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
        this.setZIndex(2);
    }

    @Override
    public void act(float delta) {
        switch (tile.getStatus()) {
            case Idle:
//                this.setVisible(false);
//                this.setColor(255, 255, 0, 0.1f);
                break;
            case Movable:
                LogUtils.debugging("tile", "actor", "movable");
                this.setVisible(true);
                sprite.setColor(255, 0, 0, 0.5f);
                break;
            case Eatable:
                this.setVisible(true);
                sprite.setColor(0, 0, 255, 0.5f);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = sprite.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
}
