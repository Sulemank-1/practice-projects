import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame(){
        Game game = new Game();
        Scanner input = new Scanner(System.in);
        int difficultyLevel;
        System.out.println(
                """
                Welcome to the Number Guessing Game!
                I'm thinking of a number between 1 and 100.
                
                Please select the difficulty level:
                1. Easy (10 chances)
                2. Medium (5 chances)
                3. Hard (3 chances)
                """
        );
        System.out.print("Enter your choice: ");
        difficultyLevel = input.nextInt();

        if (difficultyLevel < 1 || difficultyLevel > 3){
            System.out.println("Wrong choice. Please enter 1,2, or 3");
        } else {
            int guessAttempts;
            if (difficultyLevel == 1) {
                System.out.println("""
                    Great! You have selected the Easy difficulty level.
                    Let's start the game!
                    """);
                guessAttempts = 10;
            }else if (difficultyLevel == 2) {
                System.out.println("""
                        Great! You have selected the Medium difficulty level.
                        Let's start the game!
                        """);
                guessAttempts = 5;
            }else {
                System.out.println("""
                    Great! You have selected the Hard difficulty level.
                    Let's start the game!
                    """);
                guessAttempts = 3;
            }

            for (int i = 0; i < guessAttempts ; i++) {
                System.out.print("\nEnter your guess: ");
                int guess = input.nextInt();
                if (game.guessNumber(guess))
                    break;
            }
        }
    }
}
