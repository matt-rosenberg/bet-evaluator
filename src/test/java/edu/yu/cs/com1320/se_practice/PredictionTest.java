package edu.yu.cs.com1320.se_practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PredictionTest {

    @Test
    public void constructorTest() {
        Prop prop = new Moneyline();
        Team team = new Team("Steelers");

        Prediction prediction = new Prediction(prop, team, 0.6);

        assertEquals(prop, prediction.getProp());
        assertEquals(team, prediction.getPickSubject());
        assertEquals(0.6, prediction.getOdds(), 0.0001);
    }

    @Test
    public void setOddsTest() {
        Prediction prediction = new Prediction(new Moneyline(), new Team("Steelers"), 0.6);

        prediction.setOdds(0.75);

        assertEquals(0.75, prediction.getOdds(), 0.0001);
    }

    @Test
    public void getPredictionIDMoneylineTest() {
        Prop prop = new Moneyline();
        Team team = new Team("Steelers");

        Prediction prediction = new Prediction(prop, team, 0.6);

        assertEquals("Steelersmoneyline", prediction.getPredictionID());
    }

    @Test
    public void getPredictionIDSpreadTest() {
        Prop prop = new Spread(-3.5);
        Team team = new Team("Steelers");

        Prediction prediction = new Prediction(prop, team, 0.6);

        assertEquals("SteelersSpread -3.5", prediction.getPredictionID());
    }

    @Test
    public void getPredictionIDOverUnderTest() {
        Prop prop = new OverUnder(47.5, true);
        Team team = new Team("Steelers");

        Prediction prediction = new Prediction(prop, team, 0.6);

        assertEquals("Steelersover 47.5", prediction.getPredictionID());
    }
}
