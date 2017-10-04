package self.mengqi.games.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import self.mengqi.games.enums.PieceEnums;
import self.mengqi.games.models.Board;
import self.mengqi.games.piece.Piece;

import java.util.HashMap;
import java.util.Map;

import static self.mengqi.games.enums.PieceEnums.Faction;
import static self.mengqi.games.enums.PieceEnums.Type;

/**
 * Created by Mengqi on 2017/10/3.
 * UI 界面的头像展示
 */
public class Avatar extends Image {
    private Board board;
    private static Drawable redFaction;
    private static Drawable blackFaction;
    private Drawable drawable;
    private Map<Faction, Map<Type, Drawable>> pieceDrawables;

    public Avatar(Board board) {
        redFaction = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("factions/red.jpg"))));
        blackFaction = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("factions/black.jpg"))));

        pieceDrawables = new HashMap<>();
        for (Faction faction : Faction.values()) {
            Map<PieceEnums.Type, Drawable> drawableMap = new HashMap<>();
            for (Type type : Type.values()) {
                String filename = String.format("factions/%s/%s.jpg", faction, type);
                Gdx.app.debug("avatar", filename);
                drawableMap.put(type, new TextureRegionDrawable(new TextureRegion(
                        new Texture(Gdx.files.internal(filename)))));
            }
            pieceDrawables.put(faction, drawableMap);
        }

        setScaling(Scaling.fillX);

        this.board = board;

        drawable = redFaction;
    }

    @Override
    public void act(float delta) {
        if (board.isJiangJuning()) {
            Piece jiangJunPiece = board.getJiangJunPiece();
            drawable = pieceDrawables.get(jiangJunPiece.getFaction()).get(jiangJunPiece.getType());
//            drawable = pieceDrawables.get(board.get()).get(board.currentPieceType());
            setDrawable(drawable);
        } else {
            switch (board.getCurrentFaction()) {
                case Red:
                    drawable = redFaction;
                    setDrawable(drawable);
                    break;
                case Black:
                    drawable = blackFaction;
                    setDrawable(drawable);
                    break;
            }
        }
        super.act(delta);
    }
}
