package com.challenge_java.challenge_java.exceptionscustom;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@EnableWebMvc
class DefaultExceptionHandler implements HandlerExceptionResolver {


    @Override
    public @ResponseBody
    ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        if (e instanceof ConstraintViolationException) {
            modelAndView.addObject("timestamp", new Date());
            modelAndView.addObject("code", 400);
            modelAndView.addObject("msg", "Request parameter verification failed:" + e.getMessage());
            return modelAndView;
        }

        String msg = "";
        if(e.getMessage().equals(null)){
            msg = "Error no definido";
        } else {
            msg = e.getMessage();
        }

        HashMap<String, Object> resultError = new HashMap<>();

        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject("code", 400);
        modelAndView.addObject("msg", msg);


        return modelAndView;
    }
}
