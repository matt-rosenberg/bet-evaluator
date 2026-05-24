package edu.yu.cs.com1320.se_practice;

public class OverUnder implements Prop {
    private final double number;
    private final boolean overOrUnder;

    public OverUnder(double number, Boolean overOrUnder) {
        this.number = number;
        this.overOrUnder = overOrUnder;
    }

    public double getNumber() {
        return this.number;
    }

    public Boolean overOrUnder() {
        return overOrUnder;
    }

    public String toString() {
        return overOrUnder ? "over " + this.number: "under " + this.number;
    }

    public String getPropType(){
        return "OverUnder";
    }
}