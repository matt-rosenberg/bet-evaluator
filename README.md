# Bet Evaluator

A Java tool that compares sportsbook betting lines against a set of predictions and ranks the bets that offer the most value.

Built as a software engineering exercise for Data Structures (COM 1320) at Yeshiva University.

## How it works

1. **Load predictions.** Predictions arrive as JSON, each one giving the expected odds for a team on a particular bet.
2. **Load sportsbook lines.** Bets from different sportsbooks also arrive as JSON. If the same bet appears more than once, the evaluator keeps the best available odds.
3. **Grade each bet.** When a bet has a matching prediction, it gets a grade measuring how far the sportsbook's odds are from the predicted odds. Bets are re-graded automatically when new predictions or better lines come in.
4. **Rank.** Graded bets are kept in priority queues, so the evaluator can return the top bets overall, or filtered by team, by sportsbook, or by bet type.

## Supported bet types

- **Moneyline:** which team wins
- **Spread:** whether a team covers a point spread
- **Over/Under:** whether a total goes over or under a number

Each bet type implements a shared `Prop` interface, so new bet types can be added without changing the evaluator.

## Design

The [`docs`](docs) folder contains the planning documents written before the code:

- **User-Centered Design:** who the users are and what they need from the tool
- **Data Model:** the classes and how they relate

## Project structure

```
src/main/java/edu/yu/cs/com1320/se_practice/
  BetEvaluator.java   loads data, grades bets, and answers top-bet queries
  Bet.java            a sportsbook line, with its odds and grade
  Prediction.java     the predicted odds for a team and bet
  Prop.java           interface shared by all bet types
  Moneyline.java, Spread.java, OverUnder.java
  Team.java, Sportsbook.java
src/test/java/...     JUnit tests
```

## Running the tests

Requirements: Java 17 and Maven.

```
mvn test
```

## Tech stack

Java · Maven · JUnit 5 · Gson
