package self.mengqi.games.display;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mengqi on 2017/10/2.
 */
public class FixedViewport extends Viewport {
    private int worldX;
    private int worldY;
    private int worldWidth;
    private int worldHeight;

    public FixedViewport(int worldX, int worldY, int worldWidth, int worldHeight) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        setWorldSize(worldWidth, worldHeight);
        setCamera(new OrthographicCamera());
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        setScreenBounds(worldX, worldY, worldWidth, worldHeight);
        apply(centerCamera);
    }
}
