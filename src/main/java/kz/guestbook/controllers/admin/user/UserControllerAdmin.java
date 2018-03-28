package kz.guestbook.controllers.admin.user;

import kz.guestbook.components.User.forms.CreateUserForm;
import kz.guestbook.components.User.model.User;
import kz.guestbook.components.User.service.UserService;
import kz.guestbook.controllers.admin.DefaultAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class UserControllerAdmin extends DefaultAdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model, UserSearchForm userSearchForm) throws IOException{

        List users = userService.getUserBySearchString(userSearchForm.getSearchString());
        model.addAttribute("users", users);
        model.addAttribute("userSearchForm", userSearchForm);

        return "admin/user/users";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        if(user == null){
            return "error/404";
        }

        UserEditForm userEditForm = new UserEditForm();
        userEditForm.setId(user.getId());
        userEditForm.setEmail(user.getEmail());
        userEditForm.setFirstName(user.getFirstName());
        userEditForm.setLastName(user.getLastName());

        model.addAttribute("userEditForm", userEditForm);
        return "admin/user/user";
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String saveuser(UserEditForm userEditForm, Model model) {
        User user = userService.getUserById(userEditForm.getId());
        if(user == null){
            return "error/404";
        }

        user.setEmail(userEditForm.getEmail());
        user.setFirstName(userEditForm.getFirstName());
        user.setLastName(userEditForm.getLastName());

        userService.saveUser(user);

        model.addAttribute("message", "changes were saved");
        return getUser(user.getId(), model);
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public String newUserPage(CreateUserForm createUserForm) {
        return "admin/user/newuser";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public String createNewUser(@Valid CreateUserForm createUserForm, BindingResult bindingResult, Model model) throws IOException{

        User userExists = userService.getUserByEmail(createUserForm.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided.");
        }

        if(!createUserForm.getPassword().equals(createUserForm.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "error.password", "Passwords don't match.");
        }

        if (bindingResult.hasErrors()) {
            return "admin/user/newuser";
        }
        User user = createUserForm.getUser();
        userService.saveUser(user);
        model.addAttribute("message", "User has been registered successfully");

        return getUsers(model, new UserSearchForm());
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long id, Model model) throws IOException{
        User user = userService.getUserById(id);
        if(user == null){

        }
        userService.deleteUser(id);

        model.addAttribute("message", "user deleted successfully");
        return getUsers(model, new UserSearchForm());
    }
}