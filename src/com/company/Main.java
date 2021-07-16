package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();

        int total, newTotal;
        String guess;

        die1.roll();
        die2.roll();
        total = die1.getFaceValue() + die2.getFaceValue();
        System.out.println(die1.getFaceValue() + " + "  + die2.getFaceValue() + " = " + total);
        System.out.println("Will the next number be (h)igher or (l)ower?");
        guess = scan.next();

        die1.roll();
        die2.roll();
        newTotal = die1.getFaceValue() + die2.getFaceValue();
        if(total == newTotal)
            System.out.println("same number you lose");

        boolean isLarger = newTotal > total;
        System.out.println(die1.getFaceValue() + " + "  + die2.getFaceValue() + " = " + newTotal);
        switch(guess){
            case "h":
                System.out.println(isLarger ? "Number is higher you WIN" : "Number is lower you LOSE");
                break;
            case "l":
                System.out.println(isLarger ? "Number is lower you WIN" : "Number is higher you LOSE");
        }

        scan.close();
    }
}
