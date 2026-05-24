package edu.yu.cs.com1320.se_practice;

public class Spread implements Prop{
    private double numberToCover;
    public Spread(double num){
        numberToCover = num;
    }

    public double getSpread(){
        return this.numberToCover;
    }

    public String toString(){
        return "Spread " + numberToCover;
    }

    public String getPropType(){
        return "spread";
    }
}
