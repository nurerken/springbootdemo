package kz.guestbook.controllers.admin;

import kz.guestbook.components.Role.model.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@RequestMapping(value = "/admin/")
@RolesAllowed(UserRole.roleAdmin)
public abstract class DefaultAdminController {

}
