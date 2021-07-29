package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ScoreCard {
    public final String[] COMBOS = {"ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES",
            "THREE_OF_A_KIND", "FOUR_OF_A_KIND", "SMALL_STRAIGHT", "LARGE_STRAIGHT", "FULL_HOUSE", "CHANCE", "YAHTZEE"};
    public final int[][] SMALL_STRAIGHTS = {{1, 2, 3, 4}, {2, 3, 4, 5}, {3, 4, 5, 6}};
    public final int[][] LARGE_STRAIGHTS = {{1, 2, 3, 4, 5}, {2, 3, 4, 5, 6}};

    public Map<String, Integer> scorecard = new HashMap<>();
    public Map<String, Integer> possibleCombos = new HashMap<>();
    public int totalScore = 0;

    public ScoreCard(){
        for(String combo : COMBOS){
            scorecard.put(combo, -1);
        }
    }

    public void showScorecard(){
        System.out.println("- Your ScoreCard -");

        for(String combo : scorecard.keySet()){
            if(combo.equals("ONES"))
                System.out.println("Upper Section");
            else if(combo.equals("THREE_OF_A_KIND"))
                System.out.println("Lower Section");
            System.out.printf("%s: %s\n", combo, scorecard.get(combo) == -1 ? "---" : scorecard.get(combo));
        }
    }

    public void checkCombos(int[] dice){
        possibleCombos.clear();

        Map<Integer, Integer> faceValues = new HashMap<>();
        for(int die : dice){
            if(!faceValues.containsKey(die))
                faceValues.put(die, 1);
            else
                faceValues.put(die, faceValues.get(die) + 1);
        }

        for(String combo : scorecard.keySet()){
            if(scorecard.get(combo) == -1){
                switch(combo){
                    case "ONES":
                        if(faceValues.containsKey(1) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "TWOS":
                        if(faceValues.containsKey(2) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "THREES":
                        if(faceValues.containsKey(3) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "FOURS":
                        if(faceValues.containsKey(4) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "FIVES":
                        if(faceValues.containsKey(5) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "SIXES":
                        if(faceValues.containsKey(6) && faceValues.get(1) >= 1)
                            possibleCombos.put(combo, 0);
                        break;
                    case "THREE_OF_A_KIND":
                        for(int die : faceValues.values()){
                            if(die >= 3){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;
                    case "FOUR_OF_A_KIND":
                        for(int die : faceValues.values()){
                            if(die >= 4){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;
                    case "FULL_HOUSE":
                        boolean hasPair = false;
                        boolean hasTriple = false;
                        for(int die : faceValues.values()){
                            if(die == 2)
                                hasPair = true;
                            if(die == 3){
                                hasTriple = true;
                            }
                            if(hasPair && hasTriple){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;
                    case "SMALL_STRAIGHT":
                        Arrays.sort(dice);
                        if(Arrays.asList(SMALL_STRAIGHTS).contains(dice))
                            possibleCombos.put(combo, 0);
                        break;
                    case "LARGE_STRAIGHT":
                        Arrays.sort(dice);
                        if(Arrays.asList(LARGE_STRAIGHTS).contains(dice))
                            possibleCombos.put(combo, 0);
                        break;
                    case "CHANCE":
                        possibleCombos.put(combo, 0);
                        break;
                    case "YAHTZEE":
                        if(faceValues.size() == 1)
                            possibleCombos.put(combo, 0);
                        break;
                    default:

                }
            }
        }
    }

    public void showCombos(){
        System.out.println("- Possible Combos -");
    }

    public int getPoints(String key){
        int points = 0;

        for(String combo : possibleCombos.keySet()){
            switch(combo){
                case "ONES":
                    points += possibleCombos.put(combo, points);
                    break;
                case "TWOS":
                    break;
                case "THREES":
                    break;
                case "FOURS":
                    break;
                case "FIVES":
                    break;
                case "SIXES":
                    break;
                case "THREE_OF_A_KIND":
                    break;
                case "FOUR_OF_A_KIND":
                    break;
                case "FULL_HOUSE":
                    break;
                case "SMALL_STRAIGHT":
                    break;
                case "LARGE_STRAIGH":
                    break;
                case "CHANCE":
                    break;
                case "YAHTZEE":
                    break;
                default:
            }
        }

        return points;
    }

    public void calculateTotalScore(){
        for(String combo : scorecard.keySet()){
            if(combo.equals("THREE_OF_A_KIND") && totalScore >= 63)
                totalScore += 35;

            totalScore += scorecard.get(combo);
        }
    }
}
