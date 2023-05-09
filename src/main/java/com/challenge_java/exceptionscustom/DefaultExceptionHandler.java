package com.challenge_java.exceptionscustom;

import com.challenge_java.response.MessageResponse;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@ControllerAdvice
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
class DefaultExceptionHandler implements HandlerExceptionResolver {


    @Override
    public @ResponseBody
    ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        if (e instanceof MethodArgumentNotValidException) {

            List<String> mapError = new ArrayList<>();
            ((MethodArgumentNotValidException) e).getAllErrors().forEach(objectError -> {
                mapError.add(objectError.getDefaultMessage());
            });

            modelAndView.addObject("timestamp", new Date().toString());
            modelAndView.addObject("code", 400);
            modelAndView.addObject("msg-error", mapError);
            return modelAndView;
        }

        String msg = "";
        if(e.getMessage() == null){
            msg = "Error no definido";
        } else {
            msg = e.getMessage();
        }


        HashMap<String, Object> resultError = new HashMap<>();

        modelAndView.addObject("timestamp", new Date().toString());
        modelAndView.addObject("code", 400);
        modelAndView.addObject("msg-error", msg);
        return modelAndView;
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    @ResponseBody
    protected ModelAndView HandlerExceptionResolver(
            BindException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
            throws IOException {

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

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));

    }
}
