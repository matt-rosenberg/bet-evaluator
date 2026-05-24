package edu.yu.cs.com1320.se_practice;

public class Sportsbook {
    private String book;
    public Sportsbook(String sb){
        this.book = sb;
    }

    protected String getSportsbook(){
        return this.book;
    }
}
