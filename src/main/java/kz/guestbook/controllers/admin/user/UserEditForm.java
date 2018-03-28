package kz.guestbook.controllers.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditForm {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}