import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Input Class
 * @author Matthew Davis
 * Date: 3/9/2020
 * File name: Input.java
 * Purpose:
 *      To provide static, adaptable methods for user input via the CLI.
 * Assumptions:
 * - System.in InputStream should be used for user input
 */
public class RegexInput {
    static Scanner inputStream = new Scanner(System.in);
    
    /**
     * For testing purposes only!
     * For full benefit of the tests please use the -enableassertions flag when running
     */
    public static void main(){
        // integer(Predicate)
        // valid input, valid predicate
        inputStream = new Scanner("5\n");
        assert integer(p -> p % 5 == 0 && p >= 0) == 5;
        // valid input, negative, valid predicate
        inputStream = new Scanner("-5\n");
        assert integer(p -> p == -5) == -5;
        // invalid input (doesnt match predicate, characters, special characters, whitespace, float), followed by valid input, valid predicate
        inputStream = new Scanner("4\nasd\n@#$\n \n5.0\n0");
        assert integer(p -> p % 5 == 0 && p >= 0) == 0;
        
        // integer()
        // valid input
        inputStream = new Scanner("5\n");
        assert integer() == 5;
        // valid input, negative
        inputStream = new Scanner("-5\n");
        assert integer() == -5;
        // invalid input (characters, special characters, whitespace, float), followed by valid input
        inputStream = new Scanner("asd\n!@#!@$!@\n \n5.0\n0\n");
        assert integer() == 0;
        // invalid input, integer overflow attempt
        inputStream = new Scanner(String.valueOf(Integer.MAX_VALUE * 2 + 2 + 5) + "\n0\n");
        assert integer() == 0;
        
        // pattern()
        // valid input, valid predicate
        inputStream = new Scanner("This is a test\n");
        assert pattern("(\\w+)").matches("This");
                
        // Reset input stream
        inputStream.close();
        inputStream = new Scanner(System.in);
    }
    
    /**
     * Validates the input of an integer
     * @return int input
     */
    public static int integer(){
        // Matches any numbers that are at most [max integer length - 1] in length (to prevent integer overflow)
        Pattern matchPattern = Pattern.compile("(-?[0-9]{1," + (Integer.toString(Integer.MAX_VALUE).length() - 1) + "})\\s?$"); 
        String input = new String(); // Take input as a string to prevent integer overflow
        int res = 0;
        boolean badInput = true;
        while(badInput){
            if(inputStream.hasNext(matchPattern)){
                input = new String(inputStream.next(matchPattern));
                res = Integer.parseInt(input); // convert string back to integer
                badInput = false;
            }
            else{
                inputStream.nextLine(); // discard non-match input
                System.out.print("Invalid input, please try again:\n>>> ");
            }
        }
        return res;
    }
    
    /**
     * Validates the input of an integer to a predicate (boolean) statement
     * @param p Predicate logic using the Integer type that the incoming integer should satisfy to be accepted
     * @return 
     */
    public static int integer(Predicate<Integer> p){
        // Matches any numbers that are at most [max integer length - 1] in length
        Pattern matchPattern = Pattern.compile("(-?[0-9]{1," + (Integer.toString(Integer.MAX_VALUE).length() - 1) + "})\\s?$"); 
        String input = new String(); // Take input as a string to prevent integer overflow
        int res = 0;
        boolean badInput = true;
        while(badInput){
            // Check that input stream has an integer
            if(inputStream.hasNext(matchPattern)){
                input = new String(inputStream.next(matchPattern));
                res = Integer.parseInt(input); // convert string back to integer
                // Test integer against required predicate logic
                if(p.test(res)){
                    badInput = false;
                }
                else{
                    inputStream.nextLine();
                    System.out.print("Invalid input, please try again:\n>>> ");
                }
            }
            // If there is not integer in the input stream, removes a token from the input stream
            else{
                inputStream.nextLine();
                System.out.print("Invalid input, please try again:\n>>> ");
            }
        }
        return res;
    }
    
    /**
     * Validates user input to satisfy a regex filter (inputted as a string)
     * @param incPattern The regex pattern to match 
     * @return String - user input that matched the desired pattern
     */
    public static String pattern(String incPattern){
        Pattern matchPattern = Pattern.compile(incPattern);
        String input = new String();
        boolean badInput = true;

        while(badInput){
            try{
                if(inputStream.hasNext(matchPattern)){
                    input = new String(inputStream.next(matchPattern));
                    badInput = false;
                }
                else{
                    // If there is not a match in the input stream, removes a token from the input stream
                    inputStream.nextLine();
                    System.out.print("Invalid input, please try again:\n>>> ");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return input;
    }
}