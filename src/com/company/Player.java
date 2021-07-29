package com.company;

import java.util.Scanner;

public class Player {
    public String name;
    public Cup cup = new Cup();
    public int score = 0;
    public ScoreCard scorecard = new ScoreCard();

    public Player(String name){
        this.name = name;
    }

    public static Player addPlayer(String name){
        return new Player(name);
    }

    public static int getPlayerCount(int MIN, int MAX){
        Scanner scan = new Scanner(System.in);

        int numPlayers;
        boolean validNumber = false;
        do{
            System.out.print("How many players will there be? ");
            numPlayers = Integer.parseInt(scan.nextLine());
            validNumber = numPlayers >= MIN && numPlayers <= MAX;

            if(!validNumber){
                System.out.printf("You cannot have %d player%s.\n", numPlayers, numPlayers != 1 ? "s" : "");
                System.out.printf("Please pick a number between %d and %d.\n", MIN, MAX);
            }
        }while(!validNumber);

        return numPlayers;
    }

    public int updateScore(){
        int roundTotal = 0;
        for(Die die : cup.dice)
            roundTotal += die.faceValue;
        score += roundTotal;

        return roundTotal;
    }
}
