package self.mengqi.games.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Coordinate;
import self.mengqi.games.models.Position;
import self.mengqi.games.utils.LogUtils;

/**
 * Created by Mengqi on 2017/9/16.
 * handler for game input
 */
public class InputHandler extends InputAdapter implements InputProcessor {
    private Viewport viewport;
    private Board board = Board.create();

    public InputHandler(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Position targetPosition = new Position(viewport.unproject(new Vector3(screenX, screenY, 0)));
        Coordinate targetCoord = new Coordinate(targetPosition);

        LogUtils.debugging("input", "coordinate", targetCoord);

        board.makeDecision(targetCoord);
        return true;
    }
}
