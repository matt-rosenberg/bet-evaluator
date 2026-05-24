package edu.yu.cs.com1320.se_practice;

public class Prediction {
    private final Prop prop;
    private final Team team;
    private double odds;

    public Prediction(Prop prop, Team team, double odds) {
        this.prop = prop;
        this.team = team;
        this.odds = odds;
    }

    public Prop getProp() {
        return this.prop;
    }

    public Team getPickSubject() {
        return this.team;
    }

    public double getOdds() {
        return this.odds;
    }

    public void setOdds(double odds){
        this.odds = odds;
    }

    public String getPredictionID(){
        return team.getTeam() + prop.toString();
    }
}