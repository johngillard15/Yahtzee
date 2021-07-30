package com.company;

import java.sql.SQLOutput;
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

        for(String combo : scorecard.keySet()){ // TODO: not printing in order?
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

        Arrays.sort(dice);
        for(String combo : scorecard.keySet()){
            if(scorecard.get(combo) == -1){
                switch(combo){
                    case "ONES":
                        if(faceValues.containsKey(1))
                            possibleCombos.put(combo, 0);
                        break;

                    case "TWOS":
                        if(faceValues.containsKey(2))
                            possibleCombos.put(combo, 0);
                        break;

                    case "THREES":
                        if(faceValues.containsKey(3))
                            possibleCombos.put(combo, 0);
                        break;

                    case "FOURS":
                        if(faceValues.containsKey(4))
                            possibleCombos.put(combo, 0);
                        break;

                    case "FIVES":
                        if(faceValues.containsKey(5))
                            possibleCombos.put(combo, 0);
                        break;

                    case "SIXES":
                        if(faceValues.containsKey(6))
                            possibleCombos.put(combo, 0);
                        break;

                    case "THREE_OF_A_KIND":
                        for(int amount : faceValues.values()){
                            if(amount >= 3){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;

                    case "FOUR_OF_A_KIND":
                        for(int amount : faceValues.values()){
                            if(amount >= 4){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;

                    case "FULL_HOUSE":
                        boolean hasPair = false;
                        boolean hasTriple = false;
                        for(int amount : faceValues.values()){
                            if(amount == 2)
                                hasPair = true;
                            if(amount == 3){
                                hasTriple = true;
                            }
                            if(hasPair && hasTriple){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;

                    case "SMALL_STRAIGHT":
                        int[] diceShort = new int[4];
                        System.arraycopy(dice, 0, diceShort, 0, diceShort.length);

                        for(int[] small_straight : SMALL_STRAIGHTS){
                            if(Arrays.equals(small_straight, diceShort)){
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
                        break;

                    case "LARGE_STRAIGHT":
                        for(int[] large_straight : LARGE_STRAIGHTS){
                            if(Arrays.equals(large_straight, dice)) {
                                possibleCombos.put(combo, 0);
                                break;
                            }
                        }
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

        if(possibleCombos.isEmpty()){
            System.out.println("There are no valid combos on your scorecard for this roll.\n" +
                    "You must choose a score to fill with a 0:");
            for(String combo : scorecard.keySet()){
                if(scorecard.get(combo) == -1){
                    switch(combo){
                        case "ONES":
                        case "TWOS":
                        case "THREES":
                        case "FOURS":
                        case "FIVES":
                        case "SIXES":
                        case "THREE_OF_A_KIND":
                        case "FOUR_OF_A_KIND":
                        case "FULL_HOUSE":
                        case "SMALL_STRAIGHT":
                        case "LARGE_STRAIGHT":
                        case "CHANCE":
                        case "YAHTZEE":
                            possibleCombos.put(combo, 0);
                            break;
                        default:
                    }
                }
            }
        }
    }

    public void showPossibleCombos(){
        System.out.println("- Possible Combos -");

        int count = 1;
        for(String combo : possibleCombos.keySet()){
            System.out.printf("%d. %s\n", count, combo);
            count++;
        }
    }

    public void getPlayerChoice(){

    }

    public int getPoints(String key){
        int points = 0;

//        for(String combo : possibleCombos.keySet()){
//            switch(combo){
//                case "ONES":
//                    points += possibleCombos.put(combo, points);
//                    break;
//                case "TWOS":
//                    break;
//                case "THREES":
//                    break;
//                case "FOURS":
//                    break;
//                case "FIVES":
//                    break;
//                case "SIXES":
//                    break;
//                case "THREE_OF_A_KIND":
//                    break;
//                case "FOUR_OF_A_KIND":
//                    break;
//                case "FULL_HOUSE":
//                    break;
//                case "SMALL_STRAIGHT":
//                    break;
//                case "LARGE_STRAIGH":
//                    break;
//                case "CHANCE":
//                    break;
//                case "YAHTZEE":
//                    break;
//                default:
//            }
//        }

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
