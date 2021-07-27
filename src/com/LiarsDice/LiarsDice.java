package com.LiarsDice;

import com.company.DieGUI;
import com.company.Player;

import java.util.*;

/**
 * <h1>Liar's Dice</h1>
 *
 * <p>uhhh liars dice</p>
 *
 * <br>
 *
 * @since 24/7/2021
 * @author John Gillard
 * @version 27/7/2021
 */

/*
 * Liar's Dice game flow:
 *
 * start round
 * 1. roll everyone's dice
 * 2. go around table and get bids
 * 3. bid until a player accuses previous player of lying
 *
 * challenge
 * 1. count total occurrences of all face values
 * 2. check if player's bid was possible
 * 3. if bid was matched or exceeded (determined by the tableDice HashMap), mark accuser as liar
 *  if bid was not possible, mark bidder as liar
 * 4. liars get 1 die removed from their die count, or get removed from the game if they only have 1 die
 * 5. next round starts at the loser
 *
 * Plan of attack:
 *
 */

public class LiarsDice {
    private static final Scanner scan = new Scanner(System.in);
    private static final DieGUI dieGUI = new DieGUI();
    private static final int STARTING_DIE_COUNT = 5;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 5;

    private List<Player> players = new ArrayList<>();
    private Map<Integer, Integer> tableDice = new HashMap<>();
    private int[] lastBid = new int[2]; // value, amount
    private int[] currentBid = new int[2];
    private int currentPlayer = 0;
    private int currentRound = 1;
    private int currentTurn = 1;
    private boolean challenge = false;

    public LiarsDice(){
        int numPlayers = Player.getPlayerCount(MIN_PLAYERS, MAX_PLAYERS);

        int currentPlayer = 1;
        do{
            System.out.printf("\nPlayer %d, what is your name? ", currentPlayer);
            String name = scan.nextLine().trim();
            players.add(Player.addPlayer(name));
            System.out.printf("Hello, %s.\n", name);
            currentPlayer++;
        }while(players.size() != numPlayers);

        for(int faceValue = 1; faceValue <= 6; faceValue++)
            tableDice.put(faceValue, 0);
    }

    public void play(){
        do{
            round();
        }while(players.size() > 1);
        displayResults();
    }

    private void round(){
        System.out.printf("\n-- ROUND %d --", currentRound);

        for(Player player : players){
            player.cup.roll();
        }


        while(!challenge){
            if(currentPlayer == players.size())
                currentPlayer = 0;

            System.out.printf("\n- %s turn, press enter to continue... -\n", players.get(currentPlayer).name + "'s");
            scan.nextLine();

            turn(players.get(currentPlayer));

            if(challenge){
                int lastPlayer;
                if(currentPlayer == 0)
                    lastPlayer = players.size() - 1;
                else
                    lastPlayer = currentPlayer - 1;
                accuse(players.get(currentPlayer), players.get(lastPlayer));
            }
            else if(currentPlayer >= players.size() - 1){
                currentPlayer = -1;
            }
            currentPlayer++;
        }

        endRound();
    }

    private void endRound(){
        challenge = false;

        for(int faceValue = 1; faceValue <= 6; faceValue++)
            tableDice.put(faceValue, 0);

        Arrays.fill(lastBid, 0);
        Arrays.fill(currentBid, 0);

        currentTurn = 1;
        currentRound++;
    }

    private void turn(Player activePlayer){
        // TODO: catch any input exceptions
        lastBid = currentBid.clone();

        System.out.print("- Your dice -\n");
        System.out.println(activePlayer.cup.displayCup());

        if(currentTurn != 1){
            System.out.println("- Last Bid -");
            System.out.printf("%d %d's\n", lastBid[1], lastBid[0]);

            String choice;
            boolean validChoice;
            do {
                System.out.println("Would you like to (b)id or (a)ccuse?");
                choice = scan.next();

                validChoice = (choice.equalsIgnoreCase("b")) || (choice.equalsIgnoreCase("a"));
                if (!validChoice) {
                    System.out.printf("\"%s\" is not a valid option.\n" +
                            "Please choose \"b\" to place a bid and \"a\" to accuse the previous player.\n", choice);
                }
            } while (!validChoice);

            // TODO: ask cliff about switching language levels (switch expressions/arrow case)
            switch (choice) {
                case "b":
                    bid(activePlayer);
                    break;
                case "a":
                    challenge = true;
                    break;
                default:
                    System.out.println("ay bruh how tf");
            }
        }
        else{
            bid(activePlayer);
        }

        currentTurn++;
    }

