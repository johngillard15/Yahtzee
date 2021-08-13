package com.Utilities;

/**
 * <p>This class contains methods that handle runtime exceptions involving user input, mainly involving type conversions
 * from String to a number format (Integer, Double, etc.).</p>
 *
 * @author John Gillard
 * @since 13/8/2021
 */

public class InputValidator {
    /**
     * <p>Attempts to convert the value of the passed parameter to an Integer value.</p>
     *
     * @param checkMe the String to convert
     * @return a boolean, which determines whether the value can be converted to an Integer
     *
     * @throws NumberFormatException if the specified String value cannot be converted to an Integer
     */
    public static boolean validateInt(String checkMe){
        try{
            Integer.parseInt(checkMe);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}
