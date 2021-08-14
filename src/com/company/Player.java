package com.company;

import com.Yahtzee.ScoreCard;

import java.util.Scanner;

public class Player {
    public String name;
    public Cup cup;
    public int score = 0;
    public ScoreCard scorecard = new ScoreCard();

    public Player(String name){
        this.name = name;
        cup = new Cup();
    }

    public Player(String name, int startingDice){
        this.name = name;
        cup = new Cup(startingDice);
    }

    public static Player addPlayer(String name){
        return new Player(name);
    }
    public static Player addPlayer(String name, int startingDice){
        return new Player(name, startingDice);
    }
}
