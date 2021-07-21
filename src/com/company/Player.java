package com.company;

public class Player {
    public String name;
    public Cup cup = new Cup();
    public int score = 0;

    public Player(String name){
        this.name = name;
    }

    public int updateScore(){
        int roundTotal = 0;
        for(Die die : cup.dice)
            roundTotal += die.faceValue;
        score += roundTotal;

        return roundTotal;
    }
}
