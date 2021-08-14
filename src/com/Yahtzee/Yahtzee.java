package com.Yahtzee;

import com.Utilities.CLI;
import com.Utilities.InputValidator;
import com.company.DieGUI;
import com.company.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>Yahtzee</h1>
 *
 * <p>My Yahtzee</p>
 *
 * <br>
 *
 * @since 28/7/2021
 * @author John Gillard
 * @version 5/13/2021
 */

/* Yahtzee game flow:
 *
 * public Yahtzee
 * play(); play for 13 rounds(until last player's scorecard is filled)
 * round(); loop through all players
 * turn(); initial dice roll, player can re-roll 2 more times, pick score to put points
 *
 * public ScoreCard
 * HashMap<String, Integer> scoreSheet; every player will have a scoresheet to keep a tally of their scores
 * calculateScore(Player activePlayer, String scoreKey); takes in the current player and the key to the score they chose
 * getPossibleCombos();
 * showPossibleCombos();
 *
 * -------------------------
 * play game
 * 1. game goes for 13 rounds (until last player's scorecards is filled)
 *
 * round
 * 1. loop through all players
 *
 * turn
 * 1. initial dice roll
 * 2. ask player if they want to re-roll some or all dice (up to 2 times)
 * 3. determine what scores are possible based on current roll and the player's scorecard
 * 4. ask player which score they would like to contribute to
 * 5. send player over to calculateScore method along with score selection
 *
 * showing combos
 * 1. print player's scoresheet at start of turn
 * 2. after they have made all rolls, determine what combos are possible with current roll (excluding scores already
 * achieved), maybe put these in a list;
 * 3. initialize combo values to -1 and set to 0 if it was used but not scored
 * 4. chance is always available until used
 *
 * calculating combos
 * 0. any rolls that cannot be applied to the available combos are forced to mark one with a 0
 * upper
 * 1. upper scores (1-6) are the sum of the dice
 * 2. if the sum of all upper values is at least 63, add a bonus of 35 points
 * lower
 * 3. three/four-of-a-kind require 3/4 of the same face, and the score is the total of all dice
 * 4. full house has one pair and 3 of a kind; scores 25 points
 * 5. small straight requires at least 4 consecutive die (1-4, 2-5, 3-6), worth 30 points
 * 6. large straight requires 5 consecutive die (1-5 or 2-6), worth 40 points
 * 7. chance is just the sum of the current roll
 * yahtzee
 * 8. yahtzee is five-of-a-kind, and awards 50 points
 * 9. subsequent Yahtzee's (jokers) are awarded 100 points instead of 50;
 *  - if the corresponding upper score is not filled, use the roll to fill that score in addition to the joker bonus
 *  - if any lower score has not been filled, mark it as so along with the joker bonus
 *
 */

// TODO: now try to sort dice list to help player see combos

public class Yahtzee {
    private static final Scanner scan = new Scanner(System.in);
    private static final DieGUI dieGUI = new DieGUI();
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 5;
    private final List<Player> players = new ArrayList<>();

    public Yahtzee(){
        int numPlayers = Player.getPlayerCount(MIN_PLAYERS, MAX_PLAYERS);

        int currentPlayer = 1;
        while(players.size() < numPlayers){
            System.out.printf("Player %d, what is your name? ", currentPlayer);
            String name = scan.nextLine().trim();
            players.add(Player.addPlayer(name));
            System.out.printf("Hello, %s.\n", name);
            currentPlayer++;
        }
    }

    public void play(){
        for(int round = 1; round <= 13; round++){
            System.out.printf("\n-- Round %d --", round);
            if(round == 13)
                System.out.println("\nFINAL ROUND");
            round();
        }

        displayResults();
    }

    private void round(){
        for(Player activePlayer : players){
            System.out.printf("\n- %s's turn -\n", activePlayer.name);
            CLI.pause();
            turn(activePlayer);
        }
    }

    private void turn(Player activePlayer){
        activePlayer.scorecard.showScorecard();

        activePlayer.cup.roll();
        System.out.println("\n- Initial roll -");
        activePlayer.cup.sort();
        showPlayerDice(activePlayer);

        for(int i = 0; i < 2; i++){
            System.out.printf("- Re-roll %d\n", i + 1);
            getSelections(activePlayer);
            activePlayer.cup.sort();
            showPlayerDice(activePlayer);
        }

        activePlayer.scorecard.checkCombos(activePlayer.cup.parseCup());
        activePlayer.scorecard.showPossibleCombos();
        activePlayer.scorecard.getPlayerChoice();

    }

    private void getSelections(Player activePlayer){
        String rerolls;
        boolean validSelection = false;
        do{
            System.out.print("Select the dice you want to re-roll (1-5) ");
            rerolls = scan.nextLine().trim();

            if(rerolls.equals("")){
                System.out.println("-No Roll-");
                return;
            }

            // remove all whitespace from input,
            // and if there is a problem parsing it as an integer, it can't be a valid selection
            if(InputValidator.validateInt(rerolls.replaceAll("\\s+", "")))
                validSelection = true;

            System.out.println("That is not a valid selection. Please try again.\n");
        }while(!validSelection);

        activePlayer.cup.roll(activePlayer.cup.parseSelections(rerolls));
    }

    private void showPlayerDice(Player player){
        dieGUI.showDice(player.cup.parseCup());
    }

    private void displayResults(){
        Player winner = players.get(0);
        final String RADIO = "\uD83D\uDCFB";

        System.out.println("\nThat's the end of the game! Let's see who won...");
        for(Player player : players){
            player.scorecard.calculateTotalScore();
            System.out.printf("%s's Total Score: %d\n", player.name, player.scorecard.totalScore);

            /*
             * If the player has a higher score than the winner, they are the new winner
             */
            if(players.indexOf(player) == 0)
                winner = player;
            else if(player.scorecard.totalScore > winner.scorecard.totalScore){
                winner = player;
            }
        }

        System.out.printf("\n%s is the winner, with %d points scored!\n", winner.name, winner.scorecard.totalScore);

        System.out.println("\n\nThanks for playing, and congratulations!11!!!1\n\t");
        System.out.printf("\t%s♪♬ ᕕ(⌐■_■)ᕗ\n", RADIO);
    }
}
