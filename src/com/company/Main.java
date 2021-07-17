package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void game(){
        // Initialize variables
        Scanner scan = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        int total1, total2;
        String guess;

        // Initial roll
        die1.roll();
        die2.roll();
        total1 = die1.getFaceValue() + die2.getFaceValue();

        // Print initial roll and ask user for guess
        System.out.println(die1.getFaceValue() + " + "  + die2.getFaceValue() + " = " + total1);
        System.out.println("Will the next number be (h)igher or (l)ower?");
        System.out.print("Your guess: ");
        guess = scan.next();

        // Second roll
        die1.roll();
        die2.roll();
        total2 = die1.getFaceValue() + die2.getFaceValue();

        // Print second roll and determine winning/losing message
        System.out.println(die1.getFaceValue() + " + "  + die2.getFaceValue() + " = " + total2);
        if(total1 == total2){
            System.out.println("same number you lose lol");
        }
        else{
            boolean isLarger = total2 > total1; // true = total2 is larger
            switch (guess){
                case "h":
                    System.out.println(isLarger ? "Number is higher you WIN" : "Number is lower you LOSE");
                    break;
                case "l":
                    System.out.println(isLarger ? "Number is higher you LOSE" : "Number is lower you WIN");
            }
        }


        scan.close();
    }

    public static void main(String[] args) {
        // repeat game as long as player is a winner
        //game();

        List<Die> dice = new ArrayList<>(); // Create list of dice
        int numOfDice = 2; // number of dice to roll

        // add numOfDice dice to list
        for(int i = 0; i < numOfDice; i++){
            dice.add(new Die());
        }
        // roll all dice in list
        for(Die die : dice) {
            die.roll();
        }
        // show all die rolls in console
        dice.get(0).showDice(dice);
    }
}
