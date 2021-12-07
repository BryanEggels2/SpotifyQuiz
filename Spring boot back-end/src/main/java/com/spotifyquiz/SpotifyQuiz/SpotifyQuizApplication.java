package com.spotifyquiz.SpotifyQuiz;

import com.spotifyquiz.SpotifyQuiz.services.implementations.Apikeys;
import com.spotifyquiz.SpotifyQuiz.services.implementations.PropertiesReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class SpotifyQuizApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpotifyQuizApplication.class, args);
	}

}
