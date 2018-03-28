package kz.guestbook.controllers;

import kz.guestbook.components.User.model.User;
import kz.guestbook.components.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class DefaultController {

    @Autowired
    protected UserService userService;

    protected User getAuthenticatedUser(){
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            String userName = auth.getName();
            user = userService.getUserByEmail(userName);
        }
        return user;
    }
}
