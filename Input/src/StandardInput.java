import java.util.Scanner;
import java.util.IllegalFormatException;

/**
 * Input Class
 * @author Matthew Davis
 * Date: 1/10/2020
 * File name: Input.java
 * Purpose:
 *      To provide static, adaptable methods for user input via the CLI, that dont use regex
 *      (my unit coordinator hates packages -_-)
 * Assumptions:
 * - System.in InputStream should be used for user input
 */

public class StandardInput{
    static Scanner inputStream = new Scanner(System.in);

    /**
     * Runs unit tests on class methods
     */
    public static void main(String[] args){
        inputStream = new Scanner("asd asd\nasd\n");
        assert word().equals("asd");
        
        inputStream = new Scanner("asd asd asd\nasd dsa\n");
        String[] res = word(2);
        assert res[0].equals("asd") && res[1].equals("dsa");
        
        inputStream = new Scanner("1\n");
        assert formattedInput("%d").equals("1");
        
    }
    
    /**
     * Gets integer input from a user
     */
    public static int integer(){
        int input = 0;
        boolean badInput = true;
        while(badInput){
            try{
                input = inputStream.nextInt();  // nextInt() does all the input validation required
                badInput = false;
            }
            catch(Exception e) {
                System.out.print("Thats not an integer :/\nTry again :)\n>>> ");
                inputStream.next(); // remove /n character from buffer that causes infinite loop
            }
        }
        return input;
    }
    
    /*
    *   Use printf-like syntax to define expected input type/s
    *   %s - whole line
    *   %d - integer
    *   %f - float
    *   %c - character
    *   Will return a space-separated String of matching arguments (except for %s, where the string is literal)
    */
    public static String formattedInput(String format){
        String res = new String();
        boolean badInput = true;
        while(badInput){
            String input = inputStream.nextLine().trim(); // get line of input
            try{
                res = String.format(input, format); // attempt to fit the input to the format
                badInput = false;
            }
            catch(IllegalFormatException e){
                e.printStackTrace();
            }
        }
        return res;
    }
    
    /*
    *   Gets a single word
    *   Because Java's printf-like Formatter syntax can't capture 'words' (space separated substrings)
    */
    public static String word(){
        boolean badInput = true;
        String[] res = new String[0];
        while(badInput){
            res = formattedInput("%s").split(" ");
            if(res.length == 1){
                badInput = false;
            }
            else{
                System.out.print("Invalid input, please try again\n>>> ");
            }
        }
        return res[0];
    }
    
    /*
    *   Gets one or more words
    *   Because Java's printf-like Formatter syntax can't capture 'words' (space separated substrings)
    *   Index represents number of words to be expected and captured
    */
    public static String[] word(int argNumber){
        boolean badInput = true;
        String[] res = new String[0];
        while(badInput){
            res = formattedInput("%s").split(" ");
            if(res.length == argNumber){
                badInput = false;
            }
            else{
                System.out.print("Invalid input, please try again\n>>> ");
            }
        }
        return res;
    }
    
    /**
     * Counts the number of a specific character in a string
     */
    private static int countChars(String inc, char target){
        char[] chars = inc.toCharArray();
        int count = 0;
        for(char c : chars){
            if(c == target){
                count += 1;
            }
        }
        return count;
    }
    
}