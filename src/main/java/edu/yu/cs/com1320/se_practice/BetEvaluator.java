package edu.yu.cs.com1320.se_practice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


public class BetEvaluator {
    private HashMap<String, Bet> bets;
    private HashMap<String, Prediction> predictions;
    private PriorityQueue<Bet> orderedBets;
    private HashMap<Team, PriorityQueue<Bet>> betsByTeam;
    private HashMap<Sportsbook, PriorityQueue<Bet>> betsBySportsbook;
    private HashMap<String, PriorityQueue<Bet>> betsByProp;
    private Gson gson;

    public BetEvaluator(){
        this.bets = new HashMap<>();
        this.predictions = new HashMap<>();
        this.orderedBets = new PriorityQueue<>();
        this.betsByTeam = new HashMap<>();
        this.betsByProp = new HashMap<>();
        this.betsBySportsbook = new HashMap<>();
        gson = new Gson();
    }

    /**
     * creates all the bet objects from the stream we were sent
     * if we have predictions, compares the bet to its prediction
     * @param allBets the bets we are processing
     */
    public void createBets(String allBets) {
        Type type = new TypeToken<ArrayList<Bet>>() {}.getType();
        ArrayList<Bet> newBets = gson.fromJson(allBets, type);
        for (Bet b : newBets) {
            Prediction p = predictions.get(b.getBetID());
            if (p == null) {
                bets.put(b.getBetID(), b);
            } else {
                if (bets.get(b.getBetID()) == null || bets.get(b.getBetID()).getOdds() < b.getOdds()) {
                    b.compareToPrediction(p);
                    bets.put(b.getBetID(), b);
                    orderedBets.remove(b);
                    orderedBets.add(b);
                    PriorityQueue<Bet> pqt = betsByTeam.get(b.getTeam());
                    PriorityQueue<Bet> pqp = betsByProp.get(b.getProp().getPropType());
                    PriorityQueue<Bet> pqs = betsBySportsbook.get(b.getSportsbook());
                    if (pqt == null) {
                        betsByTeam.put(b.getTeam(), new PriorityQueue<Bet>());
                    }
                    if (pqp == null) {
                        betsByTeam.put(b.getTeam(), new PriorityQueue<Bet>());
                    }
                    if (pqs == null) {
                        betsByTeam.put(b.getTeam(), new PriorityQueue<Bet>());
                    }
                    betsByTeam.get(b.getTeam()).remove(b);
                    betsByTeam.get(b.getTeam()).add(b);
                    betsByProp.get(b.getProp().getPropType()).remove(b);
                    betsByProp.get(b.getProp().getPropType()).add(b);
                    betsBySportsbook.get(b.getSportsbook()).remove(b);
                    betsBySportsbook.get(b.getSportsbook()).add(b);
                }
            }
        }
    }


    /**
     *
     * @param allPredictions the predictions we are processing
     */
    public void createPredictions(String allPredictions){
        Type type = new TypeToken<ArrayList<Prediction>>(){}.getType();
        ArrayList<Prediction> newPredictions = gson.fromJson(allPredictions, type);
        for (Prediction p : newPredictions){
            predictions.put(p.getPredictionID(), p);
            Bet b = bets.get(p.getPredictionID());
            if (b != null){
                if (b.wasChecked()){
                    orderedBets.remove(b);
                    //make sure there is a heap for the team, sportsbook, and prop
                    PriorityQueue<Bet> pqt = betsByTeam.get(b.getTeam());
                    if (pqt == null){
                        betsByTeam.put(b.getTeam(), new PriorityQueue<>());
                    }
                    PriorityQueue<Bet> pqs = betsBySportsbook.get(b.getSportsbook());
                    if (pqs == null){
                        betsBySportsbook.put(b.getSportsbook(), new PriorityQueue<>());
                    }
                    PriorityQueue<Bet> pqp = betsByProp.get(b.getProp().getPropType());
                    if (pqp == null){
                        betsByProp.put(b.getProp().getPropType(), new PriorityQueue<>());
                    }

                    betsByTeam.get(b.getTeam()).remove(b);
                    betsBySportsbook.get(b.getSportsbook()).remove(b);
                    betsByProp.get(b.getProp().getPropType()).remove(b);
                    b.compareToPrediction(p);
                    orderedBets.add(b);
                    betsByTeam.get(b.getTeam()).add(b);
                    betsBySportsbook.get(b.getSportsbook()).add(b);
                    betsByProp.get(b.getProp().getPropType()).add(b);
                } else {
                    b.compareToPrediction(p);
                    orderedBets.add(b);
                    betsByTeam.get(b.getTeam()).add(b);
                    betsBySportsbook.get(b.getSportsbook()).add(b);
                    betsByProp.get(b.getProp().getPropType()).add(b);
                }
            }
        }
    }

    public List<Bet> topXBets(int numBets){
        if (numBets < 1){
            throw new IllegalArgumentException("you have to ask for at least 1 bet");
        }
        if (orderedBets.isEmpty()){
            throw new IllegalArgumentException("there are no bets");
        }
        List<Bet> topBets = new ArrayList<>();
        for (int i = 0; i < numBets; i++){
            topBets.add(orderedBets.poll());
        }
        for (int i = 0; i < numBets; i++){
            orderedBets.add(topBets.get(i));
        }
        return topBets;
    }

    public List<Bet> topXBetsByTeam(int numBets, String team){
        if (numBets < 1){
            throw new IllegalArgumentException("you have to ask for at least 1 bet");
        }
        Team t = new Team(team);
        PriorityQueue<Bet> temp = betsByTeam.get(t);
        if (temp == null){
            throw new IllegalArgumentException("there are no bets here");
        }
        List<Bet> topBetsByTeam = new ArrayList<>();
        for (int i = 0; i < numBets; i++){
            topBetsByTeam.add(temp.poll());
        }
        for (int i = 0; i < numBets; i++){
            temp.add(topBetsByTeam.get(i));
        }
        return topBetsByTeam;
    }

    public List<Bet> topXBetsBySportsbook(int numBets, String sportsbook){
        if (numBets < 1){
            throw new IllegalArgumentException("you have to ask for at least 1 bet");
        }
        Sportsbook s = new Sportsbook(sportsbook);
        PriorityQueue<Bet> temp = betsBySportsbook.get(s);
        if (temp == null){
            throw new IllegalArgumentException("there are no bets here");
        }
        List<Bet> topBetsBySportsbook = new ArrayList<>();
        for (int i = 0; i < numBets; i++){
            topBetsBySportsbook.add(temp.poll());
        }
        for (int i = 0; i < numBets; i++){
            temp.add(topBetsBySportsbook.get(i));
        }
        return topBetsBySportsbook;
    }

    public List<Bet> topXBetsByProp(int numBets, String prop){
        if (numBets < 1){
            throw new IllegalArgumentException("you have to ask for at least 1 bet");
        }
        PriorityQueue<Bet> temp = betsByProp.get(prop);
        if (temp == null){
            throw new IllegalArgumentException("there are no bets here");
        }
        List<Bet> topBetsByProp = new ArrayList<>();
        for (int i = 0; i < numBets; i++){
            topBetsByProp.add(temp.poll());
        }
        for (int i = 0; i < numBets; i++){
            temp.add(topBetsByProp.get(i));
        }
        return topBetsByProp;
    }

}
