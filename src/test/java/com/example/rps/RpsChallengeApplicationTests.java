package com.example.rps;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RpsChallengeApplicationTests {

	@Autowired Game game;

	@Test
	void theApplicationContextStartsAndBuildsAGame() {
		assertNotNull(game);
	}

}
