package com.Utilities;

import java.util.Scanner;

/**
 * <p>Provides tools that help to improve the output of a terminal based program.</p>
 *
 * @since 1/8/2021
 * @author John Gillard
 * @version 1/8/2021
 */

public class CLI {
    private static final Scanner scan = new Scanner(System.in);

    /**
     * <p>Simply prints 40 blank lines to mimic a clear screen function.</p>
     */
    public static void cls(){
        StringBuilder blankLines = new StringBuilder();
        int lines = 40;

        for(int i = 0; i < lines; i++)
            blankLines.append("\n");

        System.out.print(blankLines);
    }

    /**
     * <p>Uses Scanner to wait for user input to continue program execution.</p>
     */
    public static void pause(){
        System.out.println("Press enter to continue...\n");
        scan.nextLine();
    }
}
