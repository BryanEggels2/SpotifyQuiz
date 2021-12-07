package com.spotifyquiz.SpotifyQuiz.services.interfaces;

import com.spotifyquiz.SpotifyQuiz.models.UserDetails;

import java.net.URI;

public interface LoginInterface {
    URI login(String scope);
    UserDetails callback(String code);

}
