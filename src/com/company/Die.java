package com.company;

public class Die implements Comparable<Die>{
    public int numberOfSides;
    public int faceValue;

    public Die(){
        numberOfSides = 6;
    }

    public Die(int numberOfSides){
        this.numberOfSides = numberOfSides;
    }

    public static Die createDie(){
        return new Die();
    }

    public static Die createDie(int numberOfSides){
        return new Die(numberOfSides);
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

    @Override
    public int compareTo(Die die) {
        return this.faceValue - die.faceValue;
    }
}
