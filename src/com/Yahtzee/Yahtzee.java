package com.Yahtzee;

import com.Game.Game;
import com.Utilities.CLI;
import com.Utilities.InputValidator;
import com.company.DieGUI;
import com.company.Player;

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

public class Yahtzee extends Game {
    private static final DieGUI dieGUI = new DieGUI();

    public Yahtzee(){

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

    protected void round(){
        for(Player activePlayer : players){
            System.out.printf("\n- %s's turn -\n", activePlayer.name);
            CLI.pause();
            turn(activePlayer);
        }
    }

    protected void turn(Player activePlayer){
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

        /*for testing joker rules
        int[] dice = {6, 6, 6, 6, 6};
        activePlayer.scorecard.checkCombos(dice);*/

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
            else
                System.out.println("That is not a valid selection. Please try again.\n");
        }while(!validSelection);

        activePlayer.cup.roll(activePlayer.cup.parseSelections(rerolls));
    }

    private void showPlayerDice(Player player){
        dieGUI.showDice(player.cup.parseCup());
    }

    protected void displayResults(){
        Player winner = players.get(0);
        final String RADIO = "\uD83D\uDCFB";

        System.out.println("\nThat's the end of the game! Let's see who won...");
        for(Player player : players){
            player.scorecard.calculateTotalScore();
            System.out.printf("%s's Total Score: %d\n", player.name, player.scorecard.totalScore);

            // If the player has a higher score than the winner, they are the new winner
            if(players.indexOf(player) == 0)
                winner = player;
            else if(player.scorecard.totalScore > winner.scorecard.totalScore)
                winner = player;
        }

        System.out.printf("\n%s is the winner, with %d points scored!\n", winner.name, winner.scorecard.totalScore);


        System.out.println("\n\nThanks for playing, and congratulations!11!!!1\n");

        System.out.printf("\t%s♪♬ ᕕ(⌐■_■)ᕗ\n", RADIO);
    }
}
