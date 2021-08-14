package com.Yahtzee;

import java.util.*;

public class ScoreCard {
    private final Scanner scan = new Scanner(System.in);

    protected final String[] COMBOS = {"ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES",
            "THREE_OF_A_KIND", "FOUR_OF_A_KIND", "SMALL_STRAIGHT", "LARGE_STRAIGHT", "FULL_HOUSE", "CHANCE", "YAHTZEE"};
    protected final int[][] SMALL_STRAIGHTS = {{1, 2, 3, 4}, {2, 3, 4, 5}, {3, 4, 5, 6}};
    protected final int[][] LARGE_STRAIGHTS = {{1, 2, 3, 4, 5}, {2, 3, 4, 5, 6}};

    protected Map<String, Integer> scorecard = new HashMap<>();
    protected Map<String, Integer> possibleCombos = new HashMap<>();
    protected Map<Integer, Integer> currentFaceValues = new HashMap<>();
    protected int totalScore = 0;

    public ScoreCard(){
        for(String combo : COMBOS){
            scorecard.put(combo, -1);
        }
    }

    protected void showScorecard(){
        System.out.println("\t- Your ScoreCard -");

        for(String combo : COMBOS){
            if(combo.equals("ONES"))
                System.out.println("Upper Section");
            else if(combo.equals("THREE_OF_A_KIND")) {
                int upperScore = 0;
                boolean usedAllUppers = true;
                for(String upperCombo : COMBOS){
                    if(scorecard.get(upperCombo) == -1)
                        usedAllUppers = false;
                    else
                        upperScore += scorecard.get(upperCombo);

                    if(upperCombo.equals("SIXES")) break;
                }

                System.out.printf("%17s: %d\n", "Upper Score", upperScore);
                if(usedAllUppers) System.out.printf("%17s: %d\n", "*BONUS*", upperScore >= 63 ? 35 : 0);
                System.out.println("Lower Section");
            }
            System.out.printf("|%16s: %-4s|\n", combo, scorecard.get(combo) == -1 ? "---" : scorecard.get(combo));
        }

        calculateTotalScore();
        System.out.printf("%17s: %d\n", "Total Score", totalScore);
    }

    protected void checkCombos(int[] dice){
        possibleCombos.clear();
        currentFaceValues.clear();

        for(int die : dice){
            if(!currentFaceValues.containsKey(die))
                currentFaceValues.put(die, 1);
            else
                currentFaceValues.put(die, currentFaceValues.get(die) + 1);
        }

        for(String combo : COMBOS){
            if(scorecard.get(combo) == -1){
                int upperNum = Arrays.asList(COMBOS).indexOf(combo) + 1;

                switch(combo){
                    case "ONES":
                    case "TWOS":
                    case "THREES":
                    case "FOURS":
                    case "FIVES":
                    case "SIXES":
                        if(currentFaceValues.containsKey(upperNum))
                            possibleCombos.put(combo, getPoints(combo));
                        break;

                    case "THREE_OF_A_KIND":
                        for(int amount : currentFaceValues.values()){
                            if(amount >= 3){
                                possibleCombos.put(combo, getPoints(combo));
                                break;
                            }
                        }
                        break;

                    case "FOUR_OF_A_KIND":
                        for(int amount : currentFaceValues.values()){
                            if(amount >= 4){
                                possibleCombos.put(combo, getPoints(combo));
                                break;
                            }
                        }
                        break;

                    case "FULL_HOUSE":
                        boolean hasPair = false;
                        boolean hasTriple = false;
                        for(int amount : currentFaceValues.values()){
                            if(amount == 2)
                                hasPair = true;
                            if(amount == 3){
                                hasTriple = true;
                            }
                            if(hasPair && hasTriple){
                                possibleCombos.put(combo, getPoints(combo));
                                break;
                            }
                        }
                        break;

                    case "SMALL_STRAIGHT":
                        for(int[] small_straight : SMALL_STRAIGHTS){
                            int count = 0;

                            for(int value : small_straight){
                                if(Arrays.binarySearch(dice, value) >= 0)
                                    count++;
                            }

                            if(count >= 4){
                                possibleCombos.put(combo, getPoints(combo));
                                break;
                            }
                        }
                        break;

                    case "LARGE_STRAIGHT":
                        for(int[] large_straight : LARGE_STRAIGHTS){
                            if(Arrays.equals(large_straight, dice)) {
                                possibleCombos.put(combo, getPoints(combo));
                                break;
                            }
                        }
                        break;

                    case "CHANCE":
                        possibleCombos.put(combo, getPoints(combo));
                        break;

                    case "YAHTZEE":
                        if(currentFaceValues.size() == 1){
                            if(scorecard.get(combo) == -1)
                                possibleCombos.put(combo, getPoints(combo));
                            else
                                possibleCombos.put(combo, scorecard.get(combo) + getPoints(combo));
                        }
                        break;
                }
            }
        }

        if(possibleCombos.isEmpty()){
            System.out.println("\nThere are no valid combos on your scorecard for this roll.\n" +
                    "You must choose a score to fill with a 0:");
            for(String combo : COMBOS){
                if(scorecard.get(combo) == -1)
                    possibleCombos.put(combo, 0);
            }
        }
    }

