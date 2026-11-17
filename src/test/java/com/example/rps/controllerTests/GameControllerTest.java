package com.example.rps.controllerTests;

import com.example.rps.Game;
import com.example.rps.Move;
import com.example.rps.Result;
import com.example.rps.Round;
import com.example.rps.controller.GameController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
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
    public void newGameFormIsShown() throws Exception {
        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_game"));

    }

    @Test
    public void startsGameAndRedirectsToPlay() throws Exception {
        mockMvc.perform(post("/game")
                        .param("name", "Philip"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/play"))
                .andExpect(request().sessionAttribute("name", "Philip"));
    }


    @Test
    public void playPageGreetsThePlayerByName() throws Exception {
        mockMvc.perform(get("/play").sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name("play"))
                .andExpect(model().attribute("name", "Philip"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void blankNameShowsTheFormAgainWithAnError(String blankName) throws Exception {
        mockMvc.perform(post("/game").param("name", blankName))
                .andExpect(status().isOk())
                .andExpect(view().name("new_game"))
                .andExpect(model().attributeExists("error"))
                .andExpect(request().sessionAttributeDoesNotExist("name"));
    }

    @Test
    public void redirectsToGameWhenNoNameInSession() throws Exception {
        mockMvc.perform(get("/play"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
    }

    //5
    @Test
    public void playPageOffersTheThreeMoves() throws Exception {
        mockMvc.perform(get("/play").sessionAttr("name", "Philip"))
                .andExpect(model().attribute("moves", Move.values()));

    }

    //6e
    @Test
    public void rockThatBeatsTheComputerShowsTheWinPage() throws Exception {
        when(game.play(Move.ROCK)).thenReturn(new Round(Move.ROCK, Move.SCISSORS, Result.WIN));
        mockMvc.perform(post("/play")
                        .param("move", "ROCK")
                        .sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name("win"))
                .andExpect(model().attribute("name", "Philip"));
//                .andExpect(model().attribute("playerMove", Move.ROCK))
//                .andExpect(model().attribute("computerMove", Move.SCISSORS));
    }


    @ParameterizedTest
    @CsvSource({"WIN, win", "LOSE, lose", "DRAW, draw"})
    public void eachResultShowsItsOwnPage(Result result, String expectedView) throws Exception {
        when(game.play(Move.ROCK)).thenReturn(new Round(Move.ROCK, Move.SCISSORS, result));

        mockMvc.perform(post("/play")
                        .param("move", "ROCK")
                        .sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedView))
                .andExpect(model().attribute("name", "Philip"));


    }

    @Test
    public void postingAMoveWithoutANameRedirectsToTheNameForm() throws Exception {
        mockMvc.perform(post("/play").param("move", "ROCK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
    }

    @Test
    public void resultPageShowsBothMoves() throws Exception {
        when(game.play(Move.ROCK)).thenReturn(new Round(Move.ROCK, Move.SCISSORS, Result.WIN));
        mockMvc.perform(post("/play")
                        .param("move", "ROCK")
                        .sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name("win"))
                .andExpect(model().attribute("playerMove", Move.ROCK))
                .andExpect(model().attribute("computerMove", Move.SCISSORS));
    }
    @Test
    public void unkonownMoveReturnBadRequest() throws Exception{
        mockMvc.perform(post("/play")
                        .param("move", "LIZARD")
                        .sessionAttr("name", "Philip"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void missingMoveReturnsBadRequest



}
