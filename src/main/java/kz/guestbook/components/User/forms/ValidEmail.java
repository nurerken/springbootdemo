package kz.guestbook.components.User.forms;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EmailValidator.class })
public @interface ValidEmail {
    String message() default "Email is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}