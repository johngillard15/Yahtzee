package com.company;

import java.util.HashMap;
import java.util.Map;

public class ScoreCard {
    private final String[] COMBOS = {"ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES",
            "THREE_OF_A_KIND", "FOUR_OF_A_KIND", "SMALL_STRAIGHT", "LARGE_STRAIGHT", "FULL_HOUSE", "CHANCE", "YAHTZEE"};

    private Map<String, Integer> scorecard = new HashMap<>();

    public ScoreCard(){
        for(String combo : COMBOS){
            scorecard.put(combo, -1);
        }
    }

    private void calculateScore(){

    }
}
