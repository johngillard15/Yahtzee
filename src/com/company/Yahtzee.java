package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Yahtzee {

    public void play(){
        Cup myCup = new Cup();
        Scanner scan = new Scanner(System.in);
        String input;

        myCup.roll();
        System.out.println(myCup.displayCup());

        System.out.println("Select the dice you want to re-roll (1-5)");
        input = scan.nextLine();
        myCup.roll(myCup.parseSelections(input));
        System.out.println(myCup.displayCup());
    }
}
