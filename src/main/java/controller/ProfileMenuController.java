package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import model.App;

public class ProfileMenuController {
    public void changeScreen(Screen screen){
        App.getInstance().setScreen(screen);
    }
}
