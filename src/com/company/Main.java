package com.company;

import com.HighLow.HighLow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Yahtzee game = new Yahtzee();
//        game.play();
        Player will = new Player("Will");
        will.cup.roll();
        System.out.println(will.cup.displayCup());
        System.out.println(will.updateScore());
        System.out.println(will.score);
        will.cup.roll();
        System.out.println(will.cup.displayCup());
        System.out.println(will.updateScore());
        System.out.println(will.score);
    }
}
