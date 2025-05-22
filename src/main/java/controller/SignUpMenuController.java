package controller;

import com.badlogic.gdx.Screen;
import model.Account;
import model.AccountManager;
import model.App;
import model.Result;

public class SignUpMenuController {
    public void changeScreen(Screen screen){
        App.getInstance().setScreen(screen);
    }

    public Result submitSignup(String username, String password){
        Account account = new Account(username, password);
        App.getAccountManager().addAccount(account);

        return new Result(true, "account created successfully");
    }
}
