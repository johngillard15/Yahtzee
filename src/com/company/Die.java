package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Die {
    public int numberOfSides;
    public int faceValue;
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
    // thinner boxes
    /*public final String[] SIX_SIDED_DIE_FACES = {
            "┏━━━━━━━━━┓\n" +
            "┃         ┃\n" +
            "┃    ●    ┃\n" +
            "┃         ┃\n" +
            "┗━━━━━━━━━┛",
            "┏━━━━━━━━━┓\n" +
            "┃      ●  ┃\n" +
            "┃         ┃\n" +
            "┃  ●      ┃\n" +
            "┗━━━━━━━━━┛",
            "┏━━━━━━━━━┓\n" +
            "┃      ●  ┃\n" +
            "┃    ●    ┃\n" +
            "┃  ●      ┃\n" +
            "┗━━━━━━━━━┛",
            "┏━━━━━━━━━┓\n" +
            "┃  ●   ●  ┃\n" +
            "┃         ┃\n" +
            "┃  ●   ●  ┃\n" +
            "┗━━━━━━━━━┛",
            "┏━━━━━━━━━┓\n" +
            "┃  ●   ●  ┃\n" +
            "┃    ●    ┃\n" +
            "┃  ●   ●  ┃\n" +
            "┗━━━━━━━━━┛",
            "┏━━━━━━━━━┓\n" +
            "┃  ●   ●  ┃\n" +
            "┃  ●   ●  ┃\n" +
            "┃  ●   ●  ┃\n" +
            "┗━━━━━━━━━┛"
    };*/

    public Die(){
        numberOfSides = 6;
    }

    public Die(int numberOfSides){
        this.numberOfSides = numberOfSides;
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
            scannerList.add(new Scanner(SIX_SIDED_DIE_FACES[die.getFaceValue() - 1]));

        while(scannerList.get(0).hasNextLine()){
            StringBuffer line = new StringBuffer();
            for(Scanner scanner : scannerList)
                line.append(scanner.nextLine());
            System.out.println(line);
        }

        for(Scanner scanner : scannerList)
            scanner.close();
    }
}
