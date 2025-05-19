package controller.validators;

import model.Result;

public interface Validator <T>{
    Result isValid(T object);
}
