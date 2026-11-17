package com.example.rps;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameEndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MoveChooser moveChooser;

    @ParameterizedTest
    @CsvSource({
            "SCISSORS, win",
            "PAPER, lose",
            "ROCK, draw"
    })
    void eachOutcomeShowsItsOwnPageWithBothMoves(Move computerMove, String expectedView) throws Exception {
        when(moveChooser.choose()).thenReturn(computerMove);

        mockMvc.perform(post("/play")
                        .param("move", "ROCK")
                        .sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedView))
                .andExpect(content().string(containsString("Philip")))
                .andExpect(content().string(containsString("You played <span>ROCK</span>")))
                .andExpect(content().string(
                        containsString("the computer played <span>" + computerMove + "</span>")));
    }
}