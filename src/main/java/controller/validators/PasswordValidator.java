package controller.validators;

import model.Result;

public class PasswordValidator implements Validator<String> {
    @Override
    public Result isValid(String password) {
        if(password.length() < 8){
            return new Result(false, "should be at least 8 characters long");
        }

        if(!password.contains("abcdefghijklmnopqrstuvwxyz")){
            return new Result(false, "should be at least 8 characters long");

        }

        return null;
    }
}
