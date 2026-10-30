package edu.yu.cs.com1320.se_practice;

import java.util.Objects;

public class Sportsbook {
    private String book;
    public Sportsbook(String sb){
        this.book = sb;
    }

    protected String getSportsbook(){
        return this.book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this == null){
            return false;
        }
        if (!(o instanceof Sportsbook)) {
            return false;
        }
        Sportsbook other = (Sportsbook) o;
        return this.book.equals(other.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.book);
    }
}
