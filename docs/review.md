# RPS Challenge: Code Review Checklist (Java)

This is a general checklist built from the Airport and Takeaway rubrics. When you have the Makers RPS rubric (`docs/review.md` in their repo), paste it into a chat and it can be translated into Java and replace this file.

## Run it
- [ ] `mvn test` passes from a clean checkout
- [ ] `mvn verify` gives coverage above 95% (`target/site/jacoco/index.html`)

## Structure and README
- [ ] Dependencies are in `pom.xml`, no jars committed
- [ ] README explains your approach, and where you got to if unfinished
- [ ] README shows how to run it and how to play, from the user's side

## Tests
- [ ] Tests describe behaviour, with names that say what is proved
- [ ] The computer's move is injected, so no test depends on randomness
- [ ] Never stub or spy on the class under test
- [ ] Unit tests (isolated) are separate from feature tests (real objects)
- [ ] Web routes are tested with `MockMvc`, not by hand
- [ ] Every public method has a test. Private helpers don't need one
- [ ] Exception tests check the message as well as the type

## Code
- [ ] Every class has one clear responsibility
- [ ] Game rules contain no web code, and controllers contain no game rules
- [ ] Dependencies are injected through the constructor
- [ ] `private final` fields, no public setters, no exposed internals
- [ ] No `System.out.println` in the game classes
- [ ] No commented-out code, unused imports or unused fields
- [ ] Adding a new move would be a small change (Open/Closed)
- [ ] Consistent style: `PascalCase`, `camelCase`, `UPPER_SNAKE_CASE`, 4-space indent

## Before you submit
- [ ] Partial work is still committed
- [ ] No secrets in the repo
