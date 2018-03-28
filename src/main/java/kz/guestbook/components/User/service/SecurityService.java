package kz.guestbook.components.User.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}