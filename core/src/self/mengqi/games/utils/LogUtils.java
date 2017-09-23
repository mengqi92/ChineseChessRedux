package self.mengqi.games.utils;

import com.badlogic.gdx.Gdx;
import self.mengqi.games.common.HumanFriendly;

/**
 * Created by Mengqi on 2017/9/23.
 * utilities for logging
 */
public class LogUtils {
    public static void logging(String tag, String field, String object) {
        Gdx.app.debug(tag, String.format("%s: %s", field, object));
    }

    public static void logging(String tag, HumanFriendly field, HumanFriendly object) {
        Gdx.app.debug(tag, String.format("%s: %s", field.toReadableString(), object.toReadableString()));
    }

    public static void logging(String tag, HumanFriendly field, String log) {
        Gdx.app.debug(tag, String.format("%s: %s", field.toReadableString(), log));
    }

    public static void logging(String tag, String field, HumanFriendly object) {
        Gdx.app.debug(tag, String.format("%s: %s", field, object.toReadableString()));
    }
}
