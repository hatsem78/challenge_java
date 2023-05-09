package com.challenge_java.challenge_java.exceptionscustom;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
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
        if(e.getMessage() == null){
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

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    @ResponseBody
    protected ModelAndView HandlerExceptionResolver(
            BindException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
            throws IOException {
        System.out.println("In CustomExceptionHandlerResolver");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        response.sendError(400, "error");
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        if (ex instanceof HandlerExceptionResolver) {
            modelAndView.addObject("timestamp", new Date());
            modelAndView.addObject("code", 400);
            modelAndView.addObject("msg", "Request parameter verification failed:" + ex.getMessage());
            return modelAndView;
        }

        String msg = "";
        if(ex.getMessage() == null){
            msg = "Error no definido";
        } else {
            msg = ex.getMessage();
        }

        HashMap<String, Object> resultError = new HashMap<>();

        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject("code", 400);
        modelAndView.addObject("msg", msg);


        return modelAndView;

    }
}
