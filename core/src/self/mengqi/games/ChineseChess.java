package self.mengqi.games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import self.mengqi.games.actors.Avatar;
import self.mengqi.games.actors.BoardActor;
import self.mengqi.games.actors.PieceActor;
import self.mengqi.games.actors.TileActor;
import self.mengqi.games.controller.InputHandler;
import self.mengqi.games.display.FixedViewport;
import self.mengqi.games.models.Board;
import self.mengqi.games.models.Tile;
import self.mengqi.games.piece.Piece;

public class ChineseChess extends ApplicationAdapter {

    private static final int UI_WIDTH = 380;
    private static final int UI_HEIGHT = 640;
    private static final int BOARD_WIDTH = 573;
    private static final int BOARD_HEIGHT = 640;
    private static final boolean DEBUGGING = false;
    private Stage boardStage;
    private Stage uiStage;
	private static Board board;
	private Image uiBackgroundImage;

    @Override
	public void create () {
	    Gdx.app.setLogLevel(Application.LOG_DEBUG);
        boardStage = new Stage(new FixedViewport(0, 0, BOARD_WIDTH, BOARD_HEIGHT));
        uiStage = new Stage(new FixedViewport(BOARD_WIDTH, 0, UI_WIDTH, UI_HEIGHT));

        Skin skin = setupSkin();

        setupBoardStage();
        setupUIStage(skin);

	    Gdx.input.setInputProcessor(new InputHandler(boardStage.getViewport()));
	}

    /**
     * setup skin
     * TODO 优化 Skin 显示方式
     * @return
     */
    private Skin setupSkin() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        Skin skin = new Skin(Gdx.files.internal("themes/default/uiskin.json"));
        skin.add("default-font", font12);

        return skin;
    }

    private void setupBoardStage() {
        board = Board.create();
        Group group = new Group();
        group.addActor(new BoardActor(board));
        for (Tile tile : board.getTiles()) {
            Actor tileActor = new TileActor(tile);
            group.addActor(tileActor);
        }
        for (Piece piece : board.getPieces()) {
            group.addActor(new PieceActor(piece));
        }
        this.boardStage.addActor(group);
    }

    private void setupUIStage(Skin skin) {
        Table foregroundTable = new Table();
        foregroundTable.setFillParent(true);
        foregroundTable.setDebug(DEBUGGING);

//        // TODO 中文字体支持
//        TextButton button = new TextButton("你好A", skin);
//        backgroundTable.add(button);
//        foregroundTable.add(factionImage).pad(0f, 60f, 0f, 80f);
        foregroundTable.add(new Avatar(board)).pad(0f, 60f, 0f, 80f);

        uiBackgroundImage = new Image(new Texture(Gdx.files.internal("ui-background.jpg")));
        this.uiStage.addActor(uiBackgroundImage);
        this.uiStage.addActor(foregroundTable);
    }

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        boardStage.getViewport().apply();
        boardStage.act(Gdx.graphics.getDeltaTime());
		boardStage.draw();

        uiStage.getViewport().apply();
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
	}

    @Override
    public void resize(int width, int height) {
    }

    @Override
	public void dispose () {
	    boardStage.dispose();
	    uiStage.dispose();
    }
}
