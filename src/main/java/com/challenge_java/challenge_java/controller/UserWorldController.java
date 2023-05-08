package com.challenge_java.challenge_java.controller;


import com.challenge_java.challenge_java.exceptionscustom.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserWorldController {

    @GetMapping(value = {"/index", "/", "/home"})
    public String index() throws EntityNotFoundException {
        return "Welcome to the technical challenge";
    }
}
