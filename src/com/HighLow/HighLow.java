package com.HighLow;

import com.company.Die;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HighLow {

    public static void play(){
        Scanner scanner = new Scanner(System.in);
        List<Die> dice = new ArrayList<>(); // Create list of dice

        System.out.print("How many sides will the dice have? ");
        int numberOfSides = scanner.nextInt();
        System.out.print("How many dice do you want to roll? ");
        int numOfDice = scanner.nextInt();

        // add numOfDice dice to list
        if(numberOfSides == 6){
            for(int i = 0; i < numOfDice; i++)
                dice.add(new Die());
        }
        else{
            for(int i = 0; i < numOfDice; i++)
                dice.add(new Die(numberOfSides));
        }

        // roll all dice in list
        for (Die die : dice)
            die.roll();

        int total1 = 0, total2 = 0;
        for (Die die : dice)
            total1 += die.getFaceValue();


        // Print initial roll and ask user for guess
        dice.get(0).showDice(dice);
        System.out.println("Total = " + total1);

        // replay until player loses; also tracks total wins
        int wins = 0;
        boolean isWinner = true;
        do{
            System.out.println("Will the next roll be (h)igher or (l)ower?");
            System.out.print("Your guess: ");
            String guess = scanner.next();

            // Second roll
            for (Die die : dice)
                die.roll();

            for (Die die : dice){
                total2 += die.getFaceValue();
            }

            // Print second roll and determine winning/losing message
            dice.get(0).showDice(dice);
            System.out.println("This Roll = " + total2);

            if (total1 == total2){
                System.out.println("Same number, DRAW");
            }
            else{
                boolean isNewRollHigher = total2 > total1;
                switch (guess) {
                    case "h":
                        System.out.println(isNewRollHigher ? "Number is higher you WIN" : "Number is lower you LOSE");
                        isWinner = isNewRollHigher;
                        break;
                    case "l":
                        System.out.println(isNewRollHigher ? "Number is higher you LOSE" : "Number is lower you WIN");
                        isWinner = !isNewRollHigher;
                }
            }

            // if isWinner is true, set up variables for next round
            if(total1 == total2){
                System.out.println("Oh well. Next Round!\n");
            }
            else if(isWinner){
                System.out.println("Nice one! Let's play again!");
                wins++;
                System.out.println("Win streak: " + wins + "\n");
            }
            else {
                System.out.println("\nNot so nice... Better luck next time!");
                System.out.println("Total wins: " + wins);
            }
            total1 = total2;
            total2 = 0;
        }while(isWinner);

        scanner.close();
    }
}
