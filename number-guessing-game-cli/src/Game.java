import java.util.Random;

public class Game {
    //Data Fields
    private int number;
    private int attempts = 0;
    private Random random = new Random();

    //Constructor
    public Game(){
        this.number = random.nextInt(100) + 1;
    }

    //Methods
    public boolean guessNumber(int guess){
        attempts++;
        if (guess == number){
            System.out.println("Congratulations! You guessed the correct number in "  + attempts + " attempts.");
            return true;
        }
        else if (number > guess){
            System.out.println("Incorrect! The number is greater than " + guess);
        }
        else if (number < guess){
            System.out.println("Incorrect! The number is less than " + guess);
        }
        return false;
    }
}
