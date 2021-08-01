package com.Utilities;

import java.util.Scanner;

public class CLI {
    private static final Scanner scan = new Scanner(System.in);

    public static void cls(){
        StringBuilder blankLines = new StringBuilder();
        int lines = 40;

        for(int i = 0; i < lines; i++)
            blankLines.append("\n");

        System.out.print(blankLines);
    }

    public static void pause(){
        System.out.println("Press enter to continue...\n");
        scan.nextLine();
    }
}
