package edu.yu.cs.com1320.se_practice;

public class Bet implements Comparable<Bet> {
    private final Prop prop;
    private final Sportsbook sportsbook;
    private final Team team;
    private double odds;
    private double grade;
    private boolean checked;

    public Bet(Prop prop, Sportsbook sportsbook, Team team, double odds) {
        this.prop = prop;
        this.sportsbook = sportsbook;
        this.team = team;
        this.odds = odds;
    }

    public double compareToPrediction(Prediction prediction) {
        this.grade = prediction.getProbability() * odds - 1;
        checked = true;
        return this.grade;
    }

    public Prop getProp() {
        return this.prop;
    }

    public Sportsbook getSportsbook() {
        return this.sportsbook;
    }

    public Team getTeam() {
        return this.team;
    }

    public double getOdds() {
        return this.odds;
    }

    public void setOdds(double newOdds){
        this.odds = newOdds;
    }

    public double getGrade() {
        return this.grade;
    }

    public boolean wasChecked(){
        return this.checked;
    }

    /**
     *
     * @param newGrade the number to upgrade to
     */
    public void setGrade(double newGrade) {
        this.grade = newGrade;
    }

    @Override
    public int compareTo(Bet o) {
        return Double.compare(this.grade,o.grade);
    }

    public String getBetID(){
        return team.getTeam() + prop.toString();
    }
}