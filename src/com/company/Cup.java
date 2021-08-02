package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cup {
    public List<Die> dice = new ArrayList<>();
    private final int MAX_DIE = 5;

    public Cup(){
        while(dice.size() < MAX_DIE)
            addDie();
    }

    public Cup(int diceAmount){
        while(dice.size() < diceAmount)
            addDie();
    }

    public void addDie(){
        dice.add(Die.createDie());
    }

    public void removeDie(){
        dice.remove(0);
    }

    public void removeDie(int index){
        dice.remove(index);
    }

    public void sort(){
        Collections.sort(this.dice);
    }

    public void roll(){
        for(Die die : dice)
            die.roll();
    }

    public void roll(int selection){
        dice.get(selection).roll();
    }

    public void roll(List<Integer> selections){
        for(int selection : selections)
            roll(selection);
    }
    public void roll(int... selections){
        for(int selection : selections)
            roll(selection);
    }

    public List<Integer> parseSelections(String input){
        String[] inputArr = input.split(" ");

        List<Integer> selections = new ArrayList<>();
        for(String number : inputArr)
            selections.add(Integer.parseInt(number) - 1);

        return selections;
    }
//    public int[] parseSelections(String input){
//        String[] inputArr = input.split(" ");
//
//        int[] selections = new int[inputArr.length];
//        for(int i = 0; i < selections.length; i++)
//            selections[i] = Integer.parseInt(inputArr[i]);
//
//        return selections;
//    }

    public int[] parseCup(){
        int[] dieArr = new int[dice.size()];
        for(int i = 0; i < dieArr.length; i++)
            dieArr[i] = dice.get(i).faceValue;

        return dieArr;
    }

    public String displayCup(){
        String output = "";
        for(Die die : dice)
            output += die.faceValue + " ";

        return output.trim();
    }
}
