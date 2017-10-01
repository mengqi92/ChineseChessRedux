package self.mengqi.games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import self.mengqi.games.actors.BoardActor;
import self.mengqi.games.actors.PieceActor;
import self.mengqi.games.actors.TileActor;
import self.mengqi.games.controller.InputHandler;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Tile;
import self.mengqi.games.piece.Piece;

public class ChineseChess extends ApplicationAdapter {

	private static Stage stage;
	private static Table table;
	private static Board board;

	@Override
	public void create () {
	    Gdx.app.setLogLevel(Application.LOG_DEBUG);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

	    stage = new Stage(new ScreenViewport(camera));
	    board = Board.create();

	    table = new Table();
	    table.setFillParent(true);
	    table.setDebug(true);

	    Group group = new Group();

	    group.addActor(table);
		BoardActor boardActor = new BoardActor(board);
		group.addActor(boardActor);
		for (Tile tile : board.getTiles()) {
			group.addActor(new TileActor(tile));
		}
        for (Piece piece : board.getPieces()) {
			group.addActor(new PieceActor(piece));
        }
        stage.addActor(group);

	    Gdx.input.setInputProcessor(new InputHandler(camera));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

    @Override
    public void resize(int width, int height) {}

    @Override
	public void dispose () {
	    stage.dispose();
    }
}
