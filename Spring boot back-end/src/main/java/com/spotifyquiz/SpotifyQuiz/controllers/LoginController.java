package com.spotifyquiz.SpotifyQuiz.controllers;

import com.spotifyquiz.SpotifyQuiz.models.UserDetails;
import com.spotifyquiz.SpotifyQuiz.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @GetMapping(value = "/callback")
    public UserDetails callback(@RequestParam Map<String, String> parameters){
        String code = parameters.get("code");
        UserDetails details = null;
        if (code != null){
            details = loginRepository.callback(parameters.get("code"));
            System.out.println(parameters.get("code"));
        }
        else{
            System.out.println("Take a look at this error:");
            System.out.println(parameters.get("error"));
        }
        System.out.println(parameters.get("state"));
        return details;

    }
    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam Map<String, String> parameters){
        URI uri = loginRepository.login(parameters.get("scope"));
        return new ModelAndView("redirect:" + uri.toString());
    }
}
