package com.LiarsDice;

import com.Game.Game;
import com.Utilities.CLI;
import com.Utilities.InputValidator;
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
 * @version 5/13/2021
 */

public class LiarsDice extends Game {
    private static final DieGUI dieGUI = new DieGUI();
    private static final int MIN_PLAYERS = 2;

    private final Map<Integer, Integer> tableDice = new HashMap<>();
    private final int[] currentBid = new int[2]; // value, amount

    private int[] lastBid = new int[2];
    private int totalDiceInPlay;
    private int currentRound = 1;
    private int currentTurn = 1;
    private int currentPlayer = 0;
    private boolean challenge = false;

    // TODO: now get the probability of each bid
    // TODO: maybe keep track of previous bids to help player make future bids

    public LiarsDice(){
        zeroTable(); // throws null pointer when in override setup() and when Game calls setup()
    }

    @Override
    protected void setup() {
        int startingDice = 0;
        boolean validDiceAmount = false;
        do{
            System.out.println("\nHow many dice will each player start with?");
            System.out.print("starting dice: ");
            String input = scan.nextLine();

            if(InputValidator.validateInt(input)) {
                startingDice = Integer.parseInt(input);
                validDiceAmount = true;
            }
            else
                System.out.printf("You cannot play the game with \"%s\" dice.\n", input);
        }while(!validDiceAmount);

        int numPlayers = getPlayerCount(MIN_PLAYERS);

        do{
            System.out.printf("\nPlayer %d, what is your name? ", players.size() + 1);
            String name = scan.nextLine().trim();

            players.add(Player.addPlayer(name, startingDice));

            System.out.printf("Hello, %s.\n", name);
        }while(players.size() < numPlayers);

        totalDiceInPlay = players.size() * startingDice;

        System.out.println("\n--- Welcome to Liar's Dice! ---");
        CLI.pause();
    }

    public void play(){
        do{
            round();
        }while(players.size() > 1);
        displayResults();
    }

    private void zeroTable(){
        for(int faceValue = 1; faceValue <= 6; faceValue++)
            tableDice.put(faceValue, 0);
    }

    private void rollAll(){
        for(Player player : players){
            player.cup.roll();
        }
    }

    private void clearBids(){
        Arrays.fill(currentBid, 0);
        Arrays.fill(lastBid, 0);
    }

    protected void round(){
        CLI.cls();
        System.out.printf("\n-- ROUND %d --", currentRound);

        rollAll();
        updateTable();

        do{
            if(currentTurn != 1)
                CLI.cls();

            turn(players.get(currentPlayer));

            if(challenge){
                int lastPlayer = currentPlayer == 0
                        ? players.size() - 1
                        : currentPlayer - 1;
                accuse(players.get(currentPlayer), players.get(lastPlayer));
            }
            else
                currentPlayer = ++currentPlayer % players.size();
        }while(!challenge);

        endRound();
    }

    private void endRound(){
        challenge = false;

        zeroTable();
        //tableDice.replaceAll((key, value) -> 0);

        clearBids();

        currentRound++;
        currentTurn = 1;

        System.out.print("\n- End of round -\n");
        CLI.pause();
    }

    protected void turn(Player activePlayer){
        System.out.printf("\n- [P%d] %s's turn -\n", currentPlayer + 1, activePlayer.name);
        CLI.pause();

        System.out.print("- Your dice -\n");
        activePlayer.cup.sort();
        dieGUI.showDice(activePlayer.cup.parseCup());

        if(currentTurn == 1){
            bid();
        }
        else{
            System.out.println("- Last Bid -");
            dieGUI.showDie(currentBid[0]);
            System.out.printf(" x%d\n", currentBid[1]);
            System.out.printf("There are %d dice on the table.\n\n", totalDiceInPlay);

            switch(getTurnChoice()){
                case "b":
                    bid();
                    break;
                case "a":
                    challenge = true;
                    break;
                default:
                    System.out.println("ay bruh how tf");
            }
        }

        currentTurn++;
    }

