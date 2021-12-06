package com.spotifyquiz.SpotifyQuiz.services.interfaces;

import java.net.URI;

public interface LoginInterface {
    URI login(String scope);
    boolean callback(String code);

}
