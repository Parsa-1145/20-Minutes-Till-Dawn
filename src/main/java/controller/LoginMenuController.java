package controller;

import com.badlogic.gdx.Screen;
import model.Account;
import model.App;
import model.Result;

public class LoginMenuController {
    public void changeScreen(Screen screen){
        App.getInstance().setScreen(screen);
    }

    public Result submitLogin(String username, String password){
        Account account = App.getAccountManager().getAccountByUsername(username);
        if(account == null){
            return new Result(false, "username not found");
        }

        if(!account.getPassword().equals(password)){
            return new Result(false, "incorrect password");
        }

        App.getAccountManager().setCurrentAccount(account);
        return new Result(true, "logged in as " + username);
    }
}