    protected void showPossibleCombos(){
        System.out.println("\n- Possible Combos -");

        int count = 1;
        for(String combo : COMBOS){
            if(possibleCombos.containsKey(combo)){
                int points = possibleCombos.get(combo);
                System.out.printf("%d. %s (%d point%s)\n", count, combo, points, points == 1 ? "" : "s");
                count++;
            }
        }
    }

    protected void getPlayerChoice(){
        int choice;

        do{
            System.out.printf("Which score would you like to use? (1-%d)\n", possibleCombos.size());
            String input = scan.nextLine().trim();

            try{
                choice = Integer.parseInt(input);
                break;
            }
            catch(NumberFormatException e){
                System.out.printf("\"%s\" is not a valid choice. Please try again.\n\n", input);
            }
        }while(true);

        /*
         * to maintain the order from String[] COMBOS, and add points to the option the player picked:
         * - first check if that combo was already used, don't bother checking if it has points
         * - checks if that combo is in possibleCombos
         * - if it is, add 1 to count, which should now correlate to where it was when the player saw it
         * - check if count and choice are equal; was that what the player chose?
         * - if so, add the points from possibleCombos to the player's scorecard, exit from loop
         * - if not, move to next combo
         */
        int count = 0;
        for(String combo : COMBOS){
            if(possibleCombos.containsKey(combo))
                count++;
            if(count == choice){
                scorecard.put(combo, possibleCombos.get(combo));
                break;
            }
        }
    }

    private int getPoints(String key){
        int points = 0;
        int upperNum = Arrays.asList(COMBOS).indexOf(key) + 1;

        switch(key){
            case "ONES":
            case "TWOS":
            case "THREES":
            case "FOURS":
            case "FIVES":
            case "SIXES":
                points = currentFaceValues.get(upperNum) * upperNum;
                break;
            case "THREE_OF_A_KIND":
            case "FOUR_OF_A_KIND":
            case "CHANCE":
                for(int value : currentFaceValues.keySet())
                    points += value * currentFaceValues.get(value);
                break;
            case "FULL_HOUSE":
                points = 25;
                break;
            case "SMALL_STRAIGHT":
                points = 30;
                break;
            case "LARGE_STRAIGHT":
                points = 40;
                break;
            case "YAHTZEE":
                if(scorecard.get("YAHTZEE") >= 50)
                    points = 100;
                else
                    points = 50;
                break;
            default:
        }

        return points;
    }

    protected void calculateTotalScore(){
        totalScore = 0;

        for(String combo : COMBOS){
            int score = scorecard.get(combo);
            if(score != -1)
                totalScore += score;

            if(combo.equals("SIXES") && totalScore >= 63)
                totalScore += 35;
        }
    }
}
