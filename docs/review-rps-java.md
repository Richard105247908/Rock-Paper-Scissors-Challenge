# RPS Challenge: Code Review Checklist (Java version)

The Makers RPS rubric translated for Java 21, JUnit 5, Mockito and Spring Boot. Save this as `docs/review.md` and tick things off as you go. If you are reviewing someone else, go through it together.

Phase 1 (the game, plain Java) and Phase 2 (the web layer, Spring Boot) are marked where it matters. Items about routes, templates and `MockMvc` only apply once you reach Phase 2.

## Step 0: Checkout and run the tests
- [ ] `mvn test` passes from a clean checkout
- [ ] `mvn verify` gives coverage above 95% (`target/site/jacoco/index.html`)
- [ ] Phase 2: run the app, open it in a browser and play it through
- [ ] Try the game classes by hand in `jshell --class-path target/classes`. How easy is the structure to understand? Did you need any cognitive leaps?

## Step 1: Structure and supporting files

### Instructions in the README
- [ ] Explains your approach, and where you got to if unfinished
- [ ] Shows how to download and run it:

```sh
git clone https://github.com/<username>/rps-challenge.git
cd rps-challenge
./mvnw spring-boot:run
```

Then say which address to open, for example `http://localhost:8080`. The `spring-boot:run` goal only exists once the Spring Boot Maven plugin is in your `pom.xml` (Phase 2). Until then, say `mvn test`.

- [ ] Optional: screenshots. Commit the images to a `docs/images/` folder and link them with a relative path, so you don't need an image host:

```
![Screenshot](docs/images/result.png)
```

### Consistent folder layout
Maven fixes the top-level layout, and Spring Boot adds conventions for templates. The decision that is yours is how to separate the game from the web.

```
rps-challenge/
├── src/main/java/com/example/rps/
│   ├── RpsApplication.java            (the @SpringBootApplication class)
│   ├── game/                          (plain Java, no Spring imports)
│   │   ├── Move.java
│   │   ├── Result.java
│   │   ├── Game.java
│   │   ├── Player.java
│   │   ├── MoveChooser.java
│   │   └── RandomMoveChooser.java
│   └── web/
│       └── GameController.java
├── src/main/resources/
│   ├── templates/                     (new_game.html, win.html, lose.html, draw.html)
│   └── static/                        (CSS, images)
├── src/test/java/com/example/rps/
│   ├── game/                          (MoveTest, GameTest, ...)
│   └── web/                           (GameControllerTest, GameFeatureTest)
├── docs/review.md
├── pom.xml
└── README.md
```

- [ ] Game classes and web classes are in separate packages
- [ ] Nothing in `game/` imports from `org.springframework` or `jakarta.servlet`
- [ ] Templates are in `src/main/resources/templates`, not mixed in with the code
- [ ] Test packages mirror the main packages

### Consistent file naming
Java is stricter than Ruby here, because the compiler enforces it.

- [ ] One public class per file, and the file name matches the class name exactly (`GameController` goes in `GameController.java`)
- [ ] Classes in `PascalCase`, methods and variables in `camelCase`, constants in `UPPER_SNAKE_CASE`, packages in lowercase
- [ ] Treat acronyms as words: `RpsApplication` and `RpsController`, not `RPSApplication` or `RPS_Controller`

### Test setup
Capybara needed `Capybara.app = MyRackApp`. The Java version is the Spring test annotations. Without them, your web tests don't talk to your controller.

- [ ] Web tests use `@WebMvcTest(GameController.class)` with an injected `MockMvc`
- [ ] The random move is replaced in web tests (see Step 2)
- [ ] CI still runs the tests: `./mvnw -B test` in `.github/workflows/ci.yml`. A badge that has stopped being green is a warning, not decoration.

### No commented-out code
- [ ] Delete it before you commit. Git remembers every version.

## Step 2: Tests

### Unit tests: test every outcome of the game logic
There are a fixed number of combinations, so test them all. A parameterised test keeps this short.

