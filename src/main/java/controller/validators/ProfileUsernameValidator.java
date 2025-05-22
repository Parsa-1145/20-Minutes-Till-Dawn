package controller.validators;

import model.App;
import model.Result;

public class ProfileUsernameValidator implements Validator<String>{
    @Override
    public Result isValid(String username) {
        if(username.isEmpty()){
            return new Result(false, "should not be empty");
        }
        if(App.getAccountManager().getAccountByUsername(username) != null){
            if(App.getAccountManager().getCurrentAccount() !=
                    App.getAccountManager().getAccountByUsername(username)){
                return new Result(false, "username exists");
            }
        }
        return new Result(true, "");
    }
}
