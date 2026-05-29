import java.io.*;
import java.util.*;

public class NumberGuessingGame{

        private static final String BEST_SCORE_FILE = "bestscore.txt";
        private static int bestScore = 0;
        private static final Scanner scanner = new Scanner(System.in);
        private static final Random random = new Random();

    public static void main(String[] args) {

        loadBestScore();

        System.out.println("=== Number Guessing Game ===");

        while (true){
            System.out.println("1. Play game");
            System.out.println("2. View Best Score");
            System.out.println("3. Exit");
            System.out.println("Choose One Option");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1":
                    playGame();
                    break;
                case "2":
                    System.out.println("Current Best Score: "+bestScore);
                    break;
                case "3":
                    System.out.println("Good Bye...");
                    return;
                default:
                    System.out.println("Invalid Input. Please Enter 1,2,3");
            }

        }
    }
    private static void playGame(){
        //--Difficulty Section--
        System.out.println("\nSelect Difficulty");
        System.out.println("1. Easy (1-50 Numbers, 10 Attempts)");
        System.out.println("2. Medium (1-100 Numbers, 7 Attempts)");
        System.out.println("3. Hard (1-100 Numbers, 5 Attempts)");
        System.out.println("Choose");

        int maxNumbers, maxAttempts, multiplier;

        int diffChoice = readInt(1,3);
        switch (diffChoice){
            case 1:
                maxNumbers = 50; maxAttempts = 10; multiplier =1;
                break;
            case 2:
                maxNumbers = 100; maxAttempts = 7; multiplier = 2;
                break;
            default:
                maxNumbers = 100; maxAttempts = 5; multiplier =3;
        }

        int secretNumber = random.nextInt(maxNumbers) + 1;
        int attemptsLeft = maxAttempts;
        boolean guessCorrectly = false;

        System.out.println("\nI'm Thinking of a Number between 1 and "+maxNumbers);
        System.out.println("You Have "+ maxAttempts+" attempts. Good Luck!");

        //Guessing Loop
        while (attemptsLeft>0){
            attemptsLeft --;
            System.out.println("Attempt "+( maxAttempts-attemptsLeft )+"/"+maxAttempts +"-Guess: ");

            int guess = readInt(1, maxNumbers);

            if (guess == secretNumber){
                guessCorrectly = true;
                System.out.println("Congratulations! You guessed it!");
                break;
            }else if (guess<secretNumber){
                System.out.println("Too Low!...Guess High");
            }else {
                System.out.println("Too High!...Guess Low");
            }

        }
        if (!guessCorrectly){
            System.out.println("Out Of attempts! The Number was "+secretNumber);
        }
    //Score Calculation
        int roundScore = 0;
        if (guessCorrectly){
            roundScore =  attemptsLeft * multiplier * 10;
        }
        System.out.println("Round Score: "+roundScore);

        //--Best Score Update--
        if (roundScore>bestScore){
            bestScore = roundScore;
            saveBestScore();
            System.out.println("Your Best Score: "+ bestScore);
        }

        //--Replay Option--
        System.out.println("\nPlay again? (y/n): ");
        String again = scanner.nextLine().trim().toLowerCase();
        if (again.equals("y") || again.equals("yes")){
            playGame();//Replay
        }
        //OtherWise returns to Menu
    }
    //Helper methods

    //Reads an integer within(min, max)
    private static int readInt(int min, int max){
        while (true){
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >=min && value <=max){
                    return value;
                }
                System.out.println("Please Enter a Number between "+ min + "and "+max + ":");
            }catch (NumberFormatException e){
                System.out.println("Invalid Input. Please Enter a Number");
            }
        }
    }
    //Load Best Score or Set Best Score =0
    private static void loadBestScore(){
        try (Scanner fileScanner = new Scanner(new File(BEST_SCORE_FILE))){
            if (fileScanner.hasNextInt()) {
                bestScore = fileScanner.nextInt();
            }
        }catch (FileNotFoundException e){
            bestScore = 0;
        }
    }

    //Save New Best Score
    private static void saveBestScore(){
        try(PrintWriter writer = new PrintWriter(BEST_SCORE_FILE)) {
            writer.println(bestScore);
        }catch (FileNotFoundException e){
            System.out.println("Warning. Could not Save Best Score");
        }
    }
}