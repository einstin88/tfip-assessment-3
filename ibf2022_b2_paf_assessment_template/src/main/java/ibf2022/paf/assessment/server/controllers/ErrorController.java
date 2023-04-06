package ibf2022.paf.assessment.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.Exception.TaskCreationException;
import ibf2022.paf.assessment.server.Exception.UserCreationException;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler({ UserCreationException.class, TaskCreationException.class })
    public ModelAndView handleErrors(RuntimeException err) {
        return new ModelAndView("error.html",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
