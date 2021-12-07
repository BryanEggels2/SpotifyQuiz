package com.spotifyquiz.SpotifyQuiz.repositories;

import com.spotifyquiz.SpotifyQuiz.models.UserDetails;
import com.spotifyquiz.SpotifyQuiz.services.implementations.LoginService;
import com.spotifyquiz.SpotifyQuiz.services.interfaces.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component("loginRepository")
public class LoginRepository implements LoginInterface {

    @Autowired
    LoginInterface LoginService = new LoginService();

    @Override
    public URI login(String scope) {
        return LoginService.login(scope);
    }

    @Override
    public UserDetails callback(String code) {
        return LoginService.callback(code);
    }
}