    private void bid(Player activePlayer){
        int value, amount;
        boolean validBid;
        do{
            // TODO: catch NumberFormatExceptions
            System.out.println("- Your bid -");
            System.out.print("Value: ");
            value = scan.nextInt();
            System.out.print("Amount: ");
            amount = scan.nextInt();

           validBid = (amount > lastBid[1]) || (amount == lastBid[1] && value > lastBid[0]);
           if(!validBid){
               System.out.printf("\nA bid of %d %d's is not currently possible.\n", amount, value);
               System.out.println("The next bid must have:");
               if(lastBid[0] < 6){
                   System.out.printf("%d dice, higher than a %d\n", lastBid[1], lastBid[0]);
                   System.out.println("OR");
               }
               System.out.printf("%d or more dice of any value.\n", lastBid[1] + 1);
           }
        }while(!validBid);

        // TODO: now get the probablity of each bid
        currentBid[0] = value;
        currentBid[1] = amount;
        System.out.printf("\n%s has bid %d %d's.\n", activePlayer.name, currentBid[1], currentBid[0]);
    }

    private void accuse(Player accuser, Player accused){
        System.out.printf("\n%s has been called out by %s! But who's lying?\n", accused.name, accuser.name);
        updateTable();
        showTable();
        System.out.printf("%s bid %d %d's...\n", accused.name, currentBid[1], currentBid[0]);

        if(tableDice.get(currentBid[0]) >= currentBid[1]){
            System.out.printf("...and there are %d %d's!\n", tableDice.get(currentBid[0]), currentBid[0]);
            System.out.printf("\n%s is innocent! For shame, %s.\n", accused.name, accuser.name);
            punishment(accuser);
        }
        else{
            System.out.printf("...but there were only %d %d's...\n", tableDice.get(currentBid[0]), currentBid[0]);
            System.out.printf("\nLooks like %s was being shifty after all... I never trusted'em either.\n",
                    accused.name);
            punishment(accused);
        }
    }

    private void punishment(Player damnDirtyLiar){
        damnDirtyLiar.cup.removeDie();
        int diceLeft = damnDirtyLiar.cup.dice.size();

        if(diceLeft < 1){
            System.out.printf("\n%s has attempted to sully the good name of Liar's Dice, and must be ejected.\n" +
                            "Good riddance.\n", damnDirtyLiar.name);
            players.remove(damnDirtyLiar);
        }
        else{
            System.out.printf("\n%s still has %d dice left, good luck...\n", damnDirtyLiar.name, diceLeft);
            if(diceLeft == 1)
                System.out.println("... you'll need it...");
            currentPlayer = players.indexOf(damnDirtyLiar);
        }
    }

    private void updateTable(){
        for(Player player : players){
            int[] dieValues = parseCup(player);
            for(int faceUpValue : dieValues){
                tableDice.put(faceUpValue, tableDice.get(faceUpValue) + 1);
            }
        }
    }

    private void showTable(){
        System.out.println("\nDice in play:");
        for(int faceValue : tableDice.keySet()){
            System.out.printf("%d's: %d\n", faceValue, tableDice.get(faceValue));
        }
    }

    private int getTotalDiceInPlay(){
        int totalDiceInPlay = 0;
        for(Player player : players)
            totalDiceInPlay += player.cup.dice.size();

        return totalDiceInPlay;
    }

    public void showPlayerDice(Player player){
        int[] dieValues = parseCup(player);

        dieGUI.showDice(dieValues);
    }

    public int[] parseCup(Player player){
        String[] cupDieValues = player.cup.displayCup().split(" ");
        int[] dieValues = new int[cupDieValues.length];

        for(int i = 0; i < dieValues.length; i++)
            dieValues[i] = Integer.parseInt(cupDieValues[i]);

        return dieValues;
    }

    private void displayResults(){
        for(Player winner : players){
            System.out.printf("\n\nCongratulations, %s! You are the greatest Liar's Dice player ever!11!!!11!!\n",
                    winner.name);
            System.out.println(
                    "( •_•)\n" +
                    "( •_•)>⌐■-■\n" +
                    "(⌐■_■) "
            );
        }
    }
}
