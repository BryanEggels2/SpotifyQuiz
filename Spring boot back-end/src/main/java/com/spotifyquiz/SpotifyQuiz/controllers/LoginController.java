package com.spotifyquiz.SpotifyQuiz.controllers;

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
    public boolean callback(@RequestParam Map<String, String> parameters){
        String code = parameters.get("code");
        if (code != null){
            loginRepository.callback(parameters.get("code"));
            System.out.println(parameters.get("code"));
        }
        else{
            System.out.println("Take a look at this error:");
            System.out.println(parameters.get("error"));
        }
        System.out.println(parameters.get("state"));
        return true;
        //return new ModelAndView("redirect:" + "http://localhost:8888/callback?code=" + parameters.get("code") + "&state=" + parameters.get("state") );
    }

    @GetMapping(value = "/login")
    public URI login(@RequestParam Map<String, String> parameters){
        return loginRepository.login(parameters.get("scope"));
    }

}
