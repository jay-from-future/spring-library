package ru.otus.springlibrary.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidObjectIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidObjectId {

    String message() default "Invalid ObjectId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
