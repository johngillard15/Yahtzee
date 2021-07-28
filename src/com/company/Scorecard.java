package com.company;

import java.util.HashMap;
import java.util.Map;

public class Scorecard {
    private final String[] COMBOS = {"ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES",
            "THREE_OF_A_KIND", "FOUR_OF_A_KIND", "FULL_HOUSE", "SMALL_STRAIGHT", "LARGE_STRAIGHT", "CHANCE", "YAHTZEE"};
    private final int[][] SMALL_STRAIGHTS = {{1, 2, 3, 4}, {2, 3, 4, 5}, {3, 4, 5, 6}};
    private final int[][] LARGE_STRAIGHTS = {{1, 2, 3, 4, 5}, {2, 3, 4, 5, 6}};

    private Map<String, Integer> scorecard = new HashMap<>();
    private Map<String, Integer> possibleCombos = new HashMap<>();
    private int totalScore = 0;

    public Scorecard(){
        for(int i = 0; scorecard.size() < COMBOS.length; i++){
            scorecard.put(COMBOS[i], null);
            possibleCombos.put(COMBOS[i], 0);
        }
    }

    public void checkCombos(int[] dice){
        for(Object arr : SMALL_STRAIGHTS){
            if(dice == arr)
                possibleCombos.put("SMALL_STRAIGHT", 1);
        }
        for(Object arr : LARGE_STRAIGHTS){
            if(dice == arr)
                possibleCombos.put("LARGE_STRAIGHT", 1);
        }

        if(scorecard.get("CHANCE") == 0){

        }
    }

    public void selectScore(){

    }

    // TODO mark score as used, try version 12 expressions
    private void addCombo(int choice){
        switch(choice){
            case 1:
                totalScore += scorecard.get("ONES");
                break;
            case 2:
                totalScore += scorecard.get("TWOS") * 2;
                break;
            case 3:
                totalScore += scorecard.get("THREES") * 3;
                break;
            case 4:
                totalScore += scorecard.get("FOURS") * 4;
                break;
            case 5:
                totalScore += scorecard.get("FIVES") * 5;
                break;
            case 6:
                totalScore += scorecard.get("SIXES") * 6;
                break;
            case 7:
                totalScore += scorecard.get("THREE_OF_A_KIND");
                break;
            case 8:
                totalScore += scorecard.get("FOUR_OF_A_KIND");
                break;
            case 9:
                totalScore += 25;
                break;
            case 10:
                totalScore += 30;
                break;
            case 11:
                totalScore += 40;
                break;
            case 12:
                totalScore += scorecard.get("CHANCE");
                break;
            case 13:
                if(scorecard.get("YAHTZEE") > 1)
                    totalScore += 100;
                else
                    totalScore += 50;
                break;
        }
    }

    private int getTotalScore(){
        return totalScore;
    }

    private void yahtzee(){

    }

    public void showScorecard(){
        System.out.println();
    }
}
