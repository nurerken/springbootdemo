package kz.guestbook.components.User.forms;

import kz.guestbook.components.User.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserForm {

    @ValidEmail
    private String email;

    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Length(min = 8, message = "Password must contain at least 8 symbols")
    private String password;

    @NotNull(message = "Password confirm is required")
    @NotEmpty(message = "Password confirm is required")
    private String passwordConfirm;

    public User getUser(){
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setActive(true);
        return user;
    }
}
