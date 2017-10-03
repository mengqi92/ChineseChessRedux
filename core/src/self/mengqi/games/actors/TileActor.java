package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import self.mengqi.games.common.PresetColors;
import self.mengqi.games.models.Position;
import self.mengqi.games.models.Tile;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Mengqi on 2017/9/24.
 */
public class TileActor extends Actor {
    private Tile tile;
    private Texture texture;
    private Texture defaultTexture;
    private Texture targetTexture;

    private Position position;

    public TileActor(Tile tile) {
        position = new Position(tile.getCoordinate());

        defaultTexture = new Texture(Gdx.files.internal("tile.png"));
//        targetTexture = new Texture(Gdx.files.internal("target.gif"));

        this.tile = tile;
        texture = defaultTexture;

        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setPosition(position.x, position.y, Align.center);
        setColor(Color.WHITE);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void act(float delta) {
        switch (tile.getStatus()) {
            case Idle:
                addAction(color(Color.CLEAR, 0.2f));
                break;
            case Movable:
                addAction(sequence(delay(0.1f), color(PresetColors.LIGHT_WHITE, 0.2f)));
                break;
            case Eatable:
                addAction(color(PresetColors.LIGHT_RED, 0.3f));
                break;
        }
        super.act(delta);
    }

    @Override
    protected void positionChanged() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible())
            return;
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
