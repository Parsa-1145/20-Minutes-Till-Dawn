package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import model.App;

public class MainMenuController{
    public void changeScreen(Screen screen){
        App.getInstance().setScreen(screen);
    }
    public void exit(){
        Gdx.app.exit();
    }
    public void logout(){
        App.getAccountManager().setCurrentAccount(null);
    }
}
