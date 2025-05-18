package model;

import com.badlogic.gdx.Input;

public class AppSettings {
    public Audio audioSettings       = new Audio();
    public KeyBindings keyBindings   = new KeyBindings();
    public GamePlay gamePlaySettings = new GamePlay();
    public Graphics graphicSettings  = new Graphics();
    public Debug debugSettings       = new Debug();

    public static class Audio{
        public int uiVolume        = 100;
        public int gameVolume      = 100;
        public int musicVolume     = 100;
        public int masterVolume    = 100;
    }
    public static class KeyBindings{
        public int shoot       = Input.Buttons.LEFT;
        public int reload      = Input.Keys.R;
        public int moveUp      = Input.Keys.W;
        public int moveDown    = Input.Keys.S;
        public int moveLeft    = Input.Keys.A;
        public int moveRight   = Input.Keys.D;
    }
    public static class GamePlay{
        public boolean autoReload = true;
    }
    public static class Graphics{
        public boolean blackAndWhite = false;
    }
    public static class Debug{
        public boolean debug = false;
    }
}
