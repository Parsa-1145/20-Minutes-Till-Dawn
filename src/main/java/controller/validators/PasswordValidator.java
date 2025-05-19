package controller.validators;

import model.Result;

public class PasswordValidator implements Validator<String> {
    @Override
    public Result isValid(String password) {
        if(password.isEmpty()){
            return new Result(false, "should not be empty");
        }
        if(password.length() < 8){
            return new Result(false, "should be at least 8 characters long");
        }

        if(!password.matches(".*[a-z].*")){
            return new Result(false, "should contain a lowercase");
        }

        if(!password.matches(".*[A-Z].*")){
            return new Result(false, "should contain a uppercase");
        }

        if(!password.matches(".*[0-9].*")){
            return new Result(false, "should contain a digit");
        }

        if(!password.matches(".*[_()*&%$#@].*")){
            return new Result(false, "should contain a special character");
        }

        else return new Result(true, "");
    }
}
