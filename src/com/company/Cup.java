package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    List<Die> dice = new ArrayList<>();

    public Cup(){
        while(dice.size() < 5)
            dice.add(new Die());
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

    public String displayCup(){
        String output = "";

        for(Die die : dice)
            output += die.faceValue + " ";

        return output.trim();
    }

    public List<Integer> parseSelections(String input){
        String[] inputArr = input.split(" ");

        List<Integer> selections = new ArrayList<>();
        for(String number : inputArr)
            selections.add(Integer.parseInt(number) - 1);

        return selections;
    }
}
