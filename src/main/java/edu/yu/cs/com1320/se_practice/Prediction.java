package edu.yu.cs.com1320.se_practice;

public class Prediction {
    private final Prop prop;
    private final Team team;
    private double probability;

    public Prediction(Prop prop, Team team, double probability) {
        this.prop = prop;
        this.team = team;
        this.probability = probability;
    }

    public Prop getProp() {
        return this.prop;
    }

    public Team getPickSubject() {
        return this.team;
    }

    public double getProbability() {
        return this.probability;
    }

    public void setProbability(double probability){
        this.probability = probability;
    }

    public String getPredictionID(){
        return team.getTeam() + prop.toString();
    }
}