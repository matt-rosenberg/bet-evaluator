package edu.yu.cs.com1320.se_practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BetTest {

    @Test
    public void constructorTest() {
        Prop prop = new Moneyline();
        Sportsbook sportsbook = new Sportsbook("FanDuel");
        Team team = new Team("Steelers");

        Bet bet = new Bet(prop, sportsbook, team, 2.0);

        assertEquals(prop, bet.getProp());
        assertEquals(sportsbook, bet.getSportsbook());
        assertEquals(team, bet.getTeam());
        assertEquals(2.0, bet.getOdds(), 0.0001);
        assertEquals(0.0, bet.getGrade(), 0.0001);
        assertFalse(bet.wasChecked());
    }

    @Test
    public void compareToPredictionTest() {
        Prop prop = new Moneyline();
        Team team = new Team("Steelers");

        Bet bet = new Bet(prop, new Sportsbook("FanDuel"), team, 2.0);
        Prediction prediction = new Prediction(prop, team, 0.6);

        double grade = bet.compareToPrediction(prediction);

        assertEquals(-0.7, grade, 0.0001);
        assertEquals(-0.7, bet.getGrade(), 0.0001);
        assertTrue(bet.wasChecked());
    }

    @Test
    public void setGradeTest() {
        Bet bet = new Bet(new Moneyline(), new Sportsbook("FanDuel"), new Team("Steelers"), 2.0);

        bet.setGrade(0.25);

        assertEquals(0.25, bet.getGrade(), 0.0001);
    }

    @Test
    public void setOddsTest() {
        Bet bet = new Bet(new Moneyline(), new Sportsbook("FanDuel"), new Team("Steelers"), 2.0);

        bet.setOdds(1.5);

        assertEquals(1.5, bet.getOdds(), 0.0001);
    }

    @Test
    public void compareToSmallerGradeTest() {
        Bet bet1 = new Bet(new Moneyline(), new Sportsbook("FanDuel"), new Team("Steelers"), 2.0);
        Bet bet2 = new Bet(new Moneyline(), new Sportsbook("DraftKings"), new Team("Steelers"), 2.0);

        bet1.setGrade(0.1);
        bet2.setGrade(0.5);

        assertTrue(bet1.compareTo(bet2) < 0);
    }

    @Test
    public void compareToLargerGradeTest() {
        Bet bet1 = new Bet(new Moneyline(), new Sportsbook("FanDuel"), new Team("Steelers"), 2.0);
        Bet bet2 = new Bet(new Moneyline(), new Sportsbook("DraftKings"), new Team("Steelers"), 2.0);

        bet1.setGrade(0.8);
        bet2.setGrade(0.3);

        assertTrue(bet1.compareTo(bet2) > 0);
    }

    @Test
    public void compareToEqualGradeTest() {
        Bet bet1 = new Bet(new Moneyline(), new Sportsbook("FanDuel"), new Team("Steelers"), 2.0);
        Bet bet2 = new Bet(new Spread(3.5), new Sportsbook("DraftKings"), new Team("Ravens"), 1.8);

        bet1.setGrade(0.4);
        bet2.setGrade(0.4);

        assertEquals(0, bet1.compareTo(bet2));
    }

    @Test
    public void getBetIDMoneylineTest() {
        Prop prop = new Moneyline();
        Team team = new Team("Steelers");

        Bet bet = new Bet(prop, new Sportsbook("FanDuel"), team, 2.0);

        assertEquals("Steelersmoneyline", bet.getBetID());
    }

    @Test
    public void getBetIDSpreadTest() {
        Prop prop = new Spread(-3.5);
        Team team = new Team("Steelers");

        Bet bet = new Bet(prop, new Sportsbook("FanDuel"), team, 2.0);

        assertEquals("SteelersSpread -3.5", bet.getBetID());
    }

    @Test
    public void getBetIDOverUnderTest() {
        Prop prop = new OverUnder(47.5, true);
        Team team = new Team("Steelers");

        Bet bet = new Bet(prop, new Sportsbook("FanDuel"), team, 2.0);

        assertEquals("Steelersover 47.5", bet.getBetID());
    }
}