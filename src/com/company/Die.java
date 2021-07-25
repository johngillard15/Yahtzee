package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Die {
    public int numberOfSides;
    public int faceValue;

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
}
