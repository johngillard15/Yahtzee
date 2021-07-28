package com.company;

import com.HighLow.HighLow;
import com.LiarsDice.LiarsDice;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Which game do you want to play?");
        System.out.println("1. HighLow");
        System.out.println("2. Liar's Dice");
        System.out.println("3. Yahtzee");
        int choice = Integer.parseInt(scan.nextLine());

        switch(choice){
            case 1:
                HighLow.play();
                break;
            case 2:
                LiarsDice liarsDice = new LiarsDice();
                liarsDice.play();
                break;
            case 3:
                Yahtzee yahtzee = new Yahtzee();
                yahtzee.play();
                break;
        }
    }
}
