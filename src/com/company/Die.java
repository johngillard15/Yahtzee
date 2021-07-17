package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Die {
    public int numberOfSides;
    public int faceValue;
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

    public Die(){
        numberOfSides = 6;
        numberDieFaces.addAll(Arrays.asList(SIX_SIDED_DIE_FACES));
    }

    public Die(int numberOfSides){
        this.numberOfSides = numberOfSides;

        for(int i = 0; i < numberOfSides; i++){
            if(i + 1 < 10){
                numberDieFaces.add(
                        String.format(
                                "┌─────┐\n" +
                                "│  %d  │\n" +
                                "└─────┘",
                                i + 1
                        )
                );
            }
            else{
                numberDieFaces.add(
                        String.format(
                                "┌─────┐\n" +
                                "│  %d │\n" +
                                "└─────┘",
                                i + 1
                        )
                );
            }
        }
    }

    public void roll(){
        setFaceValue((int)(Math.random() * numberOfSides) + 1);
    }

    public int getFaceValue(){
        return faceValue;
    }

    public void setFaceValue(int faceValue){
        this.faceValue = faceValue;
    }

    public void showDice(List<Die> dice){
        List<Scanner> scannerList = new ArrayList<>();
        for(Die die : dice)
            scannerList.add(new Scanner(numberDieFaces.get(die.getFaceValue() - 1)));

        while(scannerList.get(0).hasNextLine()){
            StringBuilder line = new StringBuilder();
            for(Scanner scanner : scannerList)
                line.append(scanner.nextLine()).append(" ");
            System.out.println(line);
        }

        for(Scanner scanner : scannerList)
            scanner.close();
    }
}
