package kz.guestbook.controllers;

import kz.guestbook.components.User.forms.CreateUserForm;
import kz.guestbook.components.User.service.UserService;
import kz.guestbook.components.User.model.User;
import kz.guestbook.components.User.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController extends DefaultController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(CreateUserForm createUserForm, Model model){
		return "registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid CreateUserForm createUserForm, BindingResult bindingResult, Model model) {

		boolean userExists = userService.getUserByEmail(createUserForm.getEmail()) != null;
		if (userExists) {
			bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided.");
		}

		if(!createUserForm.getPassword().equals(createUserForm.getPasswordConfirm())) {
			bindingResult.rejectValue("password", "error.password", "Passwords don't match.");
		}

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		User user = createUserForm.getUser();
		userService.saveUser(user);
		model.addAttribute("successMessage", "User has been registered successfully");
		model.addAttribute("user", new User());
		//securityService.autologin(user.getEmail(), user.getPassword());

		return "registration";
	}

	/*
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}*/


}
