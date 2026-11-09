# RPS Challenge (Java)

![CI](https://github.com/YOUR-USERNAME/rps-challenge/actions/workflows/ci.yml/badge.svg)

A Java 21 / JUnit 5 port of the Makers [rps-challenge](https://github.com/makersacademy/rps-challenge), built test-first. A Rock, Paper, Scissors game you can play in the browser.

## Stack
- Java 21, Maven
- JUnit 5, Mockito
- JaCoCo (coverage), GitHub Actions CI
- Phase 2 (after IBM Course 7): Spring Boot, Thymeleaf

## What it does
_Write 2-3 sentences in your own words once it works._

## User stories
```
As a marketeer
So that I can see my name in lights
I would like to register my name before playing an online game

As a marketeer
So that I can enjoy myself away from the daily grind
I would like to be able to play rock/paper/scissors
```

Hints from the challenge:
- The marketeer enters their name before the game
- They are shown the choices (rock, paper, scissors) and pick one
- The game picks a random option
- A winner is declared

Basic rules: rock beats scissors, scissors beats paper, paper beats rock.

Bonus 1: multiplayer, two players against each other.
Bonus 2: Rock, Paper, Scissors, Spock, Lizard.

## Two phases
This challenge is a **web application**, but you shouldn't start with the web. Build the game itself first, as plain Java that you can test without a server. The web layer is thin and comes second.

**Phase 1: the game (plain Java, do this first)**
Everything the game decides: who beats what, who won, and where the computer's choice comes from. No Spring needed.

**Phase 2: the web layer (week 3, after IBM Course 7)**
Pages, forms and routes that call the game. Generate a Spring Boot skeleton at start.spring.io (Maven, Java 21, dependencies Spring Web and Thymeleaf), then copy its parent and dependencies into this `pom.xml` and add a `@SpringBootApplication` class.

## Java translation notes
| Ruby / Sinatra / RSpec | Java |
|---|---|
| Sinatra routes (`get '/'`, `post '/name'`) | Spring `@Controller` with `@GetMapping` and `@PostMapping` |
| ERB templates | Thymeleaf templates |
| `session[:name]` | `HttpSession` (or `@SessionAttributes`) |
| Capybara feature tests (`visit`, `fill_in`, `click_button`) | `MockMvc` (`@WebMvcTest`): `perform(get("/"))`, `andExpect(...)` |
| `double` / stubbing `rand` | Inject a `MoveChooser`. Tests use a fixed one |
| SimpleCov | JaCoCo |

## Phase 1: suggested test order
1. Rock beats scissors (written for you, RED)
2. Scissors beats paper
3. Paper beats rock
4. A move does not beat itself, and the loser does not beat the winner
5. A player has a name, and a blank name is rejected
6. A game compares the player's move with the computer's (inject a fixed `MoveChooser`) and returns win, lose or draw
7. A draw is reported as a draw
8. All nine combinations in one parameterised test (`@ParameterizedTest` with `@CsvSource`)
9. Feature test: play a full game with real objects and a fixed `MoveChooser`
10. `RandomMoveChooser`, tested with a hand-written `Random` stub, as in the airport

Keep presentation out of the game classes: return a result (an enum), and let the web layer turn it into "Alice wins!".

## Phase 2: web layer, rough order
1. `GET /` shows a form asking for a name
2. `POST /name` stores the name in the session and redirects to `/play` (post, then redirect, then get)
3. `GET /play` shows the three choices
4. `POST /play` plays a round and redirects to `/result`
5. `GET /result` shows the player's move, the computer's move and the outcome
6. Test every route with `MockMvc`, and keep controllers thin: no game rules inside them

## Design notes to think about
- **Rules as data, not code.** For bonus 2 (Spock and Lizard), a rules table ("rock beats scissors and lizard") is easier to extend than a long chain of `if` statements. Try to get there by refactoring with the tests green.
- **Open/Closed.** Adding a move should not mean editing five places.

## Running it
```bash
mvn test      # run the tests
mvn verify    # tests + coverage report at target/site/jacoco/index.html
```

## Progress (update as you go)
- [ ] Rules: rock, paper, scissors
- [ ] Player name
- [ ] Game decides win, lose or draw
- [ ] Computer move is injected (`MoveChooser`)
- [ ] Feature test of a full game
- [ ] Coverage above 95%
- [ ] Web: name form
- [ ] Web: choose a move and see the result
- [ ] Web routes tested with `MockMvc`
- [ ] Bonus 1: multiplayer
- [ ] Bonus 2: Spock and Lizard

## Architecture notes
_Fill in as you build: why the classes are split the way they are, how the computer's move is injected, and what you would improve._
