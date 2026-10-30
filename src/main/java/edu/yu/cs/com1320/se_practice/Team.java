package edu.yu.cs.com1320.se_practice;

import java.util.Objects;

public class Team {
    private String team;
    public Team(String t){
        this.team = t;
    }

    public String getTeam(){
        return this.team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this == null){
            return false;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        Team other = (Team) o;
        return this.team.equals(other.team);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.team);
    }
}