    private String getTurnChoice(){
        String choice;

        boolean validChoice;
        do{
            System.out.println("Would you like to (b)id or (a)ccuse?");
            choice = scan.nextLine();

            validChoice = ("b".equalsIgnoreCase(choice)) || ("a".equalsIgnoreCase(choice));
            if (!validChoice) {
                System.out.printf("\"%s\" is not a valid option.\n" +
                        "Please choose \"b\" to place a bid and \"a\" to accuse the previous player.\n\n", choice);
            }
        }while(!validChoice);

        return choice;
    }

    private void bid(){
        lastBid = currentBid.clone();

        while(!validateBidFormat() || !validateBid());
    }

    private boolean validateBidFormat(){
        String problemVar = "";
        String input = "";

        System.out.println("- Your bid -");
        try{
            System.out.print("Value: ");
            input = scan.nextLine();
            problemVar = "value";
            currentBid[0] = Integer.parseInt(input);

            System.out.print("Amount: ");
            input = scan.nextLine();
            problemVar = "amount";
            currentBid[1] = Integer.parseInt(input);
        }
        catch(NumberFormatException e){
            System.out.printf("\"%s\" is not a valid bid %s. Please try again.\n\n", input, problemVar);
            return false;
        }

        return true;
    }

    private boolean validateBid(){
        boolean validBid = (currentBid[0] >= 1 && currentBid[0] <= 6)
                && currentBid[1] >= 1
                && (currentBid[1] > lastBid[1] || (currentBid[1] == lastBid[1] && currentBid[0] > lastBid[0]));

        if(!validBid){
            System.out.printf("\nA bid of %d [%d]'s is not currently possible.\n", currentBid[1], currentBid[0]);
            System.out.println("The next bid must have:");
            if(currentBid[0] < 1 || currentBid[0] > 6 || currentBid[1] <= 0) {
                System.out.print("A real bid...\n" +
                        "which is ");
            }

            if(lastBid[0] != 0 && lastBid[0] < 6){
                System.out.printf("%d dice, higher than a [%d]\n", lastBid[1], lastBid[0]);
                System.out.println("OR");
            }
            System.out.printf("%d or more dice of any value.\n\n", lastBid[1] + 1);
        }

        return validBid;
    }

    private void accuse(Player accuser, Player accused){
        System.out.printf("\n%s has been called out by %s! But who's lying?\n", accused.name, accuser.name);
        CLI.pause();

        showTable();
        System.out.printf("%s bid %d [%d]'s...\n", accused.name, currentBid[1], currentBid[0]);

        if(currentBid[1] <= tableDice.get(currentBid[0])){
            System.out.printf("...and there are %d [%d]'s!\n", tableDice.get(currentBid[0]), currentBid[0]);
            System.out.printf("\n%s is innocent! For shame, %s.\n", accused.name, accuser.name);

            punishment(accuser);
        }
        else{
            System.out.printf("...but there were only %d %d's...\n", tableDice.get(currentBid[0]), currentBid[0]);
            System.out.printf("\nLooks like %s was being shifty after all... I never trusted'em either.\n",
                    accused.name);

            currentPlayer = players.indexOf(accused);
            punishment(accused);
        }
    }

    private void punishment(Player damnDirtyLiar){
        damnDirtyLiar.cup.removeDie();
        totalDiceInPlay--;
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
        }
    }

    private void updateTable(){
        for(Player player : players){
            int[] dieValues = player.cup.parseCup();
            for(int faceUpValue : dieValues){
                tableDice.put(faceUpValue, tableDice.get(faceUpValue) + 1);
            }
        }
    }

    private void showTable(){
        System.out.println("\nDice in play:");
        for(int faceValue : tableDice.keySet()){
            System.out.printf("[%d]'s: %d\n", faceValue, tableDice.get(faceValue));
        }
    }

    protected void displayResults(){
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
