package ru.otus.springlibrary.validation;

import org.bson.types.ObjectId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidObjectIdValidator implements ConstraintValidator<ValidObjectId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ObjectId.isValid(value);
    }
}
