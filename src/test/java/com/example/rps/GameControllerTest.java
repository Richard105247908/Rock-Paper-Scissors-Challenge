package com.example.rps;

import com.example.rps.controller.GameController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.naming.Name;

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

}
