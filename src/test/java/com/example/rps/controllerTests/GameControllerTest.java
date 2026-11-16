package com.example.rps.controllerTests;

import com.example.rps.Game;
import com.example.rps.controller.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    Game game;

    @Test
    void newGameFormIsShown() throws Exception {
        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_game"));

    }

    @Test
    void startsGameAndRedirectsToPlay() throws Exception{
        mockMvc.perform(post("/game")
                .param("name","Philip"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/play"))
                .andExpect(request().sessionAttribute("name", "Philip"));
    }

    @Test
    void playPageGreetsThePlayerByName() throws Exception {
        mockMvc.perform(get("/play").sessionAttr("name","Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name("play"))
                .andExpect(model().attribute("name", "Philip"));
    }

    @Test
    void takesPlayersName() throws Exception {
        mockMvc
    }
}
