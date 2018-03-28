package kz.guestbook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController extends DefaultController {

    @GetMapping("/admin")
    public String admin() {
        return "/admin/admin";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }
}