```java
@ParameterizedTest
@CsvSource({
    "ROCK, SCISSORS, WIN",   "ROCK, PAPER, LOSE",    "ROCK, ROCK, DRAW",
    "PAPER, ROCK, WIN",      "PAPER, SCISSORS, LOSE", "PAPER, PAPER, DRAW",
    "SCISSORS, PAPER, WIN",  "SCISSORS, ROCK, LOSE",  "SCISSORS, SCISSORS, DRAW"
})
void playerMoveAgainstComputerMove(Move player, Move computer, Result expected) {
    Game game = new Game(() -> computer);       // a fixed MoveChooser, as a lambda

    assertEquals(expected, game.play(player));
}
```

- [ ] All nine outcomes are tested (25 for the Spock and Lizard bonus)
- [ ] `beats` is tested directly in `MoveTest`

### Feature tests: outcomes, not combinations
Your feature tests should not repeat all the move combinations. That is the unit tests' job. They should cover every **outcome** the user can see: a win, a loss and a draw, so the user interface is correct for each.

- [ ] One feature test each for win, lose and draw
- [ ] Feature tests don't re-test the game rules

### Stub out the random behaviour
A feature test must pass every time, so the computer's move has to be fixed. In a Spring test, replace the `MoveChooser` bean with a mock:

```java
@WebMvcTest(GameController.class)
class GameFeatureTest {

    @Autowired MockMvc mockMvc;
    @MockBean MoveChooser moveChooser;    // newer Spring Boot versions use @MockitoBean

    @Test
    void rockAgainstScissorsIsShownAsAWin() throws Exception {
        when(moveChooser.choose()).thenReturn(Move.SCISSORS);

        mockMvc.perform(post("/play").param("move", "ROCK").sessionAttr("name", "Philip"))
                .andExpect(status().isOk())
                .andExpect(view().name("win"))
                .andExpect(content().string(containsString("Philip")));
    }
}
```

Adjust the routes and view names to match your own. The imports come from `org.springframework.test.web.servlet.request.MockMvcRequestBuilders` and `...result.MockMvcResultMatchers`, plus `org.hamcrest.Matchers.containsString`.

