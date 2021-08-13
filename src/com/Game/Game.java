package com.Game;

import com.Utilities.InputValidator;
import com.company.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    protected static final Scanner scan = new Scanner(System.in);
    protected List<Player> players = new ArrayList<>();

    public Game(){
        setup();
    }

    protected void setup(){
        int numPlayers = getPlayerCount();

        do{
            System.out.printf("\nPlayer %d, what is your name? ", players.size() + 1);
            String name = scan.nextLine().trim();

            players.add(Player.addPlayer(name));

            System.out.printf("Hello, %s.\n", name);
        }while(players.size() < numPlayers);
    }

    protected static int getPlayerCount(){
        return getPlayerCount(1, -1);
    }

    protected static int getPlayerCount(final int MAX){
        return getPlayerCount(1, MAX);
    }

    protected static int getPlayerCount(final int MIN, final int MAX){
        int numPlayers;
        boolean validNumber;
        do{
            String input;
            do{
                System.out.print("\nHow many players will there be? ");
                input = scan.nextLine();

                if(InputValidator.validateInt(input)) break;

                System.out.println("That is not a valid number value. Please try again.");
            }while(true);

            numPlayers = Integer.parseInt(input);

            validNumber = numPlayers >= MIN && numPlayers <= MAX;
            if(!validNumber){
                System.out.printf("You cannot have %d player%s.\n", numPlayers, numPlayers != 1 ? "s" : "");
                if(MAX == -1)
                    System.out.println("You need to have at least 1 player. Please try again with a valid number.");
                else
                    System.out.printf("Please pick a number between %d and %d.\n", MIN, MAX);
            }
        }while(!validNumber);

        return numPlayers;
    }

    public abstract void play();
    protected abstract void round();
    protected abstract void turn(Player activePlayer);
    protected abstract void displayResults();
}
