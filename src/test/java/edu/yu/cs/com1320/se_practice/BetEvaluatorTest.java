package edu.yu.cs.com1320.se_practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BetEvaluatorTest {

    @Test
    public void topXBetsThrowsIfEmptyTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBets(1);
        });
    }

    @Test
    public void topXBetsThrowsIfNumberIsZeroTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBets(0);
        });
    }

    @Test
    public void topXBetsByTeamThrowsIfNoBetsTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsByTeam(1, "Steelers");
        });
    }

    @Test
    public void topXBetsByTeamThrowsIfNumberIsZeroTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsByTeam(0, "Steelers");
        });
    }

    @Test
    public void topXBetsBySportsbookThrowsIfNoBetsTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsBySportsbook(1, "FanDuel");
        });
    }

    @Test
    public void topXBetsBySportsbookThrowsIfNumberIsZeroTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsBySportsbook(0, "FanDuel");
        });
    }

    @Test
    public void topXBetsByPropThrowsIfNoBetsTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsByProp(1, "moneyline");
        });
    }

    @Test
    public void topXBetsByPropThrowsIfNumberIsZeroTest() {
        BetEvaluator evaluator = new BetEvaluator();

        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.topXBetsByProp(0, "moneyline");
        });
    }
}