- [ ] No feature test depends on real randomness
- [ ] `RandomMoveChooser` itself is tested with a hand-written `Random` stub, as in the airport. (Mockito couldn't mock `Random` on Java 25.)

## Step 3: Application code

### Don't put presentation strings in the business logic
The `Game` class should return a code, not a message.

```java
// Bad: the game decides the wording
public String result() { return "Congratulations - you won!"; }

// Good: the game returns a code, the view decides the wording
public enum Result { WIN, LOSE, DRAW }
public Result result() { return Result.WIN; }
```

The controller then picks a view by result, and each template holds its own wording, so you could change the text or the language without touching the game.

```html
<!-- templates/win.html -->
<h1>Congratulations, <span th:text="${name}">Player</span> - you won!</h1>
```

- [ ] `Game` returns a `Result` enum, never a string for display
- [ ] No `System.out.println` anywhere in the game classes
- [ ] Use `th:text` for user-entered names. Thymeleaf escapes it, which stops a name containing HTML or script from running. Avoid `th:utext` for anything a user typed.

### Don't use if / else-if chains for the rules
Long chains and nested `if` statements are hard to read. Two cleaner options:

```java
// Option 1: a switch expression on the enum (the compiler checks every case is covered)
public boolean beats(Move other) {
    return switch (this) {
        case ROCK     -> other == SCISSORS;
        case PAPER    -> other == ROCK;
        case SCISSORS -> other == PAPER;
    };
}

// Option 2: the rules as data (easier to extend for Spock and Lizard)
private static final Map<Move, Set<Move>> BEATS = Map.of(
    Move.ROCK,     Set.of(Move.SCISSORS),
    Move.PAPER,    Set.of(Move.ROCK),
    Move.SCISSORS, Set.of(Move.PAPER));

public boolean beats(Move attacker, Move defender) {
    return BEATS.get(attacker).contains(defender);
}
```

For the Spock and Lizard bonus, option 2 means adding rows, not rewriting logic. Put the table in its own `Rules` class if you can't reference the enum's own constants from inside it.

- [ ] No chain of `if` / `else if` for who beats what
- [ ] The rules are either a `switch` expression or a data table

### A `Move` type, not strings
- [ ] Moves are an `enum` (or a small class with a `beats` method), never raw strings like `"Rock"`
- [ ] If you need all the moves repeatedly, don't call `Move.values()` in a hot path. It copies the array every time. Keep `private static final List<Move> ALL = List.of(Move.values());`

### Encapsulate the computer in its own class
The game shouldn't care whether it compares two players, a player and a computer, or two computers. In Java, that is an interface:

```java
public interface Contender {
    Move move();
}
```

A human player and a computer both implement it, and `Game` works with `Contender`s. The computer gets its choice from an injected `MoveChooser`, so `RandomMoveChooser` is the only class that touches `Random`.

- [ ] `Game` doesn't know whether it is facing a human or the computer
- [ ] The computer and its randomness live in their own class, behind an interface
- [ ] Bonus 1 (multiplayer) would not need a rewrite of `Game`

### Avoid global and shared mutable state
Java's version of the "global variable" problem is a mutable `static` field, and one more that catches people out in Spring: **a controller is a single object shared by every user.** A field on it is shared by everyone playing.

```java
// Bad: one name for the whole application, so two players overwrite each other
@Controller
class GameController {
    private String playerName;
}

// Good: per-user state lives in the session
@PostMapping("/game")
String createGame(@RequestParam String name, HttpSession session) {
    session.setAttribute("name", name);
    return "redirect:/play";
}
```

- [ ] No mutable `static` fields
- [ ] No per-player data stored in fields on a controller or other singleton
- [ ] Per-player state is kept in the `HttpSession` (or `@SessionAttributes`)

### Define the moves in one place (DRY)
- [ ] The list of moves exists only in the `Move` enum. Other classes refer to it and never copy it
- [ ] Invalid moves are stopped at the edge. `Move.valueOf("lizard")` on a bad string throws `IllegalArgumentException`, and a Spring `@RequestParam Move move` rejects a bad value with a 400. Check what happens when a user sends something unexpected.

### Routes: one purpose each, and the right verb
Each action gets its own route, which is the verb and the path together. Use `GET` for things that don't change state, and `POST` for things that do. Use lowercase, readable paths.

```java
@GetMapping("/game")                // show the form: changes nothing
String newGame() { return "new_game"; }

@PostMapping("/game")               // submit the form: creates state
String createGame(@RequestParam String name, HttpSession session) {
    session.setAttribute("name", name);
    return "redirect:/play";        // post, then redirect, then get
}
```

- [ ] No route that does two jobs
- [ ] `GET` never changes state
- [ ] After a successful `POST`, the controller redirects, so refreshing the page doesn't resubmit the form

### Keep business logic out of the view
- [ ] Templates only display values the controller put in the model (`${name}`, `${playerMove}`, `${computerMove}`)
- [ ] No `if` ladders deciding the outcome inside a template. The controller picks the view from the `Result`, and each view has its own wording
- [ ] Templates don't call game methods that change state

### Thin controllers
The controller's job is to pass the player's move to the game and send the result to a view.

```java
// Bad: the controller contains the rules and the randomness
@PostMapping("/play")
String play(@RequestParam String move, Model model) {
    Move computer = Move.values()[new Random().nextInt(3)];
    String result = "you lose!";
    if (move.equals("ROCK") && computer == Move.SCISSORS) { result = "you win!"; }
    // ... more ifs
    model.addAttribute("result", result);
    return "result";
}

// Good: the controller delegates and returns a view
@Controller
class GameController {

    private final Game game;

    GameController(Game game) {            // constructor injection
        this.game = game;
    }

    @PostMapping("/play")
    String play(@RequestParam Move move, HttpSession session, Model model) {
        Result result = game.play(move);
        model.addAttribute("name", session.getAttribute("name"));
        return result.name().toLowerCase();    // "win", "lose" or "draw"
    }
}
```

- [ ] No game rules and no `new Random()` inside a controller
- [ ] Dependencies arrive through the constructor. Don't use `@Autowired` on fields
- [ ] Anything you want to test lives in `game/`, where it can be tested without a server

### Style and tidiness
- [ ] Consistent formatting (Ctrl+Alt+L in IntelliJ), four-space indent
- [ ] No unused imports, fields or methods
- [ ] `private final` fields where possible, no public setters

## Before you submit
- [ ] Tests green, coverage above 95%
- [ ] README tells a stranger how to run and play it
- [ ] Every class has one clear responsibility
- [ ] No game logic in controllers or templates, and no presentation text in the game
- [ ] Partial work is still committed
