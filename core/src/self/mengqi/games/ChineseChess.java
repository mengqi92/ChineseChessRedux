package self.mengqi.games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import self.mengqi.games.actors.BoardActor;
import self.mengqi.games.actors.PieceActor;
import self.mengqi.games.controller.InputHandler;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Piece;

public class ChineseChess extends ApplicationAdapter {

	private static Stage stage;
	private static Table table;
	private static Board board;

	@Override
	public void create () {
	    Gdx.app.setLogLevel(Application.LOG_DEBUG);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    stage = new Stage(new ScreenViewport(camera));
	    board = Board.create();

	    table = new Table();
	    table.setFillParent(true);
	    table.setDebug(true);

	    stage.addActor(table);
        stage.addActor(new BoardActor());
        for (Piece piece : board.getPieces()) {
            stage.addActor(new PieceActor(piece));
        }

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
