package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Yahtzee {
    Cup myCup = new Cup();

    public void play(){
        turn();
    }

    public void getSelections(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select the dice you want to re-roll (1-5)");
        String input = scan.nextLine();
        myCup.roll(myCup.parseSelections(input));
    }

    public void turn(){
        myCup.roll();

        for(int i = 0; i < 2; i++) {
            System.out.println(myCup.displayCup());
            getSelections();
        }

        System.out.println(myCup.displayCup());
    }
}
