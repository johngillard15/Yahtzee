package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DiceUI {
    public final int numberOfSides;
    public List<String> numberDieFaces = new ArrayList<>();
    public final String[] SIX_SIDED_DIE_FACES = {
            "┏━━━━━━━━━━━┓\n" +
            "┃           ┃\n" +
            "┃     ●     ┃\n" +
            "┃           ┃\n" +
            "┗━━━━━━━━━━━┛",
            "┏━━━━━━━━━━━┓\n" +
            "┃        ●  ┃\n" +
            "┃           ┃\n" +
            "┃  ●        ┃\n" +
            "┗━━━━━━━━━━━┛",
            "┏━━━━━━━━━━━┓\n" +
            "┃        ●  ┃\n" +
            "┃     ●     ┃\n" +
            "┃  ●        ┃\n" +
            "┗━━━━━━━━━━━┛",
            "┏━━━━━━━━━━━┓\n" +
            "┃  ●     ●  ┃\n" +
            "┃           ┃\n" +
            "┃  ●     ●  ┃\n" +
            "┗━━━━━━━━━━━┛",
            "┏━━━━━━━━━━━┓\n" +
            "┃  ●     ●  ┃\n" +
            "┃     ●     ┃\n" +
            "┃  ●     ●  ┃\n" +
            "┗━━━━━━━━━━━┛",
            "┏━━━━━━━━━━━┓\n" +
            "┃  ●     ●  ┃\n" +
            "┃  ●     ●  ┃\n" +
            "┃  ●     ●  ┃\n" +
            "┗━━━━━━━━━━━┛"
    };

    public DiceUI(){
        numberOfSides = 6;
        numberDieFaces.addAll(Arrays.asList(SIX_SIDED_DIE_FACES));
    }

    public DiceUI(int numberOfSides){
        this.numberOfSides = numberOfSides;
        createNumberDice(numberOfSides);
    }

    public void createNumberDice(int maxFaceValue){
        for(int i = 0; i < maxFaceValue; i++){
            String faceValue = Integer.toString(i + i);
            if(Integer.parseInt(faceValue) < 10)
                faceValue += " ";

            numberDieFaces.add(
                    String.format(
                            "┌─────┐\n" +
                            "│  %s │\n" +
                            "└─────┘",
                            faceValue
                    )
            );
        }
    }

    public void showDie(int faceValue){
        System.out.println(numberDieFaces.get(faceValue - 1));
    }

    public void showDice(int ...dice){ // refactor name to describe printing dice on same line
        List<Scanner> scannerList = new ArrayList<>();
        for(int faceValue : dice)
            scannerList.add(new Scanner(numberDieFaces.get(faceValue - 1)));

        while(scannerList.get(0).hasNextLine()){
            StringBuilder line = new StringBuilder();
            for(Scanner scanner : scannerList)
                line.append(scanner.nextLine()).append(" ");
            System.out.println(line);
        }

        for(Scanner scanner : scannerList)
            scanner.close();
    }

    public void showDice(List<Die> dice){
        int[] dieArr = new int[dice.size()];
        for(int i = 0; i < dieArr.length; i++)
            dieArr[i] = dice.get(i).faceValue;

        showDice(dieArr);
    }
}
