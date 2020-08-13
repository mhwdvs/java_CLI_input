public class UsrInput {
    public static int get_int(){
        int input = 0;
        boolean bad_input = true;
        while(bad_input){
            try{
                input = input_stream.nextInt();  // nextInt() does all the input validation required
                bad_input = false;
            }
            catch(Exception e) {
                System.out.print("Thats not an integer :/\nTry again :)\n>>> ");
                // remove /n character from buffer that causes infinite loop
                input_stream.next();
            }
        }
        return input;
    }
}
