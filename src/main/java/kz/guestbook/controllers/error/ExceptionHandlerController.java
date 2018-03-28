package kz.guestbook.controllers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex) {
        return "error/internal_error";
    }

    // not working. need to enable spring mvc
    /*@ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String defaultErrorHandler(HttpServletRequest request, Exception ex) {
        return "error/404";
    }*/
}