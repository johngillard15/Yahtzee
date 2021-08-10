package com.LiarsDice;

import com.Utilities.CLI;
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
 * @version 5/9/2021
 */

/*
 * Liar's Dice game flow:
 *
 * play(); loop through rounds until there is only one player left
 * round(); loop through players until an accusation is made
 * turn(); player chooses to bid or accuse, if bid player provides the die value and the amount to bet
 * challenge(Player accuser, Player accused); determine the liar and send them to punishment()
 * punishment(Player liar); dish out punishment (remove die/kick out of game)
 * updateTable(); loop through player list and update the tableDice map; remember to reset after round
 *
 * play game
 * 1. game runs through rounds until there is only one player left
 *
 * round
 * 1. roll everyone's dice
 * 2. loop through players and get bids
 * 3. get bids until a player accuses previous player of lying
 * 4. take them to court -> challenge
 *
 * turn
 * 1. ask player if they would like to bid or accuse
 * 2. if bidding, ask for bid (value and amount)
 * 3. next bids must either bid a higher amount of any value, or the same amount of higher value
 * 4. if accusing, initiate challenge sequence
 *
 * challenge
 * 1. count total occurrences of all face values (in tableDice map)
 * 2. check if player's bid was possible (amount >= all die on table)
 * 3. if bid was matched or exceeded (determined by the tableDice HashMap), mark accuser as liar;
 *  if bid was not possible, mark bidder as liar
 * 4. liars get 1 die removed from their die count (remove an element from dice list in player.cup), or get removed
 * from the game if they only have 1 die at time of punishment (remove that player from player list)
 * 5. if the player list has only 1 element, end the game and declare the winner
 */

public class LiarsDice {
    private static final Scanner scan = new Scanner(System.in);
    private static final DieGUI dieGUI = new DieGUI();
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 5;

    private List<Player> players = new ArrayList<>();
    private Map<Integer, Integer> tableDice = new HashMap<>();
    private int totalDiceInPlay;
    private int[] currentBid = new int[2]; // value, amount
    private int[] lastBid = new int[2];
    private int currentPlayer = 0;
    private int currentRound = 1;
    private int currentTurn = 1;
    private boolean challenge = false;

    // TODO: now get the probability of each bid
    // TODO: maybe keep track of previous bids to help make future bids
    public LiarsDice(){
        System.out.println("\nHow many dice will each player start with?");
        System.out.print("starting dice: ");
        int startingDice = Integer.parseInt(scan.nextLine()); // TODO: validate number format and valid amounts

        int numPlayers = Player.getPlayerCount(MIN_PLAYERS, MAX_PLAYERS);

        int currentPlayer = 1;
        do{
            System.out.printf("\nPlayer %d, what is your name? ", currentPlayer);
            String name = scan.nextLine().trim();
            players.add(Player.addPlayer(name, startingDice));
            System.out.printf("Hello, %s.\n", name);
            currentPlayer++;
        }while(players.size() < numPlayers);

        for(int faceValue = 1; faceValue <= 6; faceValue++)
            tableDice.put(faceValue, 0);

        totalDiceInPlay = numPlayers * startingDice;
    }

    public void play(){
        do{
            round();
        }while(players.size() > 1);
        displayResults();
    }

    private void rollAll(){
        for(Player player : players){
            player.cup.roll();
        }
    }

    private void round(){ // TODO: try to use modulo to iterate through players
        CLI.cls();
        System.out.printf("\n-- ROUND %d --", currentRound);

        rollAll();
        updateTable();

        do{
            if(currentTurn != 1)
                CLI.cls();

            System.out.printf("\n- %s's turn -\n", players.get(currentPlayer).name);
            CLI.pause();

            turn(players.get(currentPlayer));
            if(challenge){
                int lastPlayer = currentPlayer == 0
                        ? players.size() - 1
                        : currentPlayer - 1;
                accuse(players.get(currentPlayer), players.get(lastPlayer));
            }

            if(currentPlayer == players.size() - 1)
                currentPlayer = 0;
            else
                currentPlayer++;
        }while(!challenge);

        endRound();
    }

    private void endRound(){
        challenge = false;

//        for(int faceValue : tableDice.keySet())
//            tableDice.put(faceValue, 0);
        tableDice.replaceAll((key, value) -> 0);

        Arrays.fill(currentBid, 0);
        Arrays.fill(lastBid, 0);

        currentTurn = 1;
        currentRound++;

        System.out.print("\n- End of round -\n");
        CLI.pause();
    }

    private void turn(Player activePlayer){
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
        boolean validBid = currentBid[1] > lastBid[1]
                || (currentBid[1] == lastBid[1] && currentBid[0] > lastBid[0]);

        if(!validBid){
            System.out.printf("\nA bid of %d [%d]'s is not currently possible.\n", currentBid[1], currentBid[0]);
            System.out.println("The next bid must have:");
            if(lastBid[0] < 6){
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

            if(accuser.cup.dice.size() == 1)
                currentPlayer = players.indexOf(accused) - 1;
            else
                currentPlayer--;
            punishment(accuser);
        }
        else{
            System.out.printf("...but there were only %d %d's...\n", tableDice.get(currentBid[0]), currentBid[0]);
            System.out.printf("\nLooks like %s was being shifty after all... I never trusted'em either.\n",
                    accused.name);

            currentPlayer = players.indexOf(accused) - 1;
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
