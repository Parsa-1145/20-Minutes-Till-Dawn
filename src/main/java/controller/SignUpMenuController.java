package controller;

import com.badlogic.gdx.Screen;
import model.App;

public class SignUpMenuController {
    public void changeScreen(Screen screen){
        App.getInstance().setScreen(screen);
    }
}
