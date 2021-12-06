package com.spotifyquiz.SpotifyQuiz;

import com.spotifyquiz.SpotifyQuiz.services.implementations.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpotifyQuizApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void requestAuthorization(){
		LoginService test = new LoginService();
		System.out.println(test.login("user-read-private user-read-email").toString());

	}
}
