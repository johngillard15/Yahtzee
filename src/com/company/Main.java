package com.company;

import com.Game.Game;
import com.HighLow.HighLow;
import com.LiarsDice.LiarsDice;
import com.Yahtzee.Yahtzee;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Which game do you want to play?");
        System.out.println("1. HighLow");
        System.out.println("2. Liar's Dice");
        System.out.println("3. Yahtzee");

        int choice;
        do{
            System.out.print("choice: ");
            String input = scan.nextLine();
            try{
                choice = Integer.parseInt(input);
                if(choice == 1 || choice == 2 || choice == 3)
                    break;
                else
                    System.out.printf("\"%d\" is not a valid choice. Please pick a number between 1 and 3.\n", choice);
            }
            catch(NumberFormatException e){
                System.out.printf("\"%s\" is not a valid choice. Please try again.\n", input);
            }
        }while(true);

        switch(choice){
            case 1:
                HighLow.play();
                break;
            case 2:
                Game liarsDice = new LiarsDice();
                liarsDice.play();
                break;
            case 3:
                Game yahtzee = new Yahtzee();
                yahtzee.play();
                break;
        }
    }
}
