package self.mengqi.games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import self.mengqi.games.ChineseChess;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 573;
		config.height = 640;
		config.resizable = false;
		new LwjglApplication(new ChineseChess(), config);
	}
}
