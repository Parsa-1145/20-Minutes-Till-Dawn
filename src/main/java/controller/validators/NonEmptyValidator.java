package controller.validators;

import model.Result;

public class NonEmptyValidator implements Validator<String>{
    @Override
    public Result isValid(String object) {
        if(object.isEmpty()) return new Result(false, "should not be empty");
        return new Result(true, "");
    }
}
