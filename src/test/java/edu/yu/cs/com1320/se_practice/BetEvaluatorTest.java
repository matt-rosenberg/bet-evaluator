package edu.yu.cs.com1320.se_practice;

import org.junit.jupiter.api.Test;

import java.util.List;

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

    // ---- Helpers for building JSON ----

    private static final String ML = "{\"type\": \"moneyline\"}";
    private static final String SPREAD = "{\"type\": \"spread\", \"spread\": -3.5}";

    private String bet(String team, String prop, String book, double odds) {
        return String.format("{\"prop\": %s, \"team\": {\"team\": \"%s\"}, \"sportsbook\": {\"book\": \"%s\"}, \"odds\": %s}",
                prop, team, book, odds);
    }

    private String prediction(String team, String prop, double probability) {
        return String.format("{\"prop\": %s, \"team\": {\"team\": \"%s\"}, \"probability\": %s}", prop, team, probability);
    }

    private String list(String... items) {
        return "[" + String.join(",", items) + "]";
    }

    // Steelers grade 0.2, Jets 0.1, Ravens -0.1
    private BetEvaluator evaluatorWithThreeBets() {
        BetEvaluator evaluator = new BetEvaluator();
        evaluator.createPredictions(list(
                prediction("Steelers", ML, 0.6),
                prediction("Ravens", ML, 0.5),
                prediction("Jets", SPREAD, 0.55)));
        evaluator.createBets(list(
                bet("Steelers", ML, "FanDuel", 2.0),
                bet("Ravens", ML, "DraftKings", 1.8),
                bet("Jets", SPREAD, "FanDuel", 2.0)));
        return evaluator;
    }

    // ---- Behavior tests ----

    @Test
    public void topXBetsReturnsHighestGradeFirstTest() {
        List<Bet> top = evaluatorWithThreeBets().topXBets(3);
        assertEquals("Steelers", top.get(0).getTeam().getTeam());
        assertEquals("Jets", top.get(1).getTeam().getTeam());
        assertEquals("Ravens", top.get(2).getTeam().getTeam());
    }

    @Test
    public void topXBetsDoesNotRemoveBetsTest() {
        BetEvaluator evaluator = evaluatorWithThreeBets();
        evaluator.topXBets(2);
        assertEquals(3, evaluator.topXBets(3).size());
    }

    @Test
    public void askingForMoreBetsThanExistReturnsAllTest() {
        assertEquals(3, evaluatorWithThreeBets().topXBets(10).size());
    }

    @Test
    public void topXBetsByTeamTest() {
        List<Bet> top = evaluatorWithThreeBets().topXBetsByTeam(5, "Ravens");
        assertEquals(1, top.size());
        assertEquals("Ravens", top.get(0).getTeam().getTeam());
    }

    @Test
    public void topXBetsBySportsbookTest() {
        List<Bet> top = evaluatorWithThreeBets().topXBetsBySportsbook(5, "FanDuel");
        assertEquals(2, top.size());
        assertEquals("Steelers", top.get(0).getTeam().getTeam());
    }

    @Test
    public void topXBetsByPropTest() {
        assertEquals(2, evaluatorWithThreeBets().topXBetsByProp(5, "moneyline").size());
    }

    @Test
    public void betterOddsReplaceExistingBetTest() {
        BetEvaluator evaluator = evaluatorWithThreeBets();
        evaluator.createBets(list(bet("Ravens", ML, "Caesars", 2.5)));  // grade 0.25

        List<Bet> top = evaluator.topXBets(10);
        assertEquals(3, top.size());
        assertEquals("Ravens", top.get(0).getTeam().getTeam());
        assertEquals(2.5, top.get(0).getOdds(), 0.0001);
        assertTrue(evaluator.topXBetsBySportsbook(5, "DraftKings").isEmpty());
    }

    @Test
    public void worseOddsDoNotReplaceExistingBetTest() {
        BetEvaluator evaluator = evaluatorWithThreeBets();
        evaluator.createBets(list(bet("Steelers", ML, "Caesars", 1.5)));
        assertEquals(2.0, evaluator.topXBetsByTeam(1, "Steelers").get(0).getOdds(), 0.0001);
    }

    @Test
    public void predictionsAfterBetsStillGradeTest() {
        BetEvaluator evaluator = new BetEvaluator();
        evaluator.createBets(list(bet("Steelers", ML, "FanDuel", 2.0)));
        evaluator.createPredictions(list(prediction("Steelers", ML, 0.6)));
        assertEquals(0.2, evaluator.topXBets(1).get(0).getGrade(), 0.0001);
    }

}