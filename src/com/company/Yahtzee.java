package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Yahtzee {
    private final Scanner scan = new Scanner(System.in);
    //public Cup myCup = new Cup();
    public Player player;

    public Yahtzee(){
        System.out.println("What is your name? ");
        player = new Player(scan.nextLine().trim());
    }

    // ✓ TODO refactor play to run 5 turns then display total score
    public void play(){
        for(int turn = 1; turn <= 5; turn++) {
            System.out.printf("\n-- Turn %d --\n", turn);
            turn();
        }
    }

    public void getSelections(){
        System.out.println("Select the dice you want to re-roll (1-5)");
        String input = scan.nextLine();
        player.cup.roll(player.cup.parseSelections(input));
    }

    // ✓ TODO refactor turn to update score and display round score *(and total score)*
    public void turn(){
        player.cup.roll();

        for(int i = 0; i < 2; i++) {
            System.out.println(player.cup.displayCup());
            getSelections();
        }

        System.out.println(player.cup.displayCup());
        System.out.println("Round total: " + player.updateScore());
        System.out.println("Total score: " + player.score);
    }
}
