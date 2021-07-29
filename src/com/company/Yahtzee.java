package com.company;

import java.util.*;


/*
second yahtzee is joker which gives 100 bonus points to yahtzee
calculate bonuses
 */

public class Yahtzee {
    private final Scanner scan = new Scanner(System.in);
    private final int ROUNDS = 3;
    private final int MIN_PLAYERS = 1;
    private final int MAX_PLAYERS = 5;

    public List<Player> players = new ArrayList<>();

    public Yahtzee(){
        int numPlayers = Player.getPlayerCount(MIN_PLAYERS, MAX_PLAYERS);

        int currentPlayer = 1;
        while(players.size() < numPlayers){
            System.out.printf("Player %d, what is your name? ", currentPlayer);
            players.add(Player.addPlayer(scan.nextLine().trim()));
            currentPlayer++;
        }
    }

    public void play(){
        for(int round = 1; round <= ROUNDS; round++){
            System.out.printf("\n-- Round %d --\n", round);
            round();
        }
        displayResults();
    }

    private void round(){
        for(Player activePlayer : players){
            System.out.printf("%s turn, press enter to continue...", activePlayer.name + "'s");
            scan.nextLine();
            turn(activePlayer);
        }
    }

    public void turn(Player activePlayer){
        activePlayer.cup.roll();

        for(int i = 0; i < 2; i++) {
            System.out.println(activePlayer.cup.displayCup());
            getSelections(activePlayer);
        }

        System.out.println(activePlayer.cup.displayCup());
        System.out.printf("Round total: %d\n\n", activePlayer.updateScore());
    }

    public void getSelections(Player activePlayer){
        System.out.println("Select the dice you want to re-roll (1-5)");
        String rerolls = scan.nextLine();

        if(rerolls.equals("")) return;

        activePlayer.cup.roll(activePlayer.cup.parseSelections(rerolls));
    }

    public void displayResults(){
        Player currentWinner = players.get(0);
        for(Player activePlayer : players){
            if(activePlayer.score > currentWinner.score)
                currentWinner = activePlayer;
        }

        System.out.printf("%s is the winner!\n", currentWinner.name);
        System.out.printf("%s's total score: %d\n", currentWinner.name, currentWinner.score);
    }
}